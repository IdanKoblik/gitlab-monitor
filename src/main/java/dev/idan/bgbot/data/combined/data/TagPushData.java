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

@Getter
public abstract class TagPushData extends WebhookData {

    @JsonProperty("event_name")
    String eventName;

    @JsonProperty("before")
    String before;

    @JsonProperty("after")
    String after;

    @JsonProperty("ref")
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

    @JsonProperty("commits")
    List<TagPushCommitData> commits;

    @JsonProperty("project")
    WebhookProjectData project;

    @JsonProperty("repository")
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

            sb.append(String.format("[`%s`](%s)", commitID, commit.getUrl()));
            sb.append(" ");
            sb.append(commit.getTitle());
            sb.append("\n");
        }

        String target = getTarget(ref);
        builder.setDescription(sb.toString());

        String url = project.getWebUrl() + getUrl(ref);

        if (after.equals(EMPTY_COMMIT_SHA)) {
            builder.setTitle(String.format("%s was deleted", target));
        } else if (before.equals(EMPTY_COMMIT_SHA)) {
            builder.setTitle(String.format("%s was created", target), url);
        } else {
            builder.setTitle(String.format("Pushed to %s", target.toLowerCase()), url);
        }
    }

    public static String getTarget(String ref) {
        if (ref.startsWith("refs/heads/")) {
            return String.format("Branch `%s`", ref.replace("refs/heads/", ""));
        }

        if (ref.startsWith("refs/tags/")) {
            return String.format("Tag `%s`", ref.replace("refs/tags/", ""));
        }

        return ref;
    }

    public static String getUrl(String ref) {
        if (ref.startsWith("refs/heads")) {
            // /-/tree/ -> branch
            return "/-/tree/" + ref.replace("refs/heads/", "");
        }

        if (ref.startsWith("refs/tags")) {
            // /-/tags/ -> tag
            return "/-/tags/" + ref.replace("refs/tags/", "");
        }

        return ref;
    }
}
