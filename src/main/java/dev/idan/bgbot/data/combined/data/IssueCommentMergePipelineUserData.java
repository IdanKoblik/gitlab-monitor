package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class IssueCommentMergePipelineUserData {

    @JsonProperty("id")
    int id;

    @JsonProperty("name")
    String name;

    @JsonProperty("username")
    String username;

    @JsonProperty("avatar_url")
    String avatarUrl;

    @JsonProperty("email")
    String email;
}
