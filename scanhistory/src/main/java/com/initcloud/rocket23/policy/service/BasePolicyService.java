package com.initcloud.rocket23.policy.service;

import com.initcloud.rocket23.infra.repository.BasePolicyRepository;
import com.initcloud.rocket23.policy.dto.BasePolicyDto;
import com.initcloud.rocket23.policy.dto.BasePolicyDto.Summary;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import java.util.List;
import java.util.stream.Collectors;
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
     * Base Policy Summary 전체 조회(페이징 X)
     */
    public List<BasePolicyDto.Summary> getBasePolicy() {
       List<BasePolicy> basePolicies = basePolicyRepository.findAll();
       return basePolicies.stream().map(BasePolicyDto.Summary::new).collect(Collectors.toList());
    }


    /**
     * Base Policy Details 전체 조회(페이징)
     */
    public Page<BasePolicyDto.Details> getPagedBasePolicyDetails(final Pageable pageable) {
        Page<BasePolicy> basePolicies = basePolicyRepository.findAll(pageable);

        return BasePolicyDto.Details.toPageDto(basePolicies);
    }

    /**
     * Base Policy Details 전체 조회(페이징 X)
     */
    public List<BasePolicyDto.Details> getBasePolicyDetails() {
        List<BasePolicy> basePolicies = basePolicyRepository.findAll();

        return basePolicies.stream().map(BasePolicyDto.Details::new).collect(Collectors.toList());
    }

}
