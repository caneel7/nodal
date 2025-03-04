package com.nodal.nodal_rest.service;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.dto.response.AvailableAppointmentDatesDTO;
import com.nodal.nodal_rest.model.UserCalendar;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface UserCalendarService {

    ResponseEntity<ApiResponse<UserCalendar>> createNewCalendar(UserCalendar body);

    ResponseEntity<ApiResponse<UserCalendar>> updateCalendar(Integer id, UserCalendar body);

    ResponseEntity<ApiResponse<List<AvailableAppointmentDatesDTO>>> getAvailableAppointmentDates(Integer userCalendarId, LocalDate startDate, LocalDate endDate);

}
