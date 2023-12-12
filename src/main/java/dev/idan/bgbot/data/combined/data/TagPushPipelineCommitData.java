package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagPushPipelineCommitData {

    @JsonProperty("id")
    String id;

    @JsonProperty("message")
    String message;

    @JsonProperty("timestamp")
    String timestamp;

    @JsonProperty("url")
    String url;

    @JsonProperty("author")
    TagPushPipelineCommitAuthorData author;
}
