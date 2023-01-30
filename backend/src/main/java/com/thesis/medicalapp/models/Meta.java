package com.thesis.medicalapp.models;

import lombok.Data;

@Data
public class Meta {
    private Object pageable;
    private Boolean last;
    private Boolean first;
    private Integer totalElements;
    private Integer totalPages;
    private Object sort;
}
