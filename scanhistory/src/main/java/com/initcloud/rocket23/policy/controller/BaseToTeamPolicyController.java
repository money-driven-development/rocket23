package com.initcloud.rocket23.policy.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.policy.dto.PolicyDto;
import com.initcloud.rocket23.policy.dto.PolicyDto.Summary;
import com.initcloud.rocket23.policy.service.BaseToTeamPolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Base Policy register Team Policy API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rocket/baseToTeam")
public class BaseToTeamPolicyController {

    private final BaseToTeamPolicyService baseToTeamPolicyService;

    /**
     * ===== All BasePolicy Register Team =====
     */


    @Operation(
            summary = "Make Base Policy To Team Policy",
            description = "Register Base Policies",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/all")
    public ResponseDto<Page<Summary>> baseTeamPolicies(
                                                         @PathVariable String teamCode
    ) {
        baseToTeamPolicyService.basePolicyAllToTeamPolicy(teamCode);

        return new ResponseDto<>(null);
    }

    /**
     * ===== Add Base Policy Set =====
     */
    @Operation(
            summary = "Make Base Policy Set",
            description = "Register Base Policy Set",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/baseSet")
    public ResponseDto<Page<Summary>> baseTeamPolicySet(
            @PathVariable String teamCode
    ) {
        baseToTeamPolicyService.createBasePolicySet(teamCode);
        return new ResponseDto<>(null);
    }



}
