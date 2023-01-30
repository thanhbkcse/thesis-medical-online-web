package com.thesis.medicalapp.services;

import com.thesis.medicalapp.models.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface FileService {
    File store(MultipartFile file) throws IOException;
    File getFile(String id);
    Stream<File> getAllFiles();
    int removeFile(String id);
}
