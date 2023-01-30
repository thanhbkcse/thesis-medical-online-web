package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.models.DoctorES;
import com.thesis.medicalapp.models.HospitalES;
import com.thesis.medicalapp.models.HospitalServiceES;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.services.DoctorESService;
import com.thesis.medicalapp.services.HospitalESService;
import com.thesis.medicalapp.services.ServiceESService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/generic-search")
@RequiredArgsConstructor
public class GenericSearchController {
    private final HospitalESService hospitalESService;
    private final ServiceESService serviceESService;
    private final DoctorESService doctorESService;

    @Data
    public static class GenericSearchResponse {
        private List<HospitalES> hospitals = new ArrayList<>();
        private List<HospitalServiceES> services = new ArrayList<>();
        private List<DoctorES> doctors = new ArrayList<>();
    }
    @Data
    public static class ESSearchRequest {
        private String value;
    }
    @PostMapping("")
    public ResponseEntity<Object> search(@RequestBody ESSearchRequest esSearchRequest) {
        String value = esSearchRequest.getValue();
        Page<HospitalES> pageHospital = hospitalESService.search(value);
        Page<HospitalServiceES> pageService = serviceESService.search(value);
        Page<DoctorES> pageDoctors = doctorESService.search(value);
        GenericSearchResponse genericSearchResponse = new GenericSearchResponse();
        genericSearchResponse.setHospitals(pageHospital.getContent());
        genericSearchResponse.setServices(pageService.getContent());
        genericSearchResponse.setDoctors(pageDoctors.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(genericSearchResponse)
        );
    }
}
