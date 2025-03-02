package com.nodal.nodal_rest.service;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.model.UserCalender;
import org.springframework.http.ResponseEntity;

public interface UserCalendarService {

    ResponseEntity<ApiResponse<UserCalender>> createNewCalender(UserCalender body);
}
