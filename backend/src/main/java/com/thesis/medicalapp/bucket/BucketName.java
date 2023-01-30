package com.thesis.medicalapp.bucket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    AMAZON_S3_STORAGE("htnstorages");
    private final String bucketName;
}
