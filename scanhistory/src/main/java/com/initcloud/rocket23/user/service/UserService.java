package com.initcloud.rocket23.user.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiAuthException;
import com.initcloud.rocket23.infra.repository.TeamWithUsersRepository;
import com.initcloud.rocket23.infra.repository.UserRepository;
import com.initcloud.rocket23.security.provider.JwtProvider;
import com.initcloud.rocket23.team.entity.TeamWithUsers;
import com.initcloud.rocket23.user.dto.UserDto;
import com.initcloud.rocket23.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
     * 사용자 상세 정보 수정
     */
    public void modifyUserProfile() {

    }

    /**
     * 탈퇴
     */
    public void removeUserProfile() {

    }

    /**
     * 사용자 상태 변경
     */
    public void modifyUserStatus() {

    }

    public User loadUser() throws UsernameNotFoundException {
        return userRepository.findUserByUsername(jwtProvider.getUsername())
                .orElseThrow(
                        () -> new ApiAuthException(ResponseCode.INVALID_CREDENTIALS)
                );
    }
}
