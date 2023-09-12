package dev.idan.bgbot.data.release.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LinksData {

    int id;

    String external;

    @JsonProperty("link_type")
    String linkType;

    String name;

    String url;
}
