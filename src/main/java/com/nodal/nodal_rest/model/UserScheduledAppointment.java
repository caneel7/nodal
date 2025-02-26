package com.nodal.nodal_rest.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "user_scheduled_appointments")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserScheduledAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "user_calendar_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "user_scheduled_appointments_user_calendar_id_fkey"))
    private UserCalender userCalendar;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "appointment_time")
    private LocalTime appointmentTime;

    @Column(name = "appointment_timezone")
    private String appointmentTimezone;

    @Column(name = "status")
    private String status;

    @Column(name = "cancel_reason")
    private String cancelReason;

    @Column(name = "cancelled_by_user")
    private Boolean cancelledByUser;

    @Column(name = "cancelled_by_host")
    private Boolean cancelledByHost;

    @Column(name = "active")
    @Builder.Default
    private boolean active = true;

    @Column(name = "created_at")
    @Builder.Default
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Builder.Default
    private Date updatedAt = new Date();
}
