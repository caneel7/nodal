package com.nodal.nodal_rest.controller;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.libs.CustomAuthenticationProvider;
import com.nodal.nodal_rest.libs.ResponseEntityBuilder;
import com.nodal.nodal_rest.model.User;
import com.nodal.nodal_rest.model.UserCalender;
import com.nodal.nodal_rest.repository.UserCalendarRepository;
import com.nodal.nodal_rest.service.UserCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user-calendar", produces = "application/json")
public class UserCalenderController {

    @Autowired
    private UserCalendarService userCalendarService;
    @Autowired
    private CustomAuthenticationProvider authenticationProvider;
    @Autowired
    private UserCalendarRepository userCalendarRepository;

    @PostMapping()
    public ResponseEntity<ApiResponse<UserCalender>> createNewCalendar(@RequestBody UserCalender body)
    {
        return userCalendarService.createNewCalender(body);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<UserCalender>>> getUserCalendarList()
    {
        User user = authenticationProvider.getUser();
        return ResponseEntityBuilder.success(userCalendarRepository.getAllByUserId(user.getId()),"All User Calendars");
    }
}
