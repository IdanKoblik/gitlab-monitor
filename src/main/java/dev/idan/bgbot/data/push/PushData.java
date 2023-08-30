package dev.idan.bgbot.data.push;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.idan.bgbot.data.combined.data.TagPushData;

public class PushData extends TagPushData {

    @JsonProperty("user_username")
    String userUsername;

    @JsonProperty("user_email")
    String userEmail;
}
