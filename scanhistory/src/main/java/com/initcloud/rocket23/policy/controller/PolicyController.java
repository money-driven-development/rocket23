package com.initcloud.rocket23.policy.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.policy.dto.PolicyCreateDto;
import com.initcloud.rocket23.policy.dto.PolicyDto;
import com.initcloud.rocket23.policy.dto.PolicySetDto;
import com.initcloud.rocket23.policy.service.PolicyService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiOperation("Team Policy and Policy-set API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rocket/team")
public class PolicyController {

    private final PolicyService policyService;

    /**
     * ===== Team Policy =====
     */

    @ApiOperation(value = "Get paged policies", notes = "Retrieve paged policies.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class)})
    @GetMapping("/{teamCode}/policies")
    public ResponseDto<Page<PolicyDto.Summary>> teamPagedPolicies( // Todo 어떻게 표현할건지?
            final Pageable pageable,
            @PathVariable String teamCode
    ) {
        Page<PolicyDto.Summary> response = policyService.getPagedTeamPolicyList(pageable, teamCode);

        return new ResponseDto<>(response);
    }

    @ApiOperation(value = "Get all policies", notes = "Retrieve all policies.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class)})
    @GetMapping("/{teamCode}/policies/all")
    public ResponseDto<List<PolicyDto.Summary>> teamPolicies( // Todo 어떻게 표현할건지?
            @PathVariable String teamCode
    ) {
        List<PolicyDto.Summary> response = policyService.getTeamPolicyList(teamCode);

        return new ResponseDto<>(response);
    }

    @ApiOperation(value = "Add a policy", notes = "Add a policy.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class)})
    @PostMapping("/{teamCode}/policies")
    public ResponseDto<String> teamPolicyAdd(
            @PathVariable String teamCode,
            @RequestBody PolicyCreateDto dto
    ) {
        String response = policyService.createTeamPolicy(teamCode, dto);

        return new ResponseDto<>(response);
    }

    @ApiOperation(value = "Get policy details", notes = "Retrieve a policy.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "policyName", paramType = "path", value = "Unique policy name", required = true, dataTypeClass = String.class)})
    @GetMapping("/{teamCode}/policies/{policyName}")
    public ResponseDto<PolicyDto.Details> teamPolicyDetails(
            @PathVariable String teamCode,
            @PathVariable String policyName
    ) {
        PolicyDto.Details response = policyService.getTeamPolicyDetails(teamCode, policyName);

        return new ResponseDto<>(response);
    }

    @ApiOperation(value = "Modify a policy", notes = "Modify policy details.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "policyName", paramType = "path", value = "Unique policy name", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "dto", paramType = "body", value = "Policy details", required = true, dataTypeClass = PolicyCreateDto.class)})
    @PutMapping("/{teamCode}/policies/{policyName}")
    public ResponseDto<String> teamPolicyModify(
            @PathVariable String teamCode,
            @PathVariable String policyName,
            @RequestBody PolicyCreateDto dto
    ) {
        String response = policyService.modifyTeamPolicy(teamCode, policyName, dto);

        return new ResponseDto<>(response);
    }

    @ApiOperation(value = "Remove a policy", notes = "Remove a policy.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "policyName", paramType = "path", value = "Unique policy name", required = true, dataTypeClass = String.class)})
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

    @ApiOperation(value = "Get policy-set List", notes = "Show policy-set List.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class)})
    @GetMapping("/{teamCode}/policysets")
    public ResponseDto<List<String>> policySetList(
            @PathVariable String teamCode
    ) {
        List<String> response = policyService.getTeamPolicySetList(teamCode);

        return new ResponseDto<>(response);
    }


    @ApiOperation(value = "Add a policy set", notes = "Add a policy set.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "request", paramType = "body", value = "Policy-set-add info.", required = true, dataTypeClass = PolicySetDto.class)})
    @PostMapping("/{teamCode}/policysets")
    public ResponseDto<String> policySetAdd(
            @PathVariable String teamCode,
            @RequestBody PolicySetDto request
    ) {
        String response = policyService.createTeamPolicySet(teamCode, request);

        return new ResponseDto<>(response);
    }

    @ApiOperation(value = "Modify a policy set", notes = "Modify a policy set.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "policySet", paramType = "path", value = "Unique policy set name", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "request", paramType = "body", value = "Policy-set-modify info.", required = true, dataTypeClass = PolicySetDto.class)})
    @PutMapping("/{teamCode}/policysets/{policySet}")
    public ResponseDto<String> policySetModify(
            @PathVariable String teamCode,
            @PathVariable String policySet,
            @RequestBody PolicySetDto request
    ) {
        String response = policyService.modifyTeamPolicySet(teamCode, policySet, request);

        return new ResponseDto<>(response);
    }

    @ApiOperation(value = "Remove a policy set", notes = "Remove a policy set.", response = ResponseDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "teamCode", paramType = "path", value = "Team unique code", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "policySet", paramType = "path", value = "Unique policy set name", required = true, dataTypeClass = String.class)})
    @DeleteMapping("/{teamCode}/policysets/{policySet}")
    public ResponseDto policySetRemove(
            @PathVariable String teamCode,
            @PathVariable String policySet
    ) {
        policyService.deleteTeamPolicySet(teamCode, policySet);

        return new ResponseDto<>(null);
    }
}


