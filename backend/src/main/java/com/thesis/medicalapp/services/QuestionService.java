package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.Question;
import com.thesis.medicalapp.search.SearchRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface QuestionService {
    Question save(Question question);
    Optional<Question> findById(String id);
    Page<Question> getAll(SearchRequest searchRequest);
}
