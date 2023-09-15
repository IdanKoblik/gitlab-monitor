package dev.idan.bgbot.data.pipeline;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

@Getter
@JsonTypeName("build")
public class PipelineBuilds {

    int id;

    String stage;

    String name;

    String status;

    @JsonProperty("created_at")
    String createdAt;

    @JsonProperty("finished_at")
    String finishedAt;

    int duration;

    @JsonProperty("failed_reason")
    String failedReason;
}
