package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.models.Favorite;
import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.repository.FavoriteRepository;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.search.SearchSpecification;
import com.thesis.medicalapp.services.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    @Override
    public Favorite save(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @Override
    public Page<Favorite> getAll(SearchRequest request) {
        SearchSpecification<Favorite> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return favoriteRepository.findAll(specification, pageable);
    }
}
