package dev.idan.bgbot.data.tag;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.WebhookRepositoryData;
import dev.idan.bgbot.data.combined.data.TagPushCommitData;
import dev.idan.bgbot.data.combined.data.TagPushPipelineCommitData;
import dev.idan.bgbot.data.combined.data.TagPushData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@JsonTypeName("tag")
public class TagWebhookData extends TagPushData {
    @Override
    public String getEmail() {
        return Optional.ofNullable(getCommits())
                .map(obj -> obj.get(0).getAuthor().getEmail()).orElse("test@test.com");
    }
}
