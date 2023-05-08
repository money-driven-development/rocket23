package com.initcloud.rocket23.file.repository;

import com.initcloud.rocket23.file.entity.FileList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Slf4j
public class FileRepositoryTest {

    @Autowired
    FileRepository fileRepository;

    @BeforeEach
    void insertTestData(){
        FileList fileList; //Setter 가 없어...
        //fileRepository.save(fileList);
    }
    @Test
    void findAllTest(){

    }
}
