package dev.idan.bgbot.data.pipeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.combined.data.IssueCommentMergePipelineUserData;
import dev.idan.bgbot.data.combined.data.TagPushPipelineCommitData;
import dev.idan.bgbot.data.pipeline.builds.PipelineBuilds;
import dev.idan.bgbot.data.pipeline.object.attributes.PipelineObjectAttributes;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.List;
import java.util.Optional;

@JsonTypeName("pipeline")
public class PipelineWebhookData extends WebhookData {

    @JsonProperty("project")
    WebhookProjectData project;

    @JsonProperty("user")
    IssueCommentMergePipelineUserData user;

    @JsonProperty("commit")
    TagPushPipelineCommitData commit;

    @JsonProperty("object_attributes")
    PipelineObjectAttributes objectAttributes;

    @JsonProperty("builds")
    List<PipelineBuilds> builds;

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
    public String getProjectUrl() {
        return project.getProjectUrl();
    }

    @Override
    public boolean sendEmbed() {
        return true;
    }

    @Override
    public void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel) {
        String status = objectAttributes.getStatus();
        String ref = objectAttributes.getRef();
        String url = project.getWebUrl() + "/-/pipelines/" + objectAttributes.getId();
        long iid = objectAttributes.getIid();

        builder.setTitle(String.format("Starting pipeline #%d of branch %s", iid, ref), url);

        if (status.equals("success"))
            builder.setTitle(
                    "Pipeline " + "#" + iid +
                        " of branch " + ref + " has passed in: " + objectAttributes.getDuration() + " seconds", url);

        if (status.equals("skipped"))
            builder.setTitle(
                    "Pipeline " + "#" + iid +
                    " of branch " + ref + " has been skipped", url);

        if (status.equals("failed")) {
            String failedReason = Optional.of(builds)
                    .map(obj -> obj.get(0).getFailedReason()).orElse("No reason provided");

            builder.setTitle("Pipeline " + "#" + iid + " of branch " + ref + " has been failed", url)
            .setDescription("Reason: " + failedReason + "\n");

            Role role = channel.getGuild().getRoleById(token.getNotifyRoleId());
            if (role != null) builder.appendDescription(role.getAsMention());
        }
    }
}
