package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.WebhookRepositoryData;
import dev.idan.bgbot.entities.Token;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.List;

import static dev.idan.bgbot.utils.CommitHelper.EMPTY_COMMIT_SHA;
import static dev.idan.bgbot.utils.CommitHelper.head;

@Getter
public abstract class TagPushData extends WebhookData {

    @JsonProperty("event_name")
    String eventName;

    String before;

    String after;

    String ref;

    @JsonProperty("ref_protected")
    boolean refProtected;

    @JsonProperty("checkout_sha")
    String checkoutSha;

    @JsonProperty("user_id")
    int userId;

    @JsonProperty("user_name")
    String userName;

    @JsonProperty("user_avatar")
    String userAvatar;

    @JsonProperty("project_id")
    int projectId;

    List<TagPushCommitData> commits;

    WebhookProjectData project;

    WebhookRepositoryData repository;


    @Override
    public String getProjectName() {
        return project.getProjectName();
    }

    @Override
    public String getAuthorName() {
        return userName;
    }

    @Override
    public String getAuthorAvatarUrl() {
        return userAvatar;
    }

    @Override
    public String getProjectUrl() {
        return project.getWebUrl();
    }

    @Override
    public boolean sendEmbed() {
        return true;
    }

    @Override
    public void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(5, commits.size()); i++) {
            TagPushCommitData commit = commits.get(i);
            String commitID = commit.getId().substring(0, 7);
            String commitMessage = commit.getMessage();

            sb.append(String.format("[`%s`](%s)", commitID, commit.getUrl()));
            sb.append(" ");
            sb.append(commitMessage);
            sb.append("\n");
        }

        String target = getTarget(ref);
        builder.setDescription(sb.toString());

        if (after.equals(EMPTY_COMMIT_SHA)) {
            builder.setTitle(String.format("%s %s was deleted", head, target));
        } else if (before.equals(EMPTY_COMMIT_SHA)) {
            builder.setTitle(String.format("%s %s was created", head, target));
        }

        builder.setTitle(String.format("Pushed to %s", target));
    }

    public static String getTarget(String ref) {
        if (ref.startsWith("refs/heads/")) {
            head = "Push";
            return String.format("%s", ref.replace("refs/heads/", ""));
        }

        else if (ref.startsWith("refs/tags")) {
            head = "Tag";
        }

        return ref;
    }
}
