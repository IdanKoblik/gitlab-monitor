package dev.idan.bgbot.data.build;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@JsonTypeName("build")
public class BuildWebhookData extends WebhookData {

    @JsonProperty("build_id")
    long buildId;

    @JsonProperty("build_name")
    String buildName;

    @JsonProperty("build_stage")
    String buildStage;

    @JsonProperty("user")
    BuildUserWebhook user;

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
        return project.getWebUrl();
    }

    @Override
    public boolean sendEmbed() {
        return false;
    }

    @Override
    public void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel) {}
}
