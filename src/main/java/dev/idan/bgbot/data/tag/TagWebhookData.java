package dev.idan.bgbot.data.tag;

import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.combined.data.TagPushData;

import java.util.Optional;

@JsonTypeName("tag_push")
public class TagWebhookData extends TagPushData {
    @Override
    public String getEmail() {
        return Optional.ofNullable(getCommits())
                .map(obj -> obj.get(0).getAuthor().getEmail()).orElse("test@test.com");
    }
}
