package com.initcloud.rocket23.checklist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.stream.StreamSupport;
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
            checksNode.forEach(checkNode -> {
                JsonNode codeBlock = checkNode.path("code_block");
                if (codeBlock.isArray()) {
                    ArrayNode improvedCodeBlock = StreamSupport.stream(codeBlock.spliterator(), false)
                            .filter(codeItem -> codeItem.isArray() && codeItem.size() == 2)
                            .map(codeItem -> {
                                int line = codeItem.get(0).asInt();
                                String content = codeItem.get(1).asText();
                                ObjectNode improvedCodeItem = objectMapper.createObjectNode();
                                improvedCodeItem.put("line", line);
                                improvedCodeItem.put("content", content);
                                return improvedCodeItem;
                            })
                            .collect(objectMapper::createArrayNode, ArrayNode::add, ArrayNode::addAll);
                    ((ObjectNode) checkNode).set("code_block", improvedCodeBlock);
                }
            });
        }
    }


}
