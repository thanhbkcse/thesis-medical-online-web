package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.models.SpecialtyType;
import com.thesis.medicalapp.payload.response.ApiResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SpecialtyController {
    @Data
    public static class Specialty {
        private SpecialtyType value;
        private String text;
    }

    private List<Specialty> specialties = new ArrayList<>();
    @GetMapping("/specialties")
    public ResponseEntity<ApiResponse> getSpecialty() {
        for(SpecialtyType s : SpecialtyType.values()) {
            Specialty specialty = new Specialty();
            specialty.setValue(s);
            specialty.setText(s.getName());
            this.specialties.add(specialty);
        }
        return ResponseEntity.ok(
                new ApiResponse(this.specialties)
        );
    }
}
