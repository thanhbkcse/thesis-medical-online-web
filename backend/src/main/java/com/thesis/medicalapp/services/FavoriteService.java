package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.Favorite;
import com.thesis.medicalapp.search.SearchRequest;
import org.springframework.data.domain.Page;

public interface FavoriteService {
    Favorite save(Favorite favorite);
    Page<Favorite> getAll(SearchRequest request);
}
