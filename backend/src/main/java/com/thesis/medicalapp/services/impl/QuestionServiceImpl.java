package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.models.Favorite;
import com.thesis.medicalapp.models.Question;
import com.thesis.medicalapp.repository.QuestionRepository;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.search.SearchSpecification;
import com.thesis.medicalapp.services.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Optional<Question> findById(String id) {
        return questionRepository.findById(id);
    }

    @Override
    public Page<Question> getAll(SearchRequest request) {
        SearchSpecification<Question> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return questionRepository.findAll(specification, pageable);
    }
}
