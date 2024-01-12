package com.initcloud.rocket23.policy.service;

import com.initcloud.rocket23.infra.repository.BasePolicyRepository;
import com.initcloud.rocket23.policy.dto.BasePolicyDto;
import com.initcloud.rocket23.policy.dto.PolicyDto;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasePolicyService {

    private final BasePolicyRepository basePolicyRepository;

    /**
     * Base Policy Summary 전체 조회(페이징)
     */
    public Page<BasePolicyDto.Summary> getPagedBasePolicy(final Pageable pageable) {
        Page<BasePolicy> basePolicies = basePolicyRepository.findAll(pageable);

        return BasePolicyDto.Summary.toPageDto(basePolicies);
    }


    /**
     * Base Policy Details 전체 조회(페이징)
     */
    public Page<BasePolicyDto.Details> getPagedBasePolicyDetails(final Pageable pageable) {
        Page<BasePolicy> basePolicies = basePolicyRepository.findAll(pageable);

        return BasePolicyDto.Details.toPageDto(basePolicies);
    }

}
