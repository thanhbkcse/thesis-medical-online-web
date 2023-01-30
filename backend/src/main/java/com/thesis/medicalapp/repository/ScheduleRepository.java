package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Doctor;
import com.thesis.medicalapp.models.Schedule;
import com.thesis.medicalapp.models.SpecialtyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    List<Schedule> findAllByDate(Date date);
    List<Schedule> findAllByDoctor(Doctor doctor);
    Schedule findByDateAndDoctor(Date data, Doctor doctor);
    Optional<Schedule> findScheduleById(String id);
    List<Schedule> getAllByDateIsBetweenAndDoctor(Date dateStart, Date dateEnd, Doctor doctor);
    List<Schedule> findAllByDateAndDoctor(Date date, Doctor doctor);
    List<Schedule> findAllByDateAndDoctor_SpecialtyAndDoctor_Hospital_Id(Date date, SpecialtyType specialtyType, String hospitalId);
}
