package dev.idan.bgbot.data.merge;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.combined.data.IssueCommentMergeData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@JsonTypeName("merge_request")
public class MergeWebhookData extends IssueCommentMergeData {

    @JsonProperty("object_attributes")
    private MergeObjectAttributes objectAttributes;

    @Override
    public void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel) {
        builder.setTitle(String.format("%s merge request from branch %s to branch %s",
                objectAttributes.getAction(),
                objectAttributes.getSourceBranch(),
                objectAttributes.getTargetBranch()
        ), objectAttributes.getUrl());
    }
}
