package com.initcloud.rocket23.policy.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.policy.dto.PolicyCreateDto;
import com.initcloud.rocket23.policy.dto.PolicyDto;
import com.initcloud.rocket23.policy.dto.PolicySetDto;
import com.initcloud.rocket23.policy.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Team Policy and Policy-set API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rocket/team")
public class PolicyController {

    private final PolicyService policyService;

    /**
     * ===== Team Policy =====
     */

    @Operation(
            summary = "Get paged policies",
            description = "Retrieve paged policies.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/policies")
    public ResponseDto<Page<PolicyDto.Summary>> teamPagedPolicies( // Todo 어떻게 표현할건지?
                                                                   final Pageable pageable,
                                                                   @PathVariable String teamCode
    ) {
        Page<PolicyDto.Summary> response = policyService.getPagedTeamPolicyList(pageable, teamCode);

        return new ResponseDto<>(response);
    }

    @Operation(
            summary = "Get all policies",
            description = "Retrieve all policies.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/policies/all")
    public ResponseDto<List<PolicyDto.Summary>> teamPolicies( // Todo 어떻게 표현할건지?
                                                              @PathVariable String teamCode
    ) {
        List<PolicyDto.Summary> response = policyService.getTeamPolicyList(teamCode);

        return new ResponseDto<>(response);
    }

    @Operation(
            summary = "Add a policy",
            description = "Add a policy.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @PostMapping("/{teamCode}/policies")
    public ResponseDto<String> teamPolicyAdd(
            @PathVariable String teamCode,
            @RequestBody PolicyCreateDto dto
    ) {
        String response = policyService.createTeamPolicy(teamCode, dto);

        return new ResponseDto<>(response);
    }

    @Operation(
            summary = "Get policy details",
            description = "Retrieve a policy.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "policyName", in = ParameterIn.PATH, description = "Unique policy name", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{teamCode}/policies/{policyName}")
    public ResponseDto<PolicyDto.Details> teamPolicyDetails(
            @PathVariable String teamCode,
            @PathVariable String policyName
    ) {
        PolicyDto.Details response = policyService.getTeamPolicyDetails(teamCode, policyName);

        return new ResponseDto<>(response);
    }

    @Operation(
            summary = "Modify a policy",
            description = "Modify policy details.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "policyName", in = ParameterIn.PATH, description = "Unique policy name", required = true, schema = @Schema(type = "string"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = PolicyCreateDto.class)), description = "Policy details", required = true)
    @PutMapping("/{teamCode}/policies/{policyName}")
    public ResponseDto<String> teamPolicyModify(
            @PathVariable String teamCode,
            @PathVariable String policyName,
            @org.springframework.web.bind.annotation.RequestBody PolicyCreateDto dto
    ) {
        String response = policyService.modifyTeamPolicy(teamCode, policyName, dto);

        return new ResponseDto<>(response);
    }

    @Operation(
            summary = "Remove a policy",
            description = "Remove a policy.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )}
    )
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "Access Token", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "teamCode", in = ParameterIn.PATH, description = "Team unique code", required = true, schema = @Schema(type = "string"))
    @Parameter(name = "policyName", in = ParameterIn.PATH, description = "Unique policy name", required = true, schema = @Schema(type = "string"))
    @DeleteMapping("/{teamCode}/policies/{policyName}")
    public ResponseDto<String> teamPolicyRemove(
            @PathVariable String teamCode,
            @PathVariable String policyName
    ) {
        String response = policyService.deleteTeamPolicy(teamCode, policyName);

        return new ResponseDto<>(response);
    }

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
    @GetMapping("/{teamCode}/policysets")
    public ResponseDto<List<String>> policySetList(
            @PathVariable String teamCode
    ) {
        List<String> response = policyService.getTeamPolicySetList(teamCode);

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
    @GetMapping("/{teamCode}/policysets/{policyset}")
    public ResponseDto<PolicySetDto> policySetDetails(
            @PathVariable String teamCode,
            @PathVariable String policyset
    ) {
        PolicySetDto response = policyService.getTeamPolicySetDetails(teamCode, policyset);

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
    @PostMapping("/{teamCode}/policysets")
    public ResponseDto<String> policySetAdd(
            @PathVariable String teamCode,
            @RequestBody PolicySetDto request
    ) {
        String response = policyService.createTeamPolicySet(teamCode, request);

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
    @PutMapping("/{teamCode}/policysets/{policySet}")
    public ResponseDto<String> policySetModify(
            @PathVariable String teamCode,
            @PathVariable String policySet,
            @RequestBody PolicySetDto request
    ) {
        String response = policyService.modifyTeamPolicySet(teamCode, policySet, request);

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
    @DeleteMapping("/{teamCode}/policysets/{policySet}")
    public ResponseDto policySetRemove(
            @PathVariable String teamCode,
            @PathVariable String policySet
    ) {
        policyService.deleteTeamPolicySet(teamCode, policySet);

        return new ResponseDto<>(null);
    }
}


