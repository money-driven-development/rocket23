package com.initcloud.rocket23.policy.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.policy.dto.PolicyDto;
import com.initcloud.rocket23.policy.dto.PolicySetDto;
import com.initcloud.rocket23.policy.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class PolicyController {

    private final PolicyService policyService;

    /**
     * ===== Team Policy =====
     */

    @GetMapping("/{teamCode}/policies")
    public ResponseDto<Page<PolicyDto.Summary>> teamPagedPolicies( // Todo 어떻게 표현할건지?
        final Pageable pageable,
        @PathVariable String teamCode
    ) {
        Page<PolicyDto.Summary> response = policyService.getPagedTeamPolicyList(pageable, teamCode);

        return new ResponseDto<>(response);
    }

    @GetMapping("/{teamCode}/policies/all")
    public ResponseDto<List<PolicyDto.Summary>> teamPolicies( // Todo 어떻게 표현할건지?
       @PathVariable String teamCode
    ) {
        List<PolicyDto.Summary> response = policyService.getTeamPolicyList(teamCode);

        return new ResponseDto<>(response);
    }

    @PostMapping("/{teamCode}/policies")
    public ResponseDto<String> teamPolicyAdd(
        @PathVariable String teamCode,
        @RequestBody PolicyDto.Create dto
    ) {
        String response = policyService.createTeamPolicy(teamCode, dto);

        return new ResponseDto<>(response);
    }

    @GetMapping("/{teamCode}/policies/{policyName}")
    public ResponseDto<PolicyDto.Details> teamPolicyDetails(
            @PathVariable String teamCode,
            @PathVariable String policyName
    ) {
        PolicyDto.Details response = policyService.getTeamPolicyDetails(teamCode, policyName);

        return new ResponseDto<>(response);
    }

    @PutMapping("/{teamCode}/policies/{policyName}")
    public ResponseDto<String> teamPolicyModify(
            @PathVariable String teamCode,
            @RequestBody PolicyDto.Create dto
    ) {
        String response = policyService.modifyTeamPolicy(teamCode, dto);

        return new ResponseDto<>(response);
    }

    @DeleteMapping("/{teamCode}/policies/{policyName}")
    public ResponseDto<String> teamPolicyDelete(
            @PathVariable String teamCode,
            @RequestBody String policyName
    ) {
        String response = policyService.deleteTeamPolicy(teamCode, policyName);

        return new ResponseDto<>(response);
    }

    /**
     * ===== Team PolicySet =====
     */

    @PostMapping("/{teamCode}/policysets")
    public ResponseDto<?> policySetAdd(
        @PathVariable String teamCode,
        @RequestBody PolicySetDto dto
    ) {
        String response = policyService.createTeamPolicySet(teamCode, dto);

        return new ResponseDto<>(response);
    }


    @PutMapping("/{teamCode}/policysets/{policySet}")
    public ResponseDto<?> policySetModify(
        @PathVariable String teamCode,
        @PathVariable String policySet,
        @RequestBody PolicySetDto dto
    ) {
        String response = policyService.modifyTeamPolicySet(teamCode, policySet, dto);

        return new ResponseDto<>(response);
    }

    @DeleteMapping("/{teamCode}/policysets/{policySet}")
    public ResponseDto<?> policySetDelete(
            @PathVariable String teamCode,
            @PathVariable String policySet
    ) {
        boolean response = policyService.deleteTeamPolicySet(teamCode, policySet);

        return new ResponseDto<>(response);
    }
}


