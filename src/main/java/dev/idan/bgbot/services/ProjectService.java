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

import java.util.Optional;

@Component
public class ProjectService {

    @Autowired
    ConfigData configData;

    @Autowired
    ProjectRepository projectRepository;

    private String buildApiUrl(String projectId) {
        return String.format("https://%s/api/v4/projects/%s", configData.gitlabUrl(), projectId);
    }

    public void getProjectFirstTime(String projectId, String accessToken) {
        String apiUrl = buildApiUrl(projectId);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("private-token", accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getProjectResponse(String projectId) throws Exception {
        Optional<Project> projectOptional = projectRepository.findByProjectId(projectId);
        if (projectOptional.isEmpty()) throw new Exception();

        String apiUrl = buildApiUrl(projectId);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("private-token", projectOptional.get().getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
    }

    @SneakyThrows
    public String getProjectName(String projectId) {
        ResponseEntity<String> responseEntity = getProjectResponse(projectId);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
        return jsonNode.get("name").asText();
    }
}
