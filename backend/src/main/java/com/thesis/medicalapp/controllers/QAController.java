package com.thesis.medicalapp.controllers;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.QuestionAnswer;
import com.thesis.medicalapp.payload.response.ApiResponse;
import com.thesis.medicalapp.pojo.ProfileDTO;
import com.thesis.medicalapp.pojo.QADTO;
import com.thesis.medicalapp.services.QAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/qa")
@RequiredArgsConstructor
public class QAController {
    private final QAService qaService;

    @PostMapping("")
    public ResponseEntity<Object> saveQA(@RequestBody @Valid QADTO qadto) {
        QADTO questionAnswer = qaService.saveQA(qadto);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(questionAnswer)
        );
    }

    @GetMapping("")
    public ResponseEntity<Object> getQAs() {
        List<QADTO> questionAnswers = qaService.getQAs();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(questionAnswers)
        );
    }

    @PostMapping("/complete")
    public ResponseEntity<Object> saveQA(@RequestParam String id) {
        QADTO questionAnswer = qaService.setComplete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>(questionAnswer)
        );
    }
}
