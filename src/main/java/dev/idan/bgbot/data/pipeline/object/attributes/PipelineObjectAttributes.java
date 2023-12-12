package dev.idan.bgbot.data.pipeline.object.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PipelineObjectAttributes {

    long id;

    long iid;

    String name;

    String ref;

    boolean tag;

    String sha;

    @JsonProperty("before_sha")
    String beforeSha;

    String source;

    String status;

    int duration;
}
