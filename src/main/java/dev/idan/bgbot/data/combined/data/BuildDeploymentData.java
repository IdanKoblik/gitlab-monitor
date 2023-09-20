package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class BuildDeploymentData extends WebhookData {

    @JsonProperty("user")
    BuildDeploymentUserWebhook user;

    @JsonProperty("project")
    WebhookProjectData project;

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getAuthorName() {
        return user.getName();
    }

    @Override
    public String getAuthorAvatarUrl() {
        return user.getAvatarUrl();
    }

    @Override
    public String getProjectName() {
        return project.getProjectName();
    }

    @Override
    public String getProjectUrl() {
        return project.getProjectUrl();
    }

    @Override
    public boolean sendEmbed() {
        return false;
    }

    @Override
    public void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel) {}
}
