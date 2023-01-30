package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Address;
import com.thesis.medicalapp.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
}
