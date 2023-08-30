package dev.idan.bgbot.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PushData extends TagAndPushData {

    @JsonProperty("user_username")
    String userUsername;

    @JsonProperty("user_email")
    String userEmail;
}
