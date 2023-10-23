package com.initcloud.rocket23.user.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.user.dto.UserDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
