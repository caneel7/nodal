package com.nodal.nodal_rest.service;

import com.nodal.nodal_rest.model.UserCalendar;
import com.nodal.nodal_rest.model.UserCalendarSlot;
import java.util.List;

public interface UserCalendarSlotService {

    List<UserCalendarSlot> createDefaultSlots(UserCalendar calendar);

}
