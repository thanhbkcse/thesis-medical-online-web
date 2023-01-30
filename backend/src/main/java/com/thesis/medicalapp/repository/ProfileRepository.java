package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Profile;
import com.thesis.medicalapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    List<Profile> findAllByUser(User user);
    Profile findById(String id);
    Boolean existsByIdentityCard(String ic);
    Optional<Profile> findProfileByPhoneAndRelationship(String phone, String relationship);
    boolean existsById(String id);
    Profile findProfileByRelationshipAndUser_Username(String relationship, String username);
}
