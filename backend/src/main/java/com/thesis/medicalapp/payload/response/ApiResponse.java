package com.thesis.medicalapp.payload.response;

import com.thesis.medicalapp.models.Meta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T results;
    private Meta meta;
    @Autowired
    private static final ModelMapper modelMapper = new ModelMapper();

    public ApiResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.results = null;
        this.meta = null;
    }

    public ApiResponse(Integer code, String message, T results) {
        this.code = code;
        this.message = message;
        this.results = null;
        this.meta = null;
    }

    public ApiResponse(T results) {
        this.code = HttpStatus.OK.value();
        this.message = HttpStatus.OK.name();
        this.results = results;
        this.meta = null;
    }

    public ApiResponse(T results, T meta) {
        this.code = HttpStatus.OK.value();
        this.message = HttpStatus.OK.name();
        this.results = results;
        Meta metaResponse = modelMapper.map(meta, Meta.class);
        this.meta = metaResponse;
    }

    public ApiResponse(HttpStatus httpStatus, T results) {
        this.code = httpStatus.value();
        this.message = httpStatus.name();
        this.results = results;
        this.meta = null;
    }
}
