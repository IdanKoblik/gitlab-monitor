package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagPushPipelineCommitAuthorData {

    @JsonProperty("name")
    String name;

    @JsonProperty("email")
    String email;
}
