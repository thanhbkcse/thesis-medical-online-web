package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Appointment;
import com.thesis.medicalapp.models.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String>, JpaSpecificationExecutor<Appointment> {
    List<Appointment> findAllByProfileId(String profileId);
    Page<Appointment> findAllByProfile_User_Username(String username, Pageable pageable);
    Page<Appointment> findAllByProfile_User_UsernameOrProfile_ProfileNumber(String username, String profileNumber, Pageable pageable);
    Appointment findAppointmentById(String id);
    Appointment findByOrderId(String orderId);
    List<Appointment> findAllByDateAndDoctor(Date date, Doctor doctor);
    Page<Appointment> findAllByDateAndDoctor_IdAndStatusIn(Date date, String username, List<String> status, Pageable pageable);
//    @Query(value = "SELECT max(a.stt) FROM Appointment as a WHERE a.date = ?1")
//    Integer max(Date date);
    List<Appointment> getAllByDateIsBetweenAndDoctor(Date start, Date end, Doctor doctor);
    List<Appointment> findAllByDateAndDoctor_IdAndTime(Date date, String doctorId, String time);
    Page<Appointment> findAll(Specification specification, Pageable pageable);
}
