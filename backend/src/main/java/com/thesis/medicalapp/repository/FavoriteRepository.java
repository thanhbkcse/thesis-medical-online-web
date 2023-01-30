package com.thesis.medicalapp.repository;

import com.thesis.medicalapp.models.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, String>, JpaSpecificationExecutor<Favorite> {
    @Query("SELECT AVG(e.favorite) FROM Favorite e WHERE e.objectId = ?1")
    Double countFavoriteByObjectId(String objectId);
}
