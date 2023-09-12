package dev.idan.bgbot.data.pipeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PipelineObjectAttributes {

    int id;

    int iid;

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
