package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.WebhookRepositoryData;
import lombok.Getter;

@Getter
public abstract class IssueCommentMergeData extends WebhookData {

    @JsonProperty("event_type")
    String eventType;

    @JsonProperty("user")
    private IssueCommentMergePipelineUserData user;

    @JsonProperty("project")
    private WebhookProjectData project;

    @JsonProperty("repository")
    private WebhookRepositoryData repository;

    @Override
    public String getAuthorName() {
        return user.getUsername();
    }

    @Override
    public String getAuthorAvatarUrl() {
        return user.getAvatarUrl();
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getProjectName() {
        return project.getProjectName();
    }

    @Override
    public String getProjectUrl() {
        return project.getWebUrl();
    }

    @Override
    public boolean sendEmbed() {
        return true;
    }
}
