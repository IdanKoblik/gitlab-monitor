package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueCommentMergePipelineUserData {

    int id;

    String name;

    String username;

    @JsonProperty("avatar_url")
    String avatarUrl;

    String email;
}
