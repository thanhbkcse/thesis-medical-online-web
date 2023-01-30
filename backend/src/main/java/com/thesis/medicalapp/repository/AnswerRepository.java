package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Answer;
import com.thesis.medicalapp.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnswerRepository extends JpaRepository<Answer, String>, JpaSpecificationExecutor<Answer> {
}
