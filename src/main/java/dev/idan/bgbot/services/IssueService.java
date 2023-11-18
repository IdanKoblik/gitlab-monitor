package dev.idan.bgbot.services;

import dev.idan.bgbot.config.ConfigData;
import dev.idan.bgbot.requests.CreateIssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IssueService {

    @Autowired
    ConfigData configData;

    public void createIssue(String projectId, String title, String description) {
        String apiUrl = String.format("https://%s/api/v4/projects/%s/issues", configData.gitlabUrl(), projectId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("private-token", configData.botAccessToken());

        CreateIssueRequest issueRequest = new CreateIssueRequest(title, description);
        HttpEntity<CreateIssueRequest> requestEntity = new HttpEntity<>(issueRequest, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
    }
}
