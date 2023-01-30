package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.Hospital;
import com.thesis.medicalapp.models.QuestionAnswer;
import com.thesis.medicalapp.models.Status;
import com.thesis.medicalapp.pojo.HospitalDTO;
import com.thesis.medicalapp.pojo.QADTO;
import com.thesis.medicalapp.repository.QARepository;
import com.thesis.medicalapp.services.QAService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QAServiceImpl implements QAService {
    private final QARepository qaRepository;

    @Override
    public QADTO saveQA(QADTO qadto) {
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setName(qadto.getName());
        questionAnswer.setPhone(qadto.getPhone());
        questionAnswer.setDemand(qadto.getDemand());
        questionAnswer.setStatus(Status.PENDING);

        QuestionAnswer qa_db = qaRepository.save(questionAnswer);
        QADTO qa_response = QADTO.from(qa_db);
        return qa_response;
    }

    @Override
    public List<QADTO> getQAs() {
        List<QuestionAnswer> qas = qaRepository.findAll()
                .stream().collect(Collectors.toList());
        List<QADTO> qadtos = qas.stream().map(d -> {
            QADTO qadto = QADTO.from(d);
            return qadto;
        }).collect(Collectors.toList());
        return qadtos;
    }

    @Override
    public QADTO setComplete(String qaId) {
        Optional<QuestionAnswer> qa_db = qaRepository.findById(qaId);
        if (!qa_db.isPresent()) throw new ApiRequestException("Không tìm thấy câu hỏi!");
        QuestionAnswer questionAnswer = qa_db.get();
        questionAnswer.setStatus(Status.COMPLETE);
        qaRepository.save(questionAnswer);
        QADTO qa_response = QADTO.from(questionAnswer);
        return qa_response;
    }
}
