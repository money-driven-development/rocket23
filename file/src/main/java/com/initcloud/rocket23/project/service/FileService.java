package com.initcloud.rocket23.project.service;

import com.initcloud.rocket23.project.dto.FileDto;
import com.initcloud.rocket23.project.enums.ProjectType;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public FileDto readAllFilesInDirectory(String teamCode, String projectCode, String fileHash) throws IOException {
        Path directoryPath = Paths.get(uploadPath, fileHash);

        String fileName = "";
        String code = "";

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) {
            for (Path filePath : directoryStream) {
                if (Files.isRegularFile(filePath)) {
                    fileName = filePath.getFileName().toString();
                    byte[] fileBytes = Files.readAllBytes(filePath);
                    code = new String(fileBytes);
                }
            }
        }

        return FileDto.toDto(fileName, directoryPath, ProjectType.LOCAL, code);
    }

}
