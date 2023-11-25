package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagPushCommitData extends TagPushPipelineCommitData {

    @JsonProperty("title")
    String title;

    @JsonProperty("author")
    private TagPushPipelineCommitAuthorData author;

}
