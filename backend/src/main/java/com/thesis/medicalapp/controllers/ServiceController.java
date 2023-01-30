package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.models.Doctor;
import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.HospitalService;
import com.thesis.medicalapp.payload.ServiceRequest;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.pojo.DoctorDTO;
import com.thesis.medicalapp.pojo.HospitalDTO;
import com.thesis.medicalapp.pojo.ServiceDTO;
import com.thesis.medicalapp.pojo.UserDoctorDTO;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.services.ServiceESService;
import com.thesis.medicalapp.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;
    private final ServiceESService serviceESService;
    private static final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("")
    public ResponseEntity<Object> saveHospitalService(@RequestBody @Valid ServiceRequest ServiceRequest) {
        HospitalService hospitalService = serviceService.save(ServiceRequest);
        // save service to ES
        serviceESService.save(hospitalService);
        ServiceDTO serviceResponse = ServiceDTO.from(hospitalService);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(serviceResponse)
        );
    }

    @PostMapping("/search")
    public ResponseEntity<Object> getHospitalServices(@RequestBody SearchRequest request) {
        Page<HospitalService> page = serviceService.search(request);
        Page<ServiceDTO> services = page.map(
                s -> {
                    ServiceDTO serviceDTO = ServiceDTO.from(s);
                    return serviceDTO;
                }
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(services.getContent(), services)
        );
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteService(@RequestParam String serviceId) {
        serviceService.delete(serviceId);
        // Delete service to ES
        serviceESService.delete(serviceId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(null)
        );
    }

    @PutMapping("")
    public ResponseEntity<Object> updateService(@RequestBody HospitalService serviceRequest) {
        HospitalService hospitalService = serviceService.update(serviceRequest);
        // Update service to ES
        serviceESService.save(hospitalService);
        ServiceDTO serviceResponse = ServiceDTO.from(hospitalService);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(serviceResponse)
        );
    }

    @GetMapping("/hospital")
    public ResponseEntity<Object> getServicesByHospital(
            @RequestParam String hospitalId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HospitalService> services = serviceService.findAllByHospitalId(hospitalId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(services.getContent(), services)
        );
    }
}
