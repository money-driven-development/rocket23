package com.initcloud.rocket23.example;

import com.initcloud.rocket23.example.entity.Users;
import com.initcloud.rocket23.example.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@Slf4j
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void insertTestData() {
        Users user = new Users();
        user.setUsername("kim ori");
        userRepository.save(user);

        user = new Users();
        user.setUsername("lee ori");
        userRepository.save(user);

        user = new Users();
        user.setUsername("kim ental");
        userRepository.save(user);

        user = new Users();
        user.setUsername("lee ental");
        userRepository.save(user);

        user = new Users();
        user.setUsername("kim samuel");
        userRepository.save(user);
    }

    @Test
    void findAllTest() {
        List<Users> userList = userRepository.findAll();
        for (Users u : userList) log.info("[FindAll]: " + u.getID() + " | " + u.getUsername());
    }

    @Test
    void find2ByNameTest() { // Like 검색으로 2개만 값을 가져오는 내가 작성한 명령을 실행해본다
        List<Users> userList = userRepository.findFirst2ByUsernameLikeOrderByIDDesc("kim%");
        for (Users u : userList) log.info("[FindSome]: " + u.getID() + " | " + u.getUsername());
    }
}
