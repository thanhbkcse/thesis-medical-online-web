package com.thesis.medicalapp.services;

import com.thesis.medicalapp.pojo.QADTO;

import java.util.List;

public interface QAService {
    QADTO saveQA(QADTO qadto);
    List<QADTO> getQAs();
    QADTO setComplete(String qaId);
}
