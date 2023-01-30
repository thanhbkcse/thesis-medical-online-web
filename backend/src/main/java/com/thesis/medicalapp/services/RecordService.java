package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.Record;
import com.thesis.medicalapp.pojo.RecordDTO;
import com.thesis.medicalapp.search.SearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecordService {
    RecordDTO saveRecord(Record record);
    Page<Record> findAll(SearchRequest searchRequest);
    List<RecordDTO> getRecordsByProfile(String profileId);
    RecordDTO getRecordByAppointment(String appointmentId);
}
