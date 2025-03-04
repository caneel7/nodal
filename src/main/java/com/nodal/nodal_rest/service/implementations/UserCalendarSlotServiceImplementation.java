package com.nodal.nodal_rest.service.implementations;

import com.nodal.nodal_rest.enums.DaysEnum;
import com.nodal.nodal_rest.model.UserCalendar;
import com.nodal.nodal_rest.model.UserCalendarSlot;
import com.nodal.nodal_rest.repository.UserCalendarRepository;
import com.nodal.nodal_rest.service.UserCalendarSlotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.*;

@Service
public class UserCalendarSlotServiceImplementation implements UserCalendarSlotService {

    private static final Logger log = LoggerFactory.getLogger(UserCalendarSlotServiceImplementation.class);
    @Autowired
    private UserCalendarRepository userCalendarRepository;

    @Override
    public List<UserCalendarSlot> createDefaultSlots(UserCalendar calendar) {
        String[] days = new String[]{"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY"};

        return Arrays.stream(days).map(
                day -> {
                    UserCalendarSlot slot = UserCalendarSlot.builder()
                            .userCalendar(calendar)
                            .day(DaysEnum.valueOf(day))
                            .startTime(LocalTime.parse("09:00:00"))
                            .endTime(LocalTime.parse("17:00:00"))
                            .build();
                    return slot;
                }
        ).toList();
    }


}
