package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String>, JpaSpecificationExecutor<Question> {

}
