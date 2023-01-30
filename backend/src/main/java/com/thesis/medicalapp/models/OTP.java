package com.thesis.medicalapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "one_time_tokens")
public class OTP {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String token;
    private Date expireDate;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public static String generateOTP() {
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        return Integer.toString(OTP);
    }

    public Date calculateExpireDate(int expireTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expireTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public Boolean isValid() {
        Date now = Date.from(Instant.now());
        return this.expireDate.after(now);
    }
}
