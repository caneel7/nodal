package com.nodal.nodal_rest.model;

import com.nodal.nodal_rest.enums.DaysEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "user_calendar_slots")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCalendarSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "day")
    @Enumerated(EnumType.STRING)
    private DaysEnum day;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "exception_start_time")
    private LocalTime exceptionStartTime;

    @Column(name = "exception_end_time")
    private LocalTime exceptionEndTime;

    @Column(name = "active")
    @Builder.Default
    private boolean active = true;

    @Column(name = "created_at")
    @Builder.Default
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Builder.Default
    private Date updatedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "user_calendar_id",nullable = false)
    private UserCalendar userCalendar;

}
