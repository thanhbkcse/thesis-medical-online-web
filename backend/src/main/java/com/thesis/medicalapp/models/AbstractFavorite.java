package com.thesis.medicalapp.models;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class AbstractFavorite extends AuditLog {
    private String name;
    private Integer registrationNumber;
    private Double favorite;
    @Type(type="text")
    private String info;
    @Type(type="text")
    private String imageUrl;
}
