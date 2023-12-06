package com.initcloud.rocket23.checklist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.rocket23.checklist.dto.ScoreDto;
import com.initcloud.rocket23.checklist.entity.scanHistory.CodeBlock;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistory;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistoryDetail;
import com.initcloud.rocket23.common.enums.Policy.Severity;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.BasePolicyRepository;
import com.initcloud.rocket23.infra.repository.CodeBlockRepository;
import com.initcloud.rocket23.infra.repository.ScanHistoryDetailRepository;
import com.initcloud.rocket23.infra.repository.ScanHistoryRepository;
import com.initcloud.rocket23.infra.repository.TeamProjectRepository;
import com.initcloud.rocket23.policy.entity.BasePolicy;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import com.initcloud.rocket23.team.service.TeamInspectService;
import com.initcloud.rocket23.team.service.TeamProjectService;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScanService {

    private final ScanParseService scanParseService;
    private final TeamInspectService teamInspectService;
    private final TeamProjectService teamProjectService;

    private final TeamProjectRepository teamProjectRepository;
    private final ScanHistoryDetailRepository scanHistoryDetailRepository;
    private final ScanHistoryRepository scanHistoryRepository;
    private final CodeBlockRepository codeBlockRepository;
    private final BasePolicyRepository basePolicyRepository;

    //@Transactional
    public ScanHistory saveCheckovScan(String data) throws Exception {

        data = scanParseService.parseJSON(data);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(data);
        JSONObject jsonObj = (JSONObject) obj;

        Team team = teamInspectService.getTeam(jsonObj.get("teamCode").toString());
        TeamProject teamProject = teamProjectService.getTeamProject(team, jsonObj.get("projectCode").toString());

        JSONObject summaryObject = (JSONObject) jsonObj.get("summary");

        ScoreDto scoreDto = countSeverity(jsonObj);

        ScanHistory scanHistory = ScanHistory.builder()
                .team(team)
                .project(teamProject)
                .projectName(teamProject.getProjectName())
                .projectCode(teamProject.getProjectCode())
                .username("username")
                .passed((int) summaryObject.get("passed"))
                .skipped((int) summaryObject.get("skipped"))
                .failed((int) summaryObject.get("failed"))
                .high(scoreDto.getSuccessHigh() + scoreDto.getFailHigh()) //TODO: 정책 추가 시 값 입력 필요
                .medium(scoreDto.getSuccessMedium() + scoreDto.getFailMedium())
                .low(scoreDto.getSuccessLow() + scoreDto.getFailLow())
                .unknown(0)
                .score(0.0)
                .build();
        scanHistoryRepository.save(scanHistory);

        saveScanHistoryDetails(jsonObj, scanHistory, "passed_checks");
        saveScanHistoryDetails(jsonObj, scanHistory, "failed_checks");

        return scanHistory;

    }

    public void saveScanHistoryDetails(JSONObject jsonObj, ScanHistory scanHistory,
                                       String selectName) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        rootNode = objectMapper.readTree(jsonObj.toString());

        JsonNode passedChecksNode = rootNode.path("results").path(selectName);

        Stream<JsonNode> checkNodeStream = StreamSupport.stream(passedChecksNode.spliterator(), false);

        // 각 checkNode에 대해 saveScanHistoryDetail을 호출
        checkNodeStream.forEach(checkNode -> saveScanHistoryDetail(checkNode, scanHistory));

    }

    private void saveScanHistoryDetail(JsonNode checkNode, ScanHistory scanHistory) {
        try {
            ScanHistoryDetail scanHistoryDetail = ScanHistoryDetail.builder()
                    .ruleName(checkNode.get("check_id").asText())
                    .scanHistory(scanHistory)
                    .ruleDescription(checkNode.get("check_name").asText())
                    .scanResult(checkNode.get("check_result").get("result").asText())
                    .targetFileName(checkNode.get("file_path").asText())
                    .appType("Terraform")
                    .build();

            scanHistoryDetailRepository.save(scanHistoryDetail);

            JsonNode codeBlockNodes = checkNode.path("code_block");
            saveCodeBlocks(codeBlockNodes, scanHistoryDetail);

        } catch (Exception e) {
            throw new ApiException(ResponseCode.SCAN_DESCRIOTION_ERROR);
        }
    }

    private void saveCodeBlocks(JsonNode codeBlockNodes, ScanHistoryDetail scanHistoryDetail) {
        try {
            codeBlockNodes.forEach(codeNode -> {
                CodeBlock codeBlock = CodeBlock.builder()
                        .line(codeNode.get("line").asInt())
                        .content(codeNode.get("content").asText())
                        .scanHistoryDetail(scanHistoryDetail)
                        .build();

                codeBlockRepository.save(codeBlock);
            });
        } catch (Exception e) {
            throw new ApiException(ResponseCode.SCAN_CODE_BLOCK_ERROR);
        }
    }

    /**
     * check_id에 따른 severity 및 score count
     * */
    private ScoreDto countSeverity(JSONObject jsonObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        rootNode = objectMapper.readTree(jsonObj.toString());
        JsonNode passedChecksNode = rootNode.path("results").path("passed_checks"); //pass, fail에 따른 루트 차이 O

        ScoreDto scoreDto = new ScoreDto();

        for (JsonNode passedCheck : passedChecksNode) {
            String checkId = passedCheck.path("check_id").asText();
            checkId = ckvToIc(checkId);
            BasePolicy basePolicy = basePolicyRepository.findByDefaultPolicyNameIC(checkId);
            if (basePolicy != null) {
                switch (basePolicy.getSeverity()) {
                    case high:
                        scoreDto.incrementSuccessHigh();
                        break;
                    case medium:
                        scoreDto.incrementSuccessMedium();
                        break;
                    case low:
                        scoreDto.incrementSuccessLow();
                        break;
                }
            }
        }

        JsonNode failedChecksNode = rootNode.path("results").path("failed_checks"); //pass, fail에 따른 루트 차이 O

        for (JsonNode passedCheck : failedChecksNode) {
            String checkId = passedCheck.path("check_id").asText();
            checkId = ckvToIc(checkId);
            System.out.println("===========");
            BasePolicy basePolicy = basePolicyRepository.findByDefaultPolicyNameIC(checkId);
            if (basePolicy != null) {
                switch (basePolicy.getSeverity()) {
                    case high:
                        scoreDto.incrementFailHigh();
                        break;
                    case medium:
                        scoreDto.incrementFailMedium();
                        break;
                    case low:
                        scoreDto.incrementFailLow();
                        break;
                }
            }
        }

        return scoreDto;

    }

    /**
     * check_id가 CKV인 경우와 IC Policy Name을 찾도록 하는 메서드
     * */
    public String ckvToIc(String policyName){

        System.out.println("policyName: " + policyName);

        // "CKV"가 포함되어 있는지 확인
        if (policyName.contains("CKV")) {
            try{
                BasePolicy basePolicy = basePolicyRepository.findByDefaultPolicyName(policyName);
                System.out.println("IC: "+basePolicy.getDefaultPolicyNameIC());
                return  basePolicy.getDefaultPolicyNameIC();
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        return policyName;

    }
}