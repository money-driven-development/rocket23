package com.initcloud.rocket23.checklist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.rocket23.checklist.dto.ScoreDto;
import com.initcloud.rocket23.infra.repository.BasePolicyRepository;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScanSeverityService {

    private final BasePolicyRepository basePolicyRepository;

    /**
     * check_id에 따른 severity 및 score count
     * */
    public ScoreDto countSeverity(JSONObject jsonObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        rootNode = objectMapper.readTree(jsonObj.toString());

        ScoreDto scoreDto = new ScoreDto();

        JsonNode passedChecksNode = rootNode.path("results").path("passed_checks"); //pass, fail에 따른 루트 차이 O
        JsonNode failedChecksNode = rootNode.path("results").path("failed_checks"); //pass, fail에 따른 루트 차이 O

        countSeverityForChecks(passedChecksNode, scoreDto, true);
        countSeverityForChecks(failedChecksNode, scoreDto, false);

        return scoreDto;

    }

    private void countSeverityForChecks(JsonNode checksNode, ScoreDto scoreDto, boolean isPassed) {
        checksNode.forEach(check -> {
            String checkId = check.path("check_id").asText();
            checkId = ckvToIc(checkId);
            BasePolicy basePolicy = basePolicyRepository.findByDefaultPolicyNameIC(checkId);

            if (basePolicy != null) {
                switch (basePolicy.getSeverity()) {
                    case high:
                        if (isPassed) {
                            scoreDto.incrementSuccessHigh();
                            break;
                        } else {
                            scoreDto.incrementFailHigh();
                            break;
                        }
                    case medium:
                        if (isPassed) {
                            scoreDto.incrementSuccessMedium();
                            break;
                        } else {
                            scoreDto.incrementFailMedium();
                            break;
                        }
                    case low:
                        if (isPassed) {
                            scoreDto.incrementSuccessLow();
                            break;
                        } else {
                            scoreDto.incrementFailLow();
                            break;
                        }
                }
            }
        });
    }


    /**
     * check_id가 CKV인 경우와 IC Policy Name을 찾도록 하는 메서드
     * */
    public String ckvToIc(String policyName){

        // "CKV"가 포함되어 있는지 확인
        if (policyName.contains("CKV")) {
            BasePolicy basePolicy = basePolicyRepository.findByDefaultPolicyName(policyName);
            return  basePolicy.getDefaultPolicyNameIC();
        }
        return policyName;

    }

    public ScoreDto getScore(ScoreDto dto){
        // 분자 계산
        int numerator = dto.getSuccessHigh() * 3
                + dto.getSuccessMedium() * 2
                + dto.getSuccessLow();

        // 분모 계산
        int denominator = (dto.getSuccessHigh() + dto.getFailHigh()) * 3
                + (dto.getSuccessMedium() + dto.getFailMedium()) * 2
                + dto.getSuccessLow() + dto.getFailLow();

        double rawScore = (double) numerator / denominator;
        double score = Math.round(rawScore * 100.0);
        return new ScoreDto(dto,score);

    }

}
