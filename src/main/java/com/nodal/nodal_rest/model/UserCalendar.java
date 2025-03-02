package com.nodal.nodal_rest.model;

import com.nodal.nodal_rest.enums.DaysType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "user_calendars")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_duration")
    private int eventDuration;

    @Column(name = "event_location")
    private String eventLocation;

    @Column(name = "description")
    private String description;

    @Column(name = "host_name")
    private String hostName;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "days")
    private int days;

    @Enumerated(EnumType.STRING)
    @Column(name = "days_type")
    private DaysType daysType;

    @Column(name = "indefinitely_future")
    private boolean indefinitelyFuture;

    @Column(name = "buffer_time_before_event")
    private int bufferTimeBeforeEvent;

    @Column(name = "buffer_time_after_event")
    private int bufferTimeAfterEvent;

    @Column(name = "time_zone")
    private String timezone;

    @Column(name = "max_meetings")
    private int maxMeetings;

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
