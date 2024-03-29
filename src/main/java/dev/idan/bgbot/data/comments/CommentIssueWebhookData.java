package dev.idan.bgbot.data.comments;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.combined.data.IssueCommentMergeData;
import dev.idan.bgbot.data.comments.issue.Issue;
import dev.idan.bgbot.data.comments.object.attributes.CommentIssueObjectAttributesData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@JsonTypeName("note")
public class CommentIssueWebhookData extends IssueCommentMergeData {

    @JsonProperty("issue")
    private Issue issue;

    @JsonProperty("object_attributes")
    private CommentIssueObjectAttributesData objectAttributes;

    @Override
    public void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel) {
        String noteableType = objectAttributes.getNoteableType();
        if (noteableType.equalsIgnoreCase("MergeRequest"))
            noteableType = "Merge Request";

        if (objectAttributes.getNoteableType().equals("Issue")) {
            builder.setTitle(
                       " Commented on " + noteableType
                            + ": " + issue.getTitle() +
                            " (#" + issue.getId() + ")", objectAttributes.getUrl());
        }

        builder.setTitle(String.format("Commented on %s", noteableType), objectAttributes.getUrl());
        builder.setDescription(objectAttributes.getNote());
    }
}
