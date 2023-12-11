package dev.idan.bgbot.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class WebhookProjectData implements ProjectNameAndUrl {

    int id;

    String name;

    String description;

    @JsonProperty("web_url")
    String webUrl;

    @JsonProperty("avatar_url")
    String avatarUrl;

    @JsonProperty("git_ssh_url")
    String gitSshUrl;

    @JsonProperty("git_http_url")
    String gitHttpUrl;

    String namespace;

    @JsonProperty("visibility_level")
    int visibilityLevel;

    @JsonProperty("path_with_namespace")
    String pathWithNamespace;

    @JsonProperty("default_branch")
    String defaultBranch;

    @Override
    public String getProjectName() {
        return name;
    }

    @Override
    public String getProjectUrl() {
        return webUrl;
    }
}