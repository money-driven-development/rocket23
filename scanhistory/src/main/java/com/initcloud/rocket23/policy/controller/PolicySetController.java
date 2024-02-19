package com.initcloud.rocket23.policy.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.policy.dto.PolicySetDto;
import com.initcloud.rocket23.policy.service.TeamPolicyService;
import com.initcloud.rocket23.policy.service.TeamPolicySetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Policy-set API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rocket/team")
public class PolicySetController {

    private final TeamPolicySetService teamPolicySetService;


    /**
     * ===== Team PolicySet =====
     */

    @Operation(
            summary = "Get policy-set List",
            description = "Show policy-set List.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/policySets")
    public ResponseDto<List<PolicySetDto.Summary>> policySetList(
            @PathVariable String teamCode
    ) {
        List<PolicySetDto.Summary> response = teamPolicySetService.getTeamPolicySetList(teamCode);

        return new ResponseDto<>(response);
    }

    @Operation(
            summary = "Get policy-set",
            description = "Show policy-set.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "policyset", in = ParameterIn.PATH, description = "policy-set code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/policySets/{policySet}")
    public ResponseDto<PolicySetDto> policySetDetails(
            @PathVariable String teamCode,
            @PathVariable String policySet
    ) {
        PolicySetDto response = teamPolicySetService.getTeamPolicySetDetails(teamCode, policySet);

        return new ResponseDto<>(response);
    }


    @Operation(
            summary = "Add a policy set",
            description = "Add a policy set.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = PolicySetDto.class)), description = "Policy-set-add info.", required = true)
    @PostMapping("/{teamCode}/policySets")
    public ResponseDto<String> policySetAdd(
            @PathVariable String teamCode,
            @RequestBody PolicySetDto request
    ) {
        String response = teamPolicySetService.createTeamPolicySet(teamCode, request);

        return new ResponseDto<>(response);
    }

    @Operation(
            summary = "Modify a policy set",
            description = "Modify a policy set.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class)))}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "policySet", in = ParameterIn.PATH, description = "Unique policy set name", required = true, schema = @Schema(type = "string"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = PolicySetDto.class)), description = "Policy-set-modify info.", required = true)
    @PatchMapping("/{teamCode}/policySets/{policySet}")
    public ResponseDto<String> policySetModify(
            @PathVariable String teamCode,
            @PathVariable String policySet,
            @RequestBody PolicySetDto request
    ) {
        String response = teamPolicySetService.modifyTeamPolicySet(teamCode, policySet, request);

        return new ResponseDto<>(response);
    }

    @Operation(
            summary = "Remove a policy set",
            description = "Remove a policy set.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class)))}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "policySet", in = ParameterIn.PATH, description = "Unique policy set name", required = true, schema = @Schema(type = "string"))
    @DeleteMapping("/{teamCode}/policySets/{policySet}")
    public ResponseDto<Boolean> policySetRemove(
            @PathVariable String teamCode,
            @PathVariable String policySet
    ) {
        teamPolicySetService.deleteTeamPolicySet(teamCode, policySet);

        return new ResponseDto<>(true);
    }

}
