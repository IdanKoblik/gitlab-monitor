package dev.idan.bgbot.data.combined.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueMergeObjectAttributesData {

    @JsonProperty("action")
    String action;

    @JsonProperty("url")
    String url;
}
