package com.initcloud.rocket23.user.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiAuthException;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.TeamWithUsersRepository;
import com.initcloud.rocket23.infra.repository.UserRepository;
import com.initcloud.rocket23.security.provider.JwtProvider;
import com.initcloud.rocket23.team.entity.TeamWithUsers;
import com.initcloud.rocket23.user.dto.UserDto;
import com.initcloud.rocket23.user.entity.User;
import com.initcloud.rocket23.user.enums.UserState;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtProvider jwtProvider;
    private final TeamWithUsersRepository teamWithUsersRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 목록 조회
     * Todo - 이건 유저 레벨에서 제공할 API 는 아닐듯 함.
     */
    public void getUserList() {

    }

    /**
     * 사용자 상세 정보 조회
     */
    public UserDto.Profile getUserDetails() {
        User user = loadUser();

        return new UserDto.Profile(user);
    }

    /**
     * 사용자가 속한 자신의 팀 목록 조회
     */
    @Transactional(readOnly = true)
    public List<UserDto.MyTeam> getUserTeamList() {
        User user = loadUser();

        List<TeamWithUsers> teams = teamWithUsersRepository.findTeamWithUsersByUser(user);

        List<UserDto.MyTeam> myTeams = teams.stream()
                .map(teamWithUsers -> new UserDto.MyTeam(teamWithUsers.getTeam()))
                .collect(Collectors.toList());

        return myTeams;
    }

    /**
     * 유저 생성(회원가입)
     * */
    public void createUser(UserDto.UserInfo userInfo) throws ApiException {
        // USER name 중복 검증
        if (userRepository.existsByUsername(userInfo.getUsername())) {
            throw new ApiException(ResponseCode.DUPLICATE_USER);
        }

        //password 유효성 검증
        if(!isValidPassword(userInfo.getPassword())){
            throw new ApiException(ResponseCode.INVALID_PASSWORD);
        }

        User user = userInfo.toDto(userInfo.getUserState(),
                userInfo.getUsername(),
                encodePassword(userInfo.getPassword()),
                userInfo.getEmail(),
                userInfo.getContact(),
                userInfo.getProfileImageUrl());

        userRepository.save(user);
    }

    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 패스워드 검증
     * */
    private boolean isValidPassword(String password) {
        // 비밀번호가 숫자, 영어, 특수문자로 구성되었는지 확인하는 정규표현식
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        // 패턴과 매칭
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }


    /**
     * 사용자 상세 정보 수정
     */
    public void modifyUserProfile(String username, UserDto.modifyUser modifyUser) {
        User user = userRepository.findByUsername(username);
        user.modifyUser(modifyUser.getProfileImageUrl(), modifyUser.getContact(), modifyUser.getEmail());
        userRepository.save(user);
    }

    /**
     * 탈퇴
     */
    public void removeUserProfile(String userName) {
        User user = userRepository.findByUsername(userName);
        user.modifyState(UserState.DELETED);
        userRepository.save(user);
    }

    /**
     * 사용자 상태 변경
     */
    public void modifyUserStatus(String userName, UserState userState) {
        User user = userRepository.findByUsername(userName);
        user.modifyState(userState);
        userRepository.save(user);
    }


    public User loadUser() throws UsernameNotFoundException {
        return userRepository.findUserByUsername(jwtProvider.getUsername())
                .orElseThrow(
                        () -> new ApiAuthException(ResponseCode.INVALID_CREDENTIALS)
                );
    }
}
