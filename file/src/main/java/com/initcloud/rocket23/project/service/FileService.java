package com.initcloud.rocket23.project.service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public List<String> readAllFilesInDirectory(String teamCode, String projectCode, String fileHash) throws IOException {
        Path directoryPath = Paths.get(uploadPath, fileHash);
        List<String> fileContents = new ArrayList<>();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) {
            for (Path filePath : directoryStream) {
                if (Files.isRegularFile(filePath)) {
                    byte[] fileBytes = Files.readAllBytes(filePath);
                    fileContents.add(new String(fileBytes));
                }
            }
        }

        return fileContents;
    }

}
