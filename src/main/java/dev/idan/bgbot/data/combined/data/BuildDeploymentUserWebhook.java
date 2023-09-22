package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BuildDeploymentUserWebhook {

    int id;

    String name;

    String email;

    @JsonProperty("avatar_url")
    String avatarUrl;
}
