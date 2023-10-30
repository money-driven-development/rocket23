package com.initcloud.rocket23.checklist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScanParseService {

    public String parseJSON(String data) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();

            JsonNode rootNode = objectMapper.readTree(data);
            JsonNode resultsNode = rootNode.path("results");
            JsonNode passedChecksNode = resultsNode.path("passed_checks");
            JsonNode failedChecksNode = resultsNode.path("failed_checks");

            processCodeBlocks(passedChecksNode, objectMapper);
            processCodeBlocks(failedChecksNode, objectMapper);

            String improvedJsonData = writer.writeValueAsString(rootNode);
            return improvedJsonData;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void processCodeBlocks(JsonNode checksNode, ObjectMapper objectMapper) {
        if (checksNode.isArray()) {
            for (JsonNode checkNode : checksNode) {
                JsonNode codeBlock = checkNode.path("code_block");
                if (codeBlock.isArray()) {
                    ArrayNode improvedCodeBlock = objectMapper.createArrayNode();
                    for (JsonNode codeItem : codeBlock) {
                        if (codeItem.isArray() && codeItem.size() == 2) {
                            int line = codeItem.get(0).asInt();
                            String content = codeItem.get(1).asText();
                            ObjectNode improvedCodeItem = objectMapper.createObjectNode();
                            improvedCodeItem.put("line", line);
                            improvedCodeItem.put("content", content);
                            improvedCodeBlock.add(improvedCodeItem);
                        }
                    }
                    ((ObjectNode) checkNode).set("code_block", improvedCodeBlock);
                }
            }
        }
    }


}
