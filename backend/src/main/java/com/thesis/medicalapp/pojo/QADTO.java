package com.thesis.medicalapp.pojo;

import com.thesis.medicalapp.models.QuestionAnswer;
import com.thesis.medicalapp.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class QADTO {
    private String id;
    @NotEmpty
    @NotNull
    private String name;
    @NotEmpty
    @NotNull
    private String phone;
    @NotEmpty
    @NotNull
    private String demand;
    private Status status;

    public static QADTO from(QuestionAnswer questionAnswer) {
        return QADTO.builder()
                .id(questionAnswer.getId())
                .name(questionAnswer.getName())
                .phone(questionAnswer.getPhone())
                .demand(questionAnswer.getDemand())
                .status(questionAnswer.getStatus())
                .build();
    }
}
