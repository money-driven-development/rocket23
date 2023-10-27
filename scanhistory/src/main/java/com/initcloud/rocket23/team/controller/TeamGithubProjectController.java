package com.initcloud.rocket23.team.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.team.dto.GithubRepositoryDto;
import com.initcloud.rocket23.team.service.GithubAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Github Apps API")
@RestController
@RequestMapping("/rocket/github")
@RequiredArgsConstructor
public class TeamGithubProjectController {

    private final GithubAppService appService;

    @Operation(summary = "Redirect to Github App Register page.", description = "Redirect to Github APP page to register App.")
    @GetMapping("/apps")
    public void githubAuthRedirect(HttpServletResponse response) {
        appService.redirectGithubApp(response);
    }

    @Operation(summary = "Github app Callback", description = "Github app Callback.")
    @GetMapping("/apps/callback")
    public void githubAppsCallback() {

    }

    @GetMapping("/{owner}")
    public ResponseDto<List<GithubRepositoryDto>> githubRepositoryList(
            @PathVariable String owner
    ) {
        List<GithubRepositoryDto> response = appService.getRepository(owner);

        return new ResponseDto<>(response);
    }

//    @GetMapping("/{owner}")
//    public ResponseDto<String> githubAccessToken(
//            @PathVariable String owner
//    ) {
//        String response = appService.getAccessToken(owner);
//
//        return new ResponseDto<>(response);
//    }
}
