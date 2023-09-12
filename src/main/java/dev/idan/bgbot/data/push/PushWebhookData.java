package dev.idan.bgbot.data.push;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.WebhookRepositoryData;
import dev.idan.bgbot.data.combined.data.TagPushCommitData;
import dev.idan.bgbot.data.combined.data.TagPushPipelineCommitData;
import dev.idan.bgbot.data.combined.data.TagPushData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.List;

@JsonTypeName("push")
public class PushWebhookData extends TagPushData {

    @JsonProperty("user_username")
    String userUsername;

    @JsonProperty("user_email")
    String userEmail;

    @Override
    public String getEmail() {
        return userEmail;
    }
}
