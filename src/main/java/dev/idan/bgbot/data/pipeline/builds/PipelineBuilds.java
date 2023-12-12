package dev.idan.bgbot.data.pipeline.builds;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PipelineBuilds {

    long id;

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
