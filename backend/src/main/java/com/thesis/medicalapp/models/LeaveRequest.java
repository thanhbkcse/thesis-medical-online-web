package com.thesis.medicalapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "leave_requests")
public class LeaveRequest {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    public String date;
    @ElementCollection
    @JoinColumn(name = "leave_request_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<String> times;
    public String doctorId;
    private Boolean isApproved;
    private String reason;
}
