package dev.idan.bgbot.data.issue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.combined.data.IssueCommentMergeData;
import dev.idan.bgbot.data.issue.object.attributes.IssueObjectAttributesData;
import dev.idan.bgbot.entities.Token;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;

@JsonTypeName("issue")
@Getter
@Component
public class IssueWebhookData extends IssueCommentMergeData {

    @JsonProperty("object_attributes")
    IssueObjectAttributesData objectAttributes;

    @Override
    public void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel) {
        builder.setTitle(String.format("%s issue: #%d %s",
                objectAttributes.getAction().substring(0, 1).toUpperCase()
                        + objectAttributes.getAction().substring(1),
                        objectAttributes.getIid(),
                        objectAttributes.getTitle()
                ), objectAttributes.getUrl()
        ).setDescription(objectAttributes.getDescription());
    }
}
