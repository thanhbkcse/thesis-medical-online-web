package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.*;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.pojo.DoctorDTO;
import com.thesis.medicalapp.pojo.UserDoctorDTO;
import com.thesis.medicalapp.repository.DoctorRepository;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.services.DoctorESService;
import com.thesis.medicalapp.services.DoctorService;
import com.thesis.medicalapp.services.HospitalService;
import com.thesis.medicalapp.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DoctorController {
    private final DoctorService doctorService;
    private final UserService userService;
    private final HospitalService hospitalService;
    private final DoctorESService doctorESService;
    private final DoctorRepository doctorRepository;

    @Data
    public static class DoctorCreateDTO {
        @NotBlank
        @NotEmpty
        private String username;
        @NotBlank
        @NotEmpty
        private String phone;
        @NotBlank
        @NotEmpty
        private String password;
        private String imageUrl;
        @NotBlank
        @NotEmpty
        private String name;
        @NotNull
        private Gender gender;
        @NotBlank
        @NotEmpty
        private String dob;
        private String email;
        @NotNull
        private SpecialtyType specialty;
        @NotBlank
        @NotEmpty
        private String level;
        @NotBlank
        @NotEmpty
        private String hospitalId;
        private String hospitalName;
        private String bio;
        @NotBlank
        @NotEmpty
        private String price;
    }

    private static final ModelMapper modelMapper = new ModelMapper();
    static {
        Converter<String, Hospital> idToHospital = ctx -> new Hospital(ctx.getSource());
        TypeMap<DoctorCreateDTO, Doctor> propertyMapper =
                modelMapper.createTypeMap(DoctorCreateDTO.class, Doctor.class);
        propertyMapper.addMappings(mapper -> {
            mapper.using(idToHospital).map(DoctorCreateDTO::getHospitalId, Doctor::setHospital);
        });
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getDoctors() {
        List<UserDoctorDTO> doctors = doctorService.getDoctors();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(doctors)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDoctorDetail(@PathVariable String id) {
        Doctor doctor =  doctorRepository.findDoctorById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(UserDoctorDTO.from(doctor))
        );
    }

    @GetMapping("/specialty")
    public ResponseEntity<Object> getDoctorsBySpecialty(@RequestParam String specialty) {
        List<UserDoctorDTO> doctors = doctorService.getDoctorsBySpecialty(specialty);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(doctors)
        );
    }

    @GetMapping("/hospital")
    public ResponseEntity<Object> getDoctorsByHospital(
            @RequestParam String hospitalId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Doctor> doctors = doctorService.findAllByHospitalId(hospitalId, pageable);
        Page<UserDoctorDTO> doctorDTOS = doctors.map(
                doctor -> {
                    UserDoctorDTO doctorDTO = UserDoctorDTO.from(doctor);
                    return doctorDTO;
                }
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(doctorDTOS.getContent(), doctorDTOS)
        );
    }

    @PostMapping("/search")
    public ResponseEntity<Object> search(@RequestBody SearchRequest request) {
        Page<Doctor> page = doctorService.search(request);
        Page<DoctorDTO> doctors = page.map(
                d -> {
                    DoctorDTO doctorDTO = DoctorDTO.from(d);
                    return doctorDTO;
                }
        );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(doctors.getContent(), doctors)
        );
    }

    @PostMapping("")
    public ResponseEntity<Object> saveDoctor(@RequestBody @Valid DoctorCreateDTO doctorCreateDTO) {
        if (userService.existsByUsername(doctorCreateDTO.getUsername()))
            throw new ApiRequestException("Tên đăng nhập đã tồn tại!");
        if (userService.existsByPhone(doctorCreateDTO.getPhone()))
            throw new ApiRequestException("Số điện thoại đã tồn tại!");
        if (!hospitalService.existsById(doctorCreateDTO.getHospitalId()))
            throw new ApiRequestException("Không tìm thấy bệnh viện!");
        Doctor doctorRequest = modelMapper.map(doctorCreateDTO, Doctor.class);
        Date dateFormat = new Date();
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(doctorCreateDTO.getDob());
        } catch (Exception e) {
            throw new ApiRequestException("Invalid date!");
        }
        doctorRequest.setDob(dateFormat);
        doctorRequest.setEnabled(Boolean.TRUE);
        doctorRequest.setRoles(new ArrayList<>());
        User doctor = userService.saveUser(doctorRequest);
        userService.addRoleToUser(doctor.getUsername(), "ROLE_DOCTOR");
        Doctor doctorES = doctorService.findDoctorById(doctor.getId());
        // Save doctor to ES
        doctorESService.save(doctorES);
        DoctorDTO doctorResponse = modelMapper.map(doctor, DoctorDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(doctorResponse)
        );
    }
}
