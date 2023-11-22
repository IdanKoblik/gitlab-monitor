package dev.idan.bgbot.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.idan.bgbot.config.ConfigData;
import dev.idan.bgbot.entities.Project;
import dev.idan.bgbot.repository.ProjectRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ProjectService {

    @Autowired
    ConfigData configData;

    @Autowired
    ProjectRepository projectRepository;

    private final Map<String, ResponseEntity<String>> projectCache = new HashMap<>();

    public ResponseEntity<String> existsByProjectId(String projectId) {
        Optional<Project> projectOptional = projectRepository.findByProjectId(projectId);
        if (projectOptional.isEmpty()) return null;

        ResponseEntity<String> cachedResponse = projectCache.get(projectId);
        if (cachedResponse != null) return cachedResponse;

        String apiUrl = String.format("https://%s/api/v4/projects/%s", configData.gitlabUrl(), projectId);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("private-token", projectOptional.get().getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        projectCache.put(projectId, responseEntity);
        return responseEntity;
    }

    @SneakyThrows
    public String getProjectName(String projectId) {
        ResponseEntity<String> responseEntity = existsByProjectId(projectId);
        if (responseEntity == null) return null;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
        return jsonNode.get("name").asText();
    }
}
