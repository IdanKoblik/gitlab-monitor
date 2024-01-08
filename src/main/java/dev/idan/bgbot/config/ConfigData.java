package dev.idan.bgbot.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ConfigData(
        @JsonProperty("discord_token")
        String discordToken,

        @JsonProperty("webhook_url")
        String webhookURL,

        @JsonProperty("gitlab_url")
        String gitlabUrl,

        @JsonProperty("bot_id")
        long botId
) {

}
