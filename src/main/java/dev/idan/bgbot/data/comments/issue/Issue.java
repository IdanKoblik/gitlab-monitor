package dev.idan.bgbot.data.comments.issue;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Issue {

    int id;

    String title;

    @JsonProperty("author_id")
    String authorId;

    @JsonProperty("created_at")
    String createdAt;

    @JsonProperty("updated_at")
    String updatedAt;

    int position;

    @JsonProperty("branch_name")
    String branchName;

    String description;

    String state;

    int iid;
}
