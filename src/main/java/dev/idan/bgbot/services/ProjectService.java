package dev.idan.bgbot.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.idan.bgbot.config.ConfigData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProjectService {

    @Autowired
    ConfigData configData;

    public ResponseEntity<?> existsByProjectId(long projectId) {
        try {
            String apiUrl = String.format("https://%s/api/v4/projects/%d", configData.gitlabUrl(), projectId);
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("private-token", configData.botAccessToken());
            HttpEntity<String> entity = new HttpEntity<>(headers);

            restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @SneakyThrows
    public String getProjectName(long projectId) {
        String apiUrl = String.format("https://%s/api/v4/projects/%d", configData.gitlabUrl(), projectId);
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("private-token", configData.botAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());
        return jsonNode.get("name").asText();
    }
}
