package com.nodal.nodal_rest.service.implementations;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.libs.CustomAuthenticationProvider;
import com.nodal.nodal_rest.libs.ResponseEntityBuilder;
import com.nodal.nodal_rest.model.User;
import com.nodal.nodal_rest.model.UserCalender;
import com.nodal.nodal_rest.repository.UserCalendarRepository;
import com.nodal.nodal_rest.service.UserCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nodal.nodal_rest.enums.DaysType.WEEKDAYS;

@Service
public class UserCalendarImplementation implements UserCalendarService {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;
    @Autowired
    private UserCalendarRepository userCalendarRepository;

    @Override
    public ResponseEntity<ApiResponse<UserCalender>> createNewCalender(UserCalender body) {

        User user = authenticationProvider.getUser();

        if(
                (body.getStartDate() == null && body.getEndDate() == null)
                && body.getDays() == 0 && !body.isIndefinitelyFuture()
        )
            return ResponseEntityBuilder.badRequest("Please Provide Either Date Range Or Number Of Days");

        try{

            UserCalender newCalendar = UserCalender.builder()
                    .userId(user.getId())
                    .eventName(Optional.ofNullable(body.getEventName()).orElse("Event"))
                    .eventDuration(Optional.ofNullable(body.getEventDuration()).orElse(60))
                    .eventLocation(Optional.ofNullable(body.getEventLocation()).orElse("Online"))
                    .description(body.getDescription())
                    .hostName(Optional.ofNullable(body.getHostName()).orElse(String.format("%s %s", user.getFirstName(), user.getLastName())))
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

            userCalendarRepository.save(newCalendar);

            return ResponseEntityBuilder.success(newCalendar,"User Calendar Created");
        }catch (Exception e){
            return ResponseEntityBuilder.serverError(e.getMessage());
        }
    }
}
