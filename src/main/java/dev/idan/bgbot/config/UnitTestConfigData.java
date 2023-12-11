package dev.idan.bgbot.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UnitTestConfigData(
        @JsonProperty("secret_token")
        String secretToken,

        @JsonProperty("guild_id")
        String guildId,

        @JsonProperty("instance_url")
        String instanceUrl
) {
}
