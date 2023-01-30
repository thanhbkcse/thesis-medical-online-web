package com.thesis.medicalapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
public class Notification extends AuditLog {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String title;
    private String text;
    private Date time;
    private String fromUser;
    private String toUser;
    private NotificationType type;
    private String objectId;
    private boolean read;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "id", column = @Column(name = "extra_data_id")),
            @AttributeOverride( name = "name", column = @Column(name = "extra_data_name")),
    })
    private ExtraData extraData;
}