package dev.idan.bgbot.data.merge;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.WebhookRepositoryData;
import dev.idan.bgbot.data.combined.data.IssueCommentMergeData;
import dev.idan.bgbot.data.combined.data.IssueCommentMergePipelineUserData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@JsonTypeName("merge")
public class MergeWebhookData extends IssueCommentMergeData {

    @JsonProperty("object_attributes")
    private MergeObjectAttributes objectAttributes;

    @Override
    public void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel) {
        builder.setTitle(String.format("%s %s merge request from %s to %s",
                getUser(),
                objectAttributes.getAction(),
                objectAttributes.getSourceBranch(),
                objectAttributes.getTargetBranch()
        ), objectAttributes.getUrl());
    }
}
