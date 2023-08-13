package com.initcloud.rocket23.policy.controller;

import com.initcloud.rocket23.common.dto.ResponseDto;
import com.initcloud.rocket23.policy.dto.PolicyDto;
import com.initcloud.rocket23.policy.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class PolicyController {

    private final PolicyService policyService;

    @GetMapping("/{teamCode}/projects/{projectCode}/policies")
    public ResponseDto<Page<PolicyDto.Summary>> projectPolicies( // Todo 어떻게 표현할건지?
         final Pageable pageable,
         @PathVariable String teamCode,
         @PathVariable String projectCode
    ) {
        Page<PolicyDto.Summary> response = policyService.getProjectPolicyList(pageable, teamCode, projectCode);

        return new ResponseDto<>(response);
    }

    @GetMapping("/{teamCode}/projects/{projectCode}/policies/{policyName}")
    public ResponseDto<?> projectPolicyDetails(
            @PathVariable String teamCode,
            @PathVariable String projectCode,
            @PathVariable String policyName
    ) {

        PolicyDto.Details response = policyService.getProjectPolicyDetails(teamCode, projectCode, policyName);

        return new ResponseDto<>(response);
    }
}
