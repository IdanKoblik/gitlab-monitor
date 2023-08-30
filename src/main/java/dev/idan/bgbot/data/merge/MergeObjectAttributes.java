package dev.idan.bgbot.data.merge;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.idan.bgbot.data.combined.data.IssueMergeObjectAttributesData;

public class MergeObjectAttributes extends IssueMergeObjectAttributesData {
    @JsonProperty("source_branch")
    String sourceBranch;

    @JsonProperty("target_branch")
    String targetBranch;
}
