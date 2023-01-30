package com.thesis.medicalapp.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ExtraData {
    private String doctorId;
    private String doctorName;
    private String doctorLevel;
}
