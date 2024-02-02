package com.initcloud.rocket23.policy.service;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.BasePolicyRepository;
import com.initcloud.rocket23.infra.repository.PolicyPerPolicySetRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicyRepository;
import com.initcloud.rocket23.infra.repository.TeamPolicySetRepository;
import com.initcloud.rocket23.infra.repository.TeamRepository;
import com.initcloud.rocket23.policy.dto.BasePolicySetDto;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import com.initcloud.rocket23.policy.entity.PolicyPerPolicySet;
import com.initcloud.rocket23.policy.entity.PolicySet;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.service.TeamInspectService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BasePolicySetService {

    private final TeamRepository teamRepository;
    private final TeamPolicyRepository teamPolicyRepository;
    private final BasePolicyRepository basePolicyRepository;
    private final TeamPolicySetRepository teamPolicySetRepository;
    private final PolicyPerPolicySetRepository policyPerPolicySetRepository;
    private final TeamInspectService teamInspectService;

    public Team basePolicyAllToTeamPolicy(String teamCode){
        Team team = teamInspectService.getTeam(teamCode);

        List<TeamPolicy> teamPolicies = teamPolicyRepository.findByTeam(team);
        if (teamPolicies.isEmpty()) {
            List<BasePolicy> basePolicies = getBasePolicy();
            basePolicies.forEach(basePolicy -> basePolicyToTeamPolicy(team, basePolicy));
        }

        return team;

    }

    /**
     * Base Policy List를 가져오기
     * */

    public List<BasePolicy> getBasePolicy(){
        List<BasePolicy> basePolicies = basePolicyRepository.findAll();

        if(basePolicies.isEmpty()){ //Base Policy Table이 있는지 검증
            throw new ApiException(ResponseCode.INVALID_BASE_POLICY);
        }

        return basePolicies;
    }

    public void basePolicyToTeamPolicy(Team team, BasePolicy basePolicy){

        TeamPolicy teamPolicy = TeamPolicy.policyCreateBuilder()
                .team(team)
                .policyName(basePolicy.getDefaultPolicyNameIC())
                .basePolicyName(basePolicy.getDefaultPolicyName())
                .origin(true)
                .policyContent(basePolicy.getPolicyContent())
                .policyContentKr(basePolicy.getPolicyContentKr())
                .policyProvider(basePolicy.getPolicyProvider())
                .policyType(basePolicy.getPolicyType())
                .policyTarget(basePolicy.getPolicyTarget())
                .isModifiable(basePolicy.isModifiable())
                .explanation(basePolicy.getExplanation())
                .explanationKr(basePolicy.getExplanationKr())
                .severity(basePolicy.getSeverity())
                .solution(basePolicy.getSolution())
                .solutionCode(basePolicy.getSolutionCode())
                .insecureCode(basePolicy.getInsecureCode())
                .secureCode(basePolicy.getSecureCode())
                .build();

        teamPolicyRepository.save(teamPolicy);
    }

    /**
     * Base Policy 정책 셋 팀 추가
     */
    @Transactional
    public void createBasePolicySetList(final String teamCode) {

        createBasePolicySet(teamCode, "기본 제공되는 AWS(1) Base Policy Set 입니다.", "AWS1_Base_Policy_Set", "IC_AWS");
        createBasePolicySet(teamCode, "기본 제공되는 AWS(2) Base Policy Set 입니다.", "AWS2_Base_Policy_Set", "IC2_AWS");
        createBasePolicySet(teamCode, "기본 제공되는 AWS(3) Base Policy Set 입니다.", "AWS3_Base_Policy_Set", "IC3_AWS");
        createBasePolicySet(teamCode, "기본 제공되는 NCP Base Policy Set 입니다.", "NCP_Base_Policy_Set", "NCP");
    }

    /**
     * Base Policy 정책 셋 팀 추가
     */
    @Transactional
    public void createBasePolicySet(final String teamCode, String description, String name, String csp) {

        // 1. 대상 팀의 Base Policy를 Team Policy로 등록
        Team team = basePolicyAllToTeamPolicy(teamCode);

        // 2. 팀 정책 셋에 적용할 기본 정책들을 모아서
        List<TeamPolicy> baseTeamPolicies = teamPolicyRepository.findTeamPoliciesByTeam_Id(team.getId());

        PolicySet basePolicySet = PolicySet.builder()
                .team(team)
                .description(description)
                .name(name)
                .build();

        // 3. 정책 셋을 만들고
        PolicySet policySet = teamPolicySetRepository.save(basePolicySet);

        // 4. 정책 셋의 정책 목록에 정책 추가
        List<PolicyPerPolicySet> policiesPerPolicySet = new ArrayList<>();
        baseTeamPolicies.stream()
                .filter(policy -> policy.getPolicyName().contains(csp))
                .forEach(policy -> policiesPerPolicySet.add(new PolicyPerPolicySet(policySet, policy, true)));
        policyPerPolicySetRepository.saveAll(policiesPerPolicySet);
    }

    /**
     * 베이스 정책 셋 수정
     */
    public void modifyBasePolicySet(final String teamCode, final String policySet, final List<BasePolicySetDto> dto) {

        Team team = teamInspectService.getTeam(teamCode);

        // 1. 수정 대상 기존 Base 정책 셋을 찾고
        PolicySet originPolicySet = teamPolicySetRepository.findPolicySetByTeamAndName(team, policySet)
                .orElseThrow(() -> new ApiException(ResponseCode.INVALID_POLICY_SET_IN_TEAM));

        // 2. 수정 대상 정책 수정
        dto.forEach(policyState -> {
            TeamPolicy teamPolicy = teamPolicyRepository.findByTeam_TeamCodeAndPolicyName(teamCode, policyState.getPolicyName());

            PolicyPerPolicySet policyPerPolicySet = policyPerPolicySetRepository.findPolicyPerPolicySetByTeamPolicyAndPolicySet(teamPolicy, originPolicySet);

            policyPerPolicySet.updateState(policyState.getState());
            policyPerPolicySetRepository.save(policyPerPolicySet);
        });
    }


}
