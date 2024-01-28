package com.initcloud.rocket23.policy.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasePolicySetDto {

    private String policyName;
    private boolean state;

    public BasePolicySetDto(String policyName, boolean state) {
        this.policyName = policyName;
        this.state = state;
    }

    public boolean getState() {
        return this.state;
    }
}
