package com.nodal.nodal_rest.repository;


import com.nodal.nodal_rest.model.UserScheduledAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

import java.util.List;

public interface UserScheduledAppointmentRepository extends JpaRepository<UserScheduledAppointment,Integer> {


    //optional can use namedQuery to map it to a dto
    @Query(value = "SELECT usa.appointment_date AS appointmentDate, COUNT(usa.id) AS count " +
            "FROM user_scheduled_appointments usa " +
            "WHERE usa.user_calendar_id = :userCalendarId  AND usa.appointment_date BETWEEN :startDate AND :endDate " +
            "GROUP BY usa.appointment_date", nativeQuery = true)
    List<Object[]> getUserCalendarAppointmentsGroupByDate(Integer userCalendarId, LocalDate startDate, LocalDate endDate);
}
