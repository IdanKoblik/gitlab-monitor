package dev.idan.bgbot.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookProjectData {

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
}