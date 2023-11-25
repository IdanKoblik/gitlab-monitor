package dev.idan.bgbot.services;

import dev.idan.bgbot.config.ConfigData;
import dev.idan.bgbot.entities.Project;
import dev.idan.bgbot.exceptions.DontConnectedException;
import dev.idan.bgbot.repository.ProjectRepository;
import dev.idan.bgbot.requests.CreateIssueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class IssueService {

    @Autowired
    ConfigData configData;

    @Autowired
    ProjectRepository projectRepository;

    public void createIssue(String projectId, String title, String description, long guildId) throws DontConnectedException {
        Optional<Project> projectOptional = projectRepository.findByGuildId(guildId);
        if (projectOptional.isEmpty())
            throw new DontConnectedException("You have not registered the issuer feature. ❌");

        Project project = projectOptional.get();
        if (project.getProjectId().equals(projectId)) {
            String apiUrl = String.format("https://%s/api/v4/projects/%s/issues", configData.gitlabUrl(), projectId);

            HttpHeaders headers = new HttpHeaders();
            headers.set("private-token", project.getAccessToken());

            CreateIssueRequest issueRequest = new CreateIssueRequest(title, description);
            HttpEntity<CreateIssueRequest> requestEntity = new HttpEntity<>(issueRequest, headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
        }

    }
}
