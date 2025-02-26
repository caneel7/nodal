package com.nodal.nodal_rest.model;

import com.nodal.nodal_rest.enums.DaysType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "user_calenders")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCalender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int user_id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_duration")
    private String eventDuration;

    @Column(name = "event_location")
    private String eventLocation;

    @Column(name = "description")
    private String description;

    @Column(name = "host_name")
    private String hostName;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime  endDate;

    @Column(name = "days")
    private int days;

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
