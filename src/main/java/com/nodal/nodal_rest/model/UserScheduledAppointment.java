package com.nodal.nodal_rest.model;

import jakarta.persistence.*;
import lombok.*;

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
}
