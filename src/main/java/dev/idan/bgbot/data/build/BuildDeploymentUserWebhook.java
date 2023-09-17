package dev.idan.bgbot.data.build;

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
