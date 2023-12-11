package dev.idan.bgbot.data.push;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.idan.bgbot.data.combined.data.TagPushData;
import org.springframework.stereotype.Component;

@JsonTypeName("push")
@Component
public class PushWebhookData extends TagPushData {

    @JsonProperty("user_username")
    String userUsername;

    @JsonProperty("user_email")
    String userEmail;

    @Override
    public String getEmail() {
        return userEmail;
    }
}
