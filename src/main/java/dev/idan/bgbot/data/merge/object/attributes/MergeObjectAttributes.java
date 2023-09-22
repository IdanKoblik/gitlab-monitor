package dev.idan.bgbot.data.merge.object.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.idan.bgbot.data.combined.data.IssueMergeObjectAttributesData;
import lombok.Getter;

@Getter
public class MergeObjectAttributes extends IssueMergeObjectAttributesData {
    @JsonProperty("source_branch")
    String sourceBranch;

    @JsonProperty("target_branch")
    String targetBranch;
}
