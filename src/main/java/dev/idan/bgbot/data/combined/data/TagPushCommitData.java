package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class TagPushCommitData extends TagPushPipelineCommitData {

    @JsonProperty("title")
    String title;

    @JsonProperty("author")
    private TagPushPipelineCommitAuthorData author;

}
