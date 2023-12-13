package dev.idan.bgbot.data.issue.object.attributes;

import dev.idan.bgbot.data.combined.data.IssueMergeObjectAttributesData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueObjectAttributesData extends IssueMergeObjectAttributesData {
    String description;

    int iid;

    String title;
}
