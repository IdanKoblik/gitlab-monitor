package dev.idan.bgbot.data.issue;

import dev.idan.bgbot.data.combined.data.IssueMergeObjectAttributesData;
import lombok.Getter;

@Getter
public class IssueObjectAttributesData extends IssueMergeObjectAttributesData {
    String description;

    int iid;

    String title;
}
