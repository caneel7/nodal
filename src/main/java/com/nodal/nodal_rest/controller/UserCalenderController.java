package com.nodal.nodal_rest.controller;

import com.nodal.nodal_rest.dto.ApiResponse;
import com.nodal.nodal_rest.dto.response.AvailableAppointmentDatesDTO;
import com.nodal.nodal_rest.libs.CustomAuthenticationProvider;
import com.nodal.nodal_rest.libs.ResponseEntityBuilder;
import com.nodal.nodal_rest.model.User;
import com.nodal.nodal_rest.model.UserCalendar;
import com.nodal.nodal_rest.repository.UserCalendarRepository;
import com.nodal.nodal_rest.service.UserCalendarService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
    public ResponseEntity<ApiResponse<UserCalendar>> createNewCalendar(@RequestBody UserCalendar body)
    {
        return userCalendarService.createNewCalendar(body);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<UserCalendar>>> getUserCalendarList()
    {
        User user = authenticationProvider.getUser();
        return ResponseEntityBuilder.success(userCalendarRepository.getAllByUserId(user.getId()),"All User Calendars");
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ApiResponse<UserCalendar>> updateUserCalendar(@PathVariable Integer id, @RequestBody UserCalendar body)
    {
        return userCalendarService.updateCalendar(id,body);
    }

    @GetMapping("/public/{calendarId}/available-dates")
    public ResponseEntity<ApiResponse<List<AvailableAppointmentDatesDTO>>> getAvailableDates
            (@PathVariable Integer calendarId,
             @RequestParam LocalDate startDate,
             @RequestParam  LocalDate endDate)
    {
        return userCalendarService.getAvailableAppointmentDates(calendarId,startDate,endDate);
    }
}
