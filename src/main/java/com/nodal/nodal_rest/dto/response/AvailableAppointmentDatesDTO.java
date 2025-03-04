package com.nodal.nodal_rest.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AvailableAppointmentDatesDTO {
    private Date date;
    private boolean isAvailable;
}
