package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagPushData {

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
}
