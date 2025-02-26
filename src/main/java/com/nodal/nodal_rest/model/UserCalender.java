package com.nodal.nodal_rest.model;

import jakarta.persistence.*;
import lombok.*;

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
}
