package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, String> {
    @Modifying
    @Query("delete from OTP t where t.expireDate <= ?1")
    void deleteByExpireDateLessThan(Date now);
    Optional<OTP> findByUserIdAndAndToken(String userId, String token);
}
