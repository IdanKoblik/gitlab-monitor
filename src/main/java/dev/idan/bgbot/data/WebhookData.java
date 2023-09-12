package dev.idan.bgbot.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.idan.bgbot.data.comments.issue.CommentIssueWebhookData;
import dev.idan.bgbot.data.issue.IssueWebhookData;
import dev.idan.bgbot.data.merge.MergeWebhookData;
import dev.idan.bgbot.data.pipeline.PipelineWebhookData;
import dev.idan.bgbot.data.push.PushWebhookData;
import dev.idan.bgbot.data.release.ReleaseWebhookData;
import dev.idan.bgbot.data.tag.TagWebhookData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "object_kind")
@JsonSubTypes({
        @JsonSubTypes.Type(PushWebhookData.class),
        @JsonSubTypes.Type(TagWebhookData.class),
        @JsonSubTypes.Type(IssueWebhookData.class),
        @JsonSubTypes.Type(MergeWebhookData.class),
        @JsonSubTypes.Type(PipelineWebhookData.class),
        @JsonSubTypes.Type(ReleaseWebhookData.class),
        @JsonSubTypes.Type(CommentIssueWebhookData.class)
})
public abstract class WebhookData implements AuthorNameAndAvatar, ProjectName, AuthorEmail {

    @JsonProperty("object_kind")
    String objectKind;

    public abstract void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel);
}
