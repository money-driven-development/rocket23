package com.initcloud.rocket23.file.repository;

import com.initcloud.rocket23.file.entity.FileEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.File;
import java.util.List;

@DataJpaTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileRepositoryTest {

    @Autowired
    FileRepository fileRepository;

    /*
    Junit test가 모두 끝나면 실행.
     */
    @AfterAll
    public void cleanup() {
        fileRepository.deleteAll();
    }

    @Test
    public void test() {
        String name = "start";
        fileRepository.save(FileEntity.builder().filename(name).build());

        List<FileEntity> filelist = fileRepository.findAll();

        FileEntity entity = filelist.get(0);
        System.out.println(entity.getFilename());
    }
}
