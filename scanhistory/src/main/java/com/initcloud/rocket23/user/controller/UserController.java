package com.initcloud.rocket23.user.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.user.dto.AuthRequestDto;
import com.initcloud.rocket23.user.dto.UserDto;
import com.initcloud.rocket23.user.enums.UserState;
import com.initcloud.rocket23.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Info API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rocket/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "User Profile", description = "Show user's individual profile.")
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Authorization Code", required = true, schema = @Schema(implementation = UserDto.Profile.class))
    @GetMapping()
    public ResponseDto<UserDto.Profile> profileDetails() {
        UserDto.Profile response = userService.getUserDetails();

        return new ResponseDto<>(response);
    }

    // 내가 속한 팀 목록
    @Operation(summary = "User's Team List", description = "Show user's teams.")
    @Parameter(
            name = "Authorization",
            in = ParameterIn.HEADER,
            description = "Authorization Code",
            required = true,
            content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))
    )
    @GetMapping("teams")
    public ResponseDto<List<UserDto.MyTeam>> userTeams() {
        List<UserDto.MyTeam> response = userService.getUserTeamList();

        return new ResponseDto<>(response);
    }
    // 유저 생성(회원 가입)
    @Operation(summary = "create User", description = "Make a Users")
    @Parameter(
            name = "Authorization",
            in = ParameterIn.HEADER,
            description = "Authorization Code",
            required = true,
            content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))
    )
    @PostMapping
    public ResponseDto<Boolean> createUsers(@RequestBody UserDto.UserInfo request) {
        userService.createUser(request);

        return new ResponseDto<>(true);
    }

    // 유저 정보 수정
    @Operation(summary = "modify User", description = "update user info")
    @Parameter(
            name = "username",
            in = ParameterIn.PATH,
            description = "username",
            required = true,
            content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))
    )
    @PatchMapping("/{username}")
    public ResponseDto<Boolean> modifyUsers(
            @PathVariable String username,
            @RequestBody UserDto.modifyUser request) {
        userService.modifyUserProfile(username, request);

        return new ResponseDto<>(true);
    }

    // 유저 상태 변경
    @Operation(summary = "modify User Status", description = "update user status")
    @Parameter(
            name = "username",
            in = ParameterIn.PATH,
            description = "username",
            required = true,
            content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))
    )
    @PatchMapping("/{username}/state")
    public ResponseDto<Boolean> modifyUserState(
            @PathVariable String username,
            @RequestBody UserState request) {
        userService.modifyUserStatus(username, request);

        return new ResponseDto<>(true);
    }

    // 유저 정보 삭제 상태 변경
    @Operation(summary = "delete User", description = "delete user info")
    @Parameter(
            name = "username",
            in = ParameterIn.PATH,
            description = "username",
            required = true,
            content = @Content(array = @ArraySchema(schema = @Schema(type = "string")))
    )
    @DeleteMapping("/{username}")
    public ResponseDto<Boolean> deleteUsers(
            @PathVariable String username) {
        userService.removeUserProfile(username);

        return new ResponseDto<>(true);
    }
}
