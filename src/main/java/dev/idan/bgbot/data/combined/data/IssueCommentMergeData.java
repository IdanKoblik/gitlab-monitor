package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.WebhookRepositoryData;
import dev.idan.bgbot.data.merge.MergeObjectAttributes;
import lombok.Getter;

@Getter
public abstract class IssueCommentMergeData extends WebhookData {

    @JsonProperty("event_type")
    String eventType;

    private IssueCommentMergePipelineUserData user;

    private WebhookProjectData project;

    private WebhookRepositoryData repository;

    @Override
    public String getAuthorName() {
        return user.getName();
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
}
