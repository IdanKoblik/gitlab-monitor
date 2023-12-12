package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class IssueMergeObjectAttributesData {

    @JsonProperty("action")
    String action;

    @JsonProperty("url")
    String url;
}
