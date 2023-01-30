package com.thesis.medicalapp.services.impl;

import com.thesis.medicalapp.models.File;
import com.thesis.medicalapp.repository.FileRepository;
import com.thesis.medicalapp.services.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    @Override
    public File store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File File = new File(fileName, file.getContentType(), file.getBytes());
        return fileRepository.save(File);
    }
    @Override
    public File getFile(String id) {
        return fileRepository.findById(id).get();
    }
    @Override
    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }
    @Override
    public int removeFile(String id) {
        try {
            fileRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            System.out.println("Error in profile service " + e.getMessage());
            return 0;
        }
    }
}
