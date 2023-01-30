package com.thesis.medicalapp.search;

import com.thesis.medicalapp.exception.ApiRequestException;
import com.thesis.medicalapp.models.Gender;
import com.thesis.medicalapp.models.SpecialtyType;
import com.thesis.medicalapp.models.Status;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public enum FieldType {
    BOOLEAN {
        public Object parse(String value) {
            return Boolean.valueOf(value);
        }
    },

    CHAR {
        public Object parse(String value) {
            return value.charAt(0);
        }
    },

    DATE {
        public Object parse(String value) {
            Object date = null;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                date = LocalDateTime.parse(value, formatter);
            } catch (Exception e) {
                log.info("Failed parse field type DATE {}", e.getMessage());
            }

            return date;
        }
    },

    DOUBLE {
        public Object parse(String value) {
            return Double.valueOf(value);
        }
    },

    INTEGER {
        public Object parse(String value) {
            return Integer.valueOf(value);
        }
    },

    LONG {
        public Object parse(String value) {
            return Long.valueOf(value);
        }
    },

    STRING {
        public Object parse(String value) { return value; }
    },

    SPECIALTY {
        public Object parse(String value) {
            for (SpecialtyType type : SpecialtyType.values()) {
                if (value.equals(type.toString())) {
                    return type;
                }
            }
            throw new ApiRequestException("Không tìm thấy chuyên khoa!");
        }
    },

    STATUS {
        public Object parse(String value) {
            for (Status status : Status.values()) {
                if (value.equals(status.toString())) {
                    return status;
                }
            }
            throw new ApiRequestException("Không tìm thấy chuyên khoa!");
        }
    },

    GENDER {
        public Object parse(String value) {
            for (Gender type : Gender.values()) {
                if (value.equals(type.toString())) {
                    return type;
                }
            }
            throw new ApiRequestException("Không tìm thấy giới tính!");
        }
    };

    public abstract Object parse(String value);
}
