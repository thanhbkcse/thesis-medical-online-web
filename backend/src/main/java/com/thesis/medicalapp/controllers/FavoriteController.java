package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.*;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.pojo.HospitalDTO;
import com.thesis.medicalapp.repository.*;
import com.thesis.medicalapp.search.*;
import com.thesis.medicalapp.services.FavoriteService;
import com.thesis.medicalapp.services.ProfileService;
import com.thesis.medicalapp.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final ProfileRepository profileRepository;
    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepository;
    private final ServiceRepository serviceRepository;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    @Data
    public static class FavoriteRequest {
        @NotNull
        private Integer favorite;
        private String comment;
        @NotNull
        private String username;
        @NotNull
        private String objectId;
        private String objectType;
    }

    @PostMapping("")
    public ResponseEntity<Object> saveFavorite(@RequestBody @Valid FavoriteRequest favoriteRequest) {
        String username = favoriteRequest.getUsername();
        if (!userService.existsByUsername(username))
            throw new ApiRequestException("Không tìm thấy user!");
        String objectId = favoriteRequest.getObjectId();
        Favorite favorite = new Favorite();
        favorite.setFavorite(favoriteRequest.getFavorite());
        favorite.setComment(favoriteRequest.getComment());
        favorite.setDate(new Date());
        Profile profile = profileRepository.findProfileByRelationshipAndUser_Username("Chủ tài khoản", username);
        favorite.setProfile(profile);
        favorite.setObjectId(objectId);
        Favorite favoriteRes = favoriteService.save(favorite);
        if (favoriteRequest.getObjectType().equals("hospital")) {
            Hospital hospital = hospitalRepository.findById(objectId).get();
            Double favoriteCount = favoriteRepository.countFavoriteByObjectId(objectId);
            hospital.setFavorite(Double.parseDouble(df.format(favoriteCount)));
            hospitalRepository.save(hospital);
        }
        else if (favoriteRequest.getObjectType().equals("doctor")) {
            Doctor doctor = doctorRepository.findById(objectId).get();
            Double favoriteCount = favoriteRepository.countFavoriteByObjectId(objectId);
            doctor.setFavorite(Double.parseDouble(df.format(favoriteCount)));
            doctorRepository.save(doctor);
        }
        else if (favoriteRequest.getObjectType().equals("service")) {
            HospitalService hospitalService = serviceRepository.findById(objectId).get();
            Double favoriteCount = favoriteRepository.countFavoriteByObjectId(objectId);
            hospitalService.setFavorite(Double.parseDouble(df.format(favoriteCount)));
            serviceRepository.save(hospitalService);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(favoriteRes)
        );
    }

    @GetMapping("")
    public ResponseEntity<Object> getFavorites(
            @RequestParam String objectId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        SearchRequest request = new SearchRequest(objectId, page, size);
        Page<Favorite> pageResponse = favoriteService.getAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(pageResponse.getContent(), pageResponse)
        );
    }
}
