package dev.idan.bgbot.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagAndPushData extends WebhookGlobalData {

    @JsonProperty("event_name")
    String eventName;

    String after;

    String before;

    String ref;

    @JsonProperty("ref_protected")
    Boolean refProtected;

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
}
