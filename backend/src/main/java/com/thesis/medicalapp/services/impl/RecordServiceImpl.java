package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.Record;
import com.thesis.medicalapp.pojo.ProfileDTO;
import com.thesis.medicalapp.pojo.RecordDTO;
import com.thesis.medicalapp.repository.RecordRepository;
import com.thesis.medicalapp.search.SearchRequest;
import com.thesis.medicalapp.search.SearchSpecification;
import com.thesis.medicalapp.services.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;
    @Override
    public Page<Record> findAll(SearchRequest request) {
        SearchSpecification<Record> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return recordRepository.findAll(specification, pageable);
    }

    @Override
    public RecordDTO saveRecord(Record record) {
        Record recordEntity = recordRepository.save(record);
        RecordDTO recordResponse = RecordDTO.from(recordEntity);
        return recordResponse;
    }

    @Override
    public List<RecordDTO> getRecordsByProfile(String profileId) {
        List<Record> records = recordRepository.findByAppointment_Profile_IdOrderByCreatedAtDesc(profileId)
                .stream()
                .collect(Collectors.toList());
        List<RecordDTO> recordDTOS = records.stream().map(p -> {
            RecordDTO recordDTO = RecordDTO.from(p);
            return recordDTO;
        }).collect(Collectors.toList());
        return recordDTOS;
    }

    @Override
    public RecordDTO getRecordByAppointment(String appointmentId) {
        Record record = recordRepository.findByAppointment_Id(appointmentId);
        return RecordDTO.from(record);
    }
}
