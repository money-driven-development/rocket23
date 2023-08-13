package com.initcloud.rocket23.policy.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.TeamProjectPolicyRepository;
import com.initcloud.rocket23.infra.repository.TeamProjectRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.policy.dto.PolicyDto;
import com.initcloud.rocket23.policy.entity.TeamProjectPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyService {

    // Todo - 요거 나중에 Join 쿼리로 묶을 예정
    private final TeamRepository teamRepository;
    private final TeamProjectRepository teamProjectRepository;
    private final TeamProjectPolicyRepository teamProjectPolicyRepository;

    public Page<PolicyDto.Summary> getProjectPolicyList(final Pageable pageable, final String teamCode, final String projectCode) {
        Page<TeamProjectPolicy> teamProjectPolicies = teamProjectPolicyRepository.findTeamProjectPoliciesByTeam_TeamCodeAndTeamProject_ProjectCode(pageable, teamCode, projectCode);

        return PolicyDto.Summary.toPageDto(teamProjectPolicies);
    }

    public PolicyDto.Details getProjectPolicyDetails(final String teamCode, final String projectCode, final String policyName) {
        TeamProjectPolicy policy = teamProjectPolicyRepository.findTeamProjectPolicyByTeam_TeamCodeAndTeamProject_ProjectCodeAndPolicyNameIC(teamCode, projectCode, policyName)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_PROJECT_IN_TEAM));

        return new PolicyDto.Details(policy);
    }
}
