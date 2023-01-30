package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.services.HospitalService;
import com.thesis.medicalapp.models.StoreMedicine;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.pojo.HospitalDTO;
import com.thesis.medicalapp.pojo.StoreMedicineDTO;
import com.thesis.medicalapp.services.StoreMedicineService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;
@Data
class StoreMedicineCreateDTO {
    @NotBlank
    @NotEmpty
    private String name;
    private String imageUrl;
    private String price;
    private String info;
    @NotBlank
    @NotEmpty
    private String hospitalId;
}

@Data
class StoreMedicineSelect {
    private String id;
    private String name;
}

@RestController
@RequestMapping("/api/store-medicines")
@RequiredArgsConstructor
public class StoreMedicineController {
    private static final ModelMapper modelMapper = new ModelMapper();
    private final StoreMedicineService storeMedicineService;
    private final HospitalService hospitalService;

    static {
        Converter<String, Hospital> idToHospital = ctx -> new Hospital(ctx.getSource());
        TypeMap<StoreMedicineCreateDTO, StoreMedicine> propertyMapper =
                modelMapper.createTypeMap(StoreMedicineCreateDTO.class, StoreMedicine.class);
        propertyMapper.addMappings(mapper -> {
            mapper.using(idToHospital).map(StoreMedicineCreateDTO::getHospitalId, StoreMedicine::setHospital);
        });
    }

    @GetMapping("")
    public ResponseEntity<Object> getStoreMedicines() {
        List<StoreMedicineDTO> storeMedicineDTOS = storeMedicineService.getAll()
                .stream()
                .map(storeMedicine -> modelMapper.map(storeMedicine, StoreMedicineDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(storeMedicineDTOS)
        );
    }

    @PostMapping("")
    public ResponseEntity<Object> createStoreMedicine(@RequestBody @Valid StoreMedicineCreateDTO storeMedicineCreateDTO) {
        if (!hospitalService.existsById(storeMedicineCreateDTO.getHospitalId())) {
            throw new ApiRequestException("Không tìm thấy bệnh viện!");
        }
        StoreMedicine storeMedicineRequest = modelMapper.map(storeMedicineCreateDTO, StoreMedicine.class);
        StoreMedicine storeMedicine = storeMedicineService.save(storeMedicineRequest);
        StoreMedicineDTO storeMedicineRes = modelMapper.map(storeMedicine, StoreMedicineDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(storeMedicineRes)
        );
    }

    @PostMapping("/search")
    public ResponseEntity<Object> search(@RequestBody SearchRequest request) {
        Page<StoreMedicine> page = storeMedicineService.search(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(page.getContent(), page)
        );
    }

    @GetMapping("/hospital")
    public ResponseEntity<Object> getByHospital(@RequestParam String hospitalId) {
        List<StoreMedicineSelect> storeMedicines = storeMedicineService.getByHospital(hospitalId)
                .stream()
                .map(storeMedicine -> modelMapper.map(storeMedicine, StoreMedicineSelect.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(storeMedicines)
        );
    }
}