package dev.idan.bgbot.data.issue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.WebhookRepositoryData;
import dev.idan.bgbot.data.combined.data.IssueCommentMergeData;
import dev.idan.bgbot.data.combined.data.IssueCommentMergePipelineUserData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@JsonTypeName("issue")
public class IssueWebhookData extends IssueCommentMergeData {

    @JsonProperty("object_attributes")
    private IssueObjectAttributesData objectAttributes;

    @Override
    public void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel) {
        builder.setTitle(String.format("%s Issue: #%d %s",
                        objectAttributes.getAction(),
                        objectAttributes.getIid(),
                        objectAttributes.getTitle()
                ), objectAttributes.getUrl()
        ).setDescription(objectAttributes.getDescription());
    }
}
