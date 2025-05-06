package com.nodal.nodal_rest.service.implementations;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.dto.response.AvailableAppointmentDatesDTO;
import com.nodal.nodal_rest.exception.ResourceNotFoundException;
import com.nodal.nodal_rest.libs.CustomAuthenticationProvider;
import com.nodal.nodal_rest.libs.DateUtil;
import com.nodal.nodal_rest.libs.ResponseEntityBuilder;
import com.nodal.nodal_rest.model.User;
import com.nodal.nodal_rest.model.UserCalendar;
import com.nodal.nodal_rest.model.UserCalendarSlot;
import com.nodal.nodal_rest.repository.UserCalendarRepository;
import com.nodal.nodal_rest.repository.UserCalendarSlotRepository;
import com.nodal.nodal_rest.repository.UserScheduledAppointmentRepository;
import com.nodal.nodal_rest.service.UserCalendarService;
import com.nodal.nodal_rest.service.UserCalendarSlotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

import static com.nodal.nodal_rest.enums.DaysType.WEEKDAYS;

@Service
public class UserCalendarImplementation implements UserCalendarService {

    private static final Logger log = LoggerFactory.getLogger(UserCalendarImplementation.class);
    @Autowired
    private CustomAuthenticationProvider authenticationProvider;
    @Autowired
    private UserCalendarRepository userCalendarRepository;
    @Autowired
    private UserCalendarSlotService userCalendarSlotService;
    @Autowired
    private UserCalendarSlotRepository userCalendarSlotRepository;
    @Autowired
    private UserScheduledAppointmentRepository userScheduledAppointmentRepository;

    @Override
    public ResponseEntity<ApiResponse<UserCalendar>> createNewCalendar(UserCalendar body) {

        User user = authenticationProvider.getUser();

        if(
                (body.getStartDate() == null && body.getEndDate() == null)
                && body.getDays() == 0 && !body.isIndefinitelyFuture()
        )
            return ResponseEntityBuilder.badRequest("Please Provide Either Date Range Or Number Of Days");

        try{

            UserCalendar newCalendar = UserCalendar.builder()
                    .userId(user.getId())
                    .eventName(Optional.ofNullable(body.getEventName()).orElse("Event"))
                    .eventDuration(body.getEventDuration() != 0 ? body.getEventDuration() : 60)
                    .eventLocation(Optional.ofNullable(body.getEventLocation()).orElse("Online"))
                    .description(body.getDescription())
                    .hostName(
                            Optional.ofNullable(body.getHostName())
                                    .orElse(String.format("%s %s", user.getFirstName(), user.getLastName())
                    ))
                    .startDate(body.getStartDate())
                    .endDate(body.getEndDate())
                    .days(body.getDays())
                    .daysType(Optional.ofNullable(body.getDaysType()).orElse(WEEKDAYS))
                    .indefinitelyFuture(body.isIndefinitelyFuture())
                    .bufferTimeBeforeEvent(body.getBufferTimeBeforeEvent())
                    .bufferTimeAfterEvent(body.getBufferTimeAfterEvent())
                    .timezone(body.getTimezone())
                    .maxMeetings(body.getMaxMeetings())
            .build();

            newCalendar = userCalendarRepository.save(newCalendar);

            List<UserCalendarSlot> slots = userCalendarSlotService.createDefaultSlots(newCalendar);

            userCalendarSlotRepository.saveAll(slots);

            return ResponseEntityBuilder.created(newCalendar,"User Calendar Created");
        }catch (Exception e){
            return ResponseEntityBuilder.serverError(e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse<UserCalendar>> updateCalendar(Integer id, UserCalendar body)
    {

        User user = authenticationProvider.getUser();

        UserCalendar calendar = userCalendarRepository.findOneByIdAndUserId(id,user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot Find Calendar"));

        if(calendar == null)
            return ResponseEntityBuilder.notFound("Cannot Find User Calendar");

        try{

            UserCalendar savedCalendar = userCalendarRepository.save(body);

            return ResponseEntityBuilder.success(savedCalendar,"User Calendar Updated");
        }catch (Exception e){
            return ResponseEntityBuilder.serverError(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<AvailableAppointmentDatesDTO>>> getAvailableAppointmentDates(
            Integer userCalendarId, LocalDate startDate, LocalDate endDate
    ) {

        UserCalendar calendar = userCalendarRepository.findById(userCalendarId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot Find User Calendar."));


        Map<String,Integer> maxMeetings = new HashMap<>();

        List<String> days = new ArrayList<>(7);

        calendar.getSlots().forEach(slot -> {

            days.add(slot.getDay().toString().toUpperCase());

            Duration duration = Duration.between(slot.getStartTime(), slot.getEndTime());

            maxMeetings.put(
                    slot.getDay().toString().toUpperCase(),
                    ((int) duration.toMinutes() / calendar.getEventDuration()
            ));

        });

        List<Object[]> meetingCountGroupedByDate = userScheduledAppointmentRepository.getUserCalendarAppointmentsGroupByDate(
                calendar.getId(),
                startDate,
                endDate
        );

        Map<String,Integer> scheduledDates = new HashMap<>();

        for (Object[] row : meetingCountGroupedByDate) {

            //row[i] is the key from select clause in query
            //since we only selected appointmentDate, count, we're only using row[0] and row[1]
            scheduledDates.put(row[0].toString(),((Number) row[1]).intValue());

        }

        List<AvailableAppointmentDatesDTO> availableDates = new ArrayList<>();

        LocalDate date = startDate;
        while (date.isBefore(endDate) || date.equals(endDate)){
            if(days.indexOf(date.getDayOfWeek().toString()) != -1){

                int meeting = scheduledDates.getOrDefault(date.toString(),0);
                int maxMeeting = maxMeetings.getOrDefault(date.getDayOfWeek().toString(),0);

                availableDates.add(
                        AvailableAppointmentDatesDTO.builder()
                                .date(DateUtil.asDate(date))
                                .isAvailable(
                                   meeting >= maxMeeting ? false : true
                                ).build()
                );
            }
            date = date.plusDays(1);
        }

        return ResponseEntityBuilder.success(availableDates,"All Available Dates");
    }
}
