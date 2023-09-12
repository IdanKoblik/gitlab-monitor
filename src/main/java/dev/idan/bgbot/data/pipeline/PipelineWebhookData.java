package dev.idan.bgbot.data.pipeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.combined.data.IssueCommentMergePipelineUserData;
import dev.idan.bgbot.data.combined.data.TagPushPipelineCommitData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@JsonTypeName("pipeline")
public class PipelineWebhookData extends WebhookData {

    private WebhookProjectData project;

    private IssueCommentMergePipelineUserData user;

    private TagPushPipelineCommitData commit;

    @JsonProperty("object_attributes")
    private PipelineObjectAttributes objectAttributes;

    private List<PipelineBuilds> builds;

    @Override
    public String getAuthorName() {
        return user.getName();
    }

    @Override
    public String getAuthorAvatarUrl() {
        return user.getAvatarUrl();
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getProjectName() {
        return project.getProjectName();
    }

    @Override
    public void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel) {
        String status = objectAttributes.getStatus();
        String ref = objectAttributes.getRef();
        String userName = user.getName();
        String url = instanceURL + "/" + project.getProjectName() + "/-/pipelines/" + objectAttributes.getId();
        int iid = objectAttributes.getIid();

        if (status.equals("success"))
            builder.setTitle(
                    "Pipeline " + "#" + iid +
                        " of branch " + ref + " by " + userName + " " +
                        "has passed in: " + objectAttributes.getDuration() + " seconds", url);

        if (status.equals("skipped"))
            builder.setTitle(
                    "Pipeline " + "#" + iid +
                    " of branch " + ref + " by " + userName + " has been skipped", url);

        if (status.equals("failed")) {
            String failedReason = Optional.ofNullable(builds)
                    .map(obj -> obj.get(0).getFailedReason()).orElse("No reason provided");

            builder.setTitle("Pipeline " + "#" + iid + " of branch " + ref + " by " + userName + " has been failed", url)
            .setDescription("Reason: " + failedReason + "\n");

            Role role = channel.getGuild().getRoleById(token.getNotifyRoleID());
            if (role != null) builder.appendDescription(role.getAsMention());
        }
    }
}
