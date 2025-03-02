package com.nodal.nodal_rest.service;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.model.UserCalendar;
import org.springframework.http.ResponseEntity;

public interface UserCalendarService {

    ResponseEntity<ApiResponse<UserCalendar>> createNewCalendar(UserCalendar body);

    ResponseEntity<ApiResponse<UserCalendar>> updateCalendar(Integer id, UserCalendar body);
}
