package dev.idan.bgbot.data.release.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class Assets {

    @JsonProperty("links")
    List<LinksData> links;

    @JsonProperty("sources")
    List<SourcesData> sources;
}
