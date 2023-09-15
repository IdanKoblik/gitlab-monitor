package dev.idan.bgbot.data.release;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.combined.data.TagPushPipelineCommitData;
import dev.idan.bgbot.data.release.assets.Assets;
import dev.idan.bgbot.data.release.assets.LinksData;
import dev.idan.bgbot.data.release.assets.SourcesData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@JsonTypeName("release")
public class ReleaseWebhookData extends WebhookData {

    int id;

    @JsonProperty("created_at")
    String createdAt;

    String description;

    String name;

    @JsonProperty("released_at")
    String releasedAt;

    String tag;

    String url;

    String action;

    @JsonProperty("project")
    WebhookProjectData project;

    @JsonProperty("commit")
    TagPushPipelineCommitData commit;

    @JsonProperty("assets")
    Assets assets;

    @Override
    public String getAuthorName() {
        return commit.getAuthor().getName();
    }

    @Override
    public String getAuthorAvatarUrl() {
        return "https://www.gravatar.com/avatar/";
    }

    @Override
    public String getEmail() {
        return commit.getAuthor().getEmail();
    }

    @Override
    public String getProjectName() {
        return project.getProjectName();
    }

    @Override
    public void apply(EmbedBuilder builder, String instanceURL, Token token, TextChannel channel) {
        if (commit == null) System.out.println("Commit null");
        if (commit != null) System.out.println("Commit !null");

        if (project == null) System.out.println("Project null");
        if (project != null) System.out.println("Project !null");


        for (SourcesData sourcesData : assets.getSources()) {
            String sourceName = sourcesData.getFormat();
            String sourceLink = sourcesData.getUrl();
            String format = String.format("[%s](%s)", "Source code", sourceLink);
            builder.addField(sourceName, format, true);
        }

        for (LinksData linksData : assets.getLinks()) {
            String linkName = linksData.getName();
            String linkLink = linksData.getUrl();
            String format = String.format("[%s](%s)", linkName, linkLink);
            builder.addField("link", format, true);
        }

        builder.setTitle(String.format("New release: %s", name), url);
    }

}
