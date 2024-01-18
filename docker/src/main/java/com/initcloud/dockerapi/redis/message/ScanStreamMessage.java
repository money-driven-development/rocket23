package com.initcloud.dockerapi.redis.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.initcloud.dockerapi.common.enums.ScanType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScanStreamMessage<T> implements Serializable {

    public enum MessageType {
        SCAN_COMPLETE, // 스캔 완료
        SCAN_FAULT // 스캔 실패
    }

    /* 파일 업로드 경로 */
    private String directory;
    private String teamCode;
    private String projectCode;
    private MessageType messageType;

    private Boolean hasBody;

    private T body;

    /**
     * 성공 시
     */
    public ScanStreamMessage(String directory, T body) {
        this.directory = directory;
        this.messageType = MessageType.SCAN_COMPLETE;
        this.hasBody = true;
        this.body = body;
    }

    /**
     * 실패 시
     */
    @Builder(builderClassName = "streamBuilder", builderMethodName = "redisStreamFailedBuilder")
    public ScanStreamMessage(String directory) {
        this.directory = directory;
        this.messageType = MessageType.SCAN_FAULT;
        this.hasBody = false;
        this.body = null;
    }

    /**
     * 실패 시 builder 를 통해 메시지 생성
     */
    public static ScanStreamMessage<Object> toFailedStreamMessage(String directory) {
        return ScanStreamMessage.redisStreamFailedBuilder()
                .directory(directory)
                .build();
    }

    public static String serializeToScanResult(String rawData, String teamCode, String projectCode, String fileHash)
            throws ParseException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(rawData);

        if(rootNode.isArray()){
            for (JsonNode node : rootNode) {
                if (node.isObject() && ScanType.TERRAFORM.getType().equals(node.get("check_type").asText())) {
                    ((ObjectNode) node).put("teamCode", teamCode);
                    ((ObjectNode) node).put("projectCode", projectCode);
                    ((ObjectNode) node).put("fileHash", fileHash);
                    return String.valueOf(node);
                }
            }
        }
        else{
            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(rawData);

            // 팀, 프로젝트 정보 추가
            jsonObject.put("teamCode", teamCode);
            jsonObject.put("projectCode", projectCode);
            jsonObject.put("fileHash", fileHash);

            return jsonObject.toJSONString();
        }

        return rawData;
    }
}
