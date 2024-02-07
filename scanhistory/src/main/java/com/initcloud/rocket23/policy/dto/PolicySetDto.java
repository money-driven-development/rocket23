package com.initcloud.rocket23.policy.dto;

import com.initcloud.rocket23.policy.entity.PolicySet;
import com.initcloud.rocket23.policy.entity.TeamPolicy;
import com.initcloud.rocket23.team.entity.Team;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PolicySetDto {

    private boolean isNameModified;
    private boolean isDescriptionModified;
    private String policySetName;
    private String description;
    private List<PolicyState> policyState = new ArrayList<>();

    @Getter
    public class PolicyState {
        private String policyName;
        private boolean state;

        public PolicyState(String policyName, boolean state) {
            this.policyName = policyName;
            this.state = state;
        }
    }

    @Getter
    public static class Summary {
        private final String policyName;
        private final String description;

        public Summary(String policyName, String description) {
            this.policyName = policyName;
            this.description = description;
        }
    }

    public PolicySetDto(final PolicySet policySet) {
        this.isNameModified = false;
        this.isDescriptionModified = false;
        this.policySetName = policySet.getName();
        this.description = policySet.getDescription();
        this.policyState = policySet.getPoliciesPerPolicySet()
                .stream()
                .map(policyPerPolicySet -> {
                    TeamPolicy policy = policyPerPolicySet.getTeamPolicy();
                    return new PolicyState(policy.getPolicyName(), policyPerPolicySet.isState());
                })
                .collect(Collectors.toList());
    }

    public PolicySetDto(boolean isNameModified, boolean isDescriptionModified, String policySetName, String description, List<PolicyState> policyState) {
        this.isNameModified = isNameModified;
        this.isDescriptionModified = isDescriptionModified;
        this.policySetName = policySetName;
        this.description = description;
        this.policyState = policyState;
    }

    public static PolicySetDto cannotModifyingPolicySetDto(String policySetName, String description, List<PolicyState> policyState) {
        return new PolicySetDto(false, false, policySetName, description, policyState);
    }

    public PolicySet toEntity(final Team team) {
        return new PolicySet(team, this.policySetName, this.description);
    }
}
