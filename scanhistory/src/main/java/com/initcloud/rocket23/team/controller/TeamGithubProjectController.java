package com.initcloud.rocket23.team.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.team.dto.GithubRepositoryDto;
import com.initcloud.rocket23.team.service.GithubAppService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@ApiOperation("Github Apps API")
@RestController
@RequestMapping("/rocket/github")
@RequiredArgsConstructor
public class TeamGithubProjectController {

    private final GithubAppService appService;

    @ApiOperation(value = "Redirect to Github App Register page.", notes = "Redirect to Github APP page to register App.")
    @GetMapping("/apps")
    public void githubAuthRedirect(HttpServletResponse response) {
        appService.redirectGithubApp(response);
    }

    @ApiOperation(value = "Github app Callback", notes = "Github app Callback.")
    @GetMapping("/apps/callback")
    public void githubAppsCallback() {

    }

    @GetMapping("/{owner}")
    public ResponseDto<GithubRepositoryDto> githubRepositoryList(
            @PathVariable String owner,
            @PathVariable String name
    ) {
        GithubRepositoryDto response = appService.getRepository(owner, name);

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
