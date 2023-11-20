package com.initcloud.rocket23.checklist.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.rocket23.checklist.entity.scanHistory.CodeBlock;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistory;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistoryDetail;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.CodeBlockRepository;
import com.initcloud.rocket23.infra.repository.ScanHistoryDetailRepository;
import com.initcloud.rocket23.infra.repository.ScanHistoryRepository;
import com.initcloud.rocket23.infra.repository.TeamProjectRepository;
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

    //@Transactional
    public ScanHistory saveCheckovScan(String data) throws Exception {

        data = scanParseService.parseJSON(data);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(data);
        JSONObject jsonObj = (JSONObject) obj;

        Team team = teamInspectService.getTeam(jsonObj.get("teamCode").toString());
        TeamProject teamProject = teamProjectService.getTeamProject(team, jsonObj.get("projectCode").toString());

        JSONObject summaryObject = (JSONObject) jsonObj.get("summary");

        ScanHistory scanHistory = ScanHistory.builder()
                .team(team)
                .project(teamProject)
                .projectName(teamProject.getProjectName())
                .projectCode(teamProject.getProjectCode())
                .username("username")
                .passed((int) summaryObject.get("passed"))
                .skipped((int) summaryObject.get("skipped"))
                .failed((int) summaryObject.get("failed"))
                .high(0) //TODO: 정책 추가 시 값 입력 필요
                .medium(0)
                .low(0)
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
}