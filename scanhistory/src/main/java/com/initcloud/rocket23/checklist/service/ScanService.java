package com.initcloud.rocket23.checklist.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initcloud.rocket23.checklist.dto.ScoreDto;
import com.initcloud.rocket23.checklist.entity.scanHistory.CodeBlock;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistory;
import com.initcloud.rocket23.checklist.entity.scanHistory.ScanHistoryDetail;
import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.enums.State;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.infra.repository.CodeBlockRepository;
import com.initcloud.rocket23.infra.repository.ScanHistoryDetailRepository;
import com.initcloud.rocket23.infra.repository.ScanHistoryRepository;
import com.initcloud.rocket23.team.entity.Team;
import com.initcloud.rocket23.team.entity.TeamProject;
import com.initcloud.rocket23.team.service.TeamInspectService;
import com.initcloud.rocket23.team.service.TeamProjectService;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;
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

    private final ScanHistoryDetailRepository scanHistoryDetailRepository;
    private final ScanHistoryRepository scanHistoryRepository;
    private final CodeBlockRepository codeBlockRepository;

    private final ScanSeverityService scanSeverityService;

    public ScanHistory saveScanState(String data) throws Exception {;

        data = scanParseService.parseJSON(data);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(data);
        JSONObject jsonObj = (JSONObject) obj;

        Team team = teamInspectService.getTeam(jsonObj.get("team").toString());
        TeamProject teamProject = teamProjectService.getTeamProject(team, jsonObj.get("project").toString());

        ScanHistory scanHistory = ScanHistory.builder()
                .team(team)
                .project(teamProject)
                .projectName(teamProject.getProjectName())
                .projectCode(teamProject.getProjectCode())
                .username("username")
                .fileHash(jsonObj.get("uuid").toString())
                .state(State.SCANNING)
                .build();

        scanHistoryRepository.save(scanHistory);
        return scanHistory;

    }


    @Transactional
    public ScanHistory saveCheckovScan(String data) throws Exception {

        data = scanParseService.parseJSON(data);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(data);
        JSONObject jsonObj = (JSONObject) obj;

        if(Objects.equals(jsonObj.get("error").toString(), "false")){
            ScanHistory scanHistory = scanHistoryRepository.findByFileHash(jsonObj.get("fileHash").toString());
            JSONObject summaryObject = (JSONObject) jsonObj.get("summary");

            ScoreDto scoreDto = scanSeverityService.countSeverity(jsonObj);
            ScoreDto dto = scanSeverityService.getScore(scoreDto);

            scanHistory.updateScan(summaryObject, dto);
            scanHistoryRepository.save(scanHistory);
            saveScanHistoryDetails(jsonObj, scanHistory, "passed_checks");
            saveScanHistoryDetails(jsonObj, scanHistory, "failed_checks");

            return scanHistory;
        }
        else{
            saveScanError(data);
        }
        return null;
    }

    public ScanHistory saveScanError(String data) throws Exception {

        data = scanParseService.parseJSON(data);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(data);
        JSONObject jsonObj = (JSONObject) obj;

        ScanHistory scanHistory = scanHistoryRepository.findByFileHash(jsonObj.get("fileHash").toString());

        scanHistory.updateError();

        scanHistoryRepository.save(scanHistory);

        return scanHistory;

    }

    @Transactional
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

    @Transactional
    public void saveScanHistoryDetail(JsonNode checkNode, ScanHistory scanHistory) {
        try {
            ScanHistoryDetail scanHistoryDetail = ScanHistoryDetail.builder()
                    .ruleName(checkNode.get("check_id").asText().replace("CKV","IC"))
                    .scanHistory(scanHistory)
                    .ruleDescription(checkNode.get("check_name").asText())
                    .scanResult(checkNode.get("check_result").get("result").asText())
                    .scanResource(parseResource(checkNode.get("resource").asText()))
                    .targetFileName(checkNode.get("file_path").asText())
                    .appType("Terraform")
                    .build();
            scanHistoryDetailRepository.save(scanHistoryDetail);

            JsonNode codeBlockNodes = checkNode.path("code_block");
            saveCodeBlocks(codeBlockNodes, scanHistoryDetail);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ResponseCode.SCAN_DESCRIOTION_ERROR);
        }
    }

    private String parseResource(String postText){
        int index = postText.indexOf(".");
        if (index != -1) {
            return postText.substring(0, index);
        } else {
            return postText; // delimiter가 없는 경우 전체 문자열 반환
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