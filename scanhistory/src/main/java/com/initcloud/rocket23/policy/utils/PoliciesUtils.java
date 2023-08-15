package com.initcloud.rocket23.policy.utils;

import com.initcloud.rocket23.policy.dto.PolicySetDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PoliciesUtils {

    public static List<String> toPolicyNameList(List<PolicySetDto.PolicyState> state) {
        return state.stream()
                .map(PolicySetDto.PolicyState::getPolicyName)
                .collect(Collectors.toList());
    }
}
