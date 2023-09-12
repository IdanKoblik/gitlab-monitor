package dev.idan.bgbot.data.comments.object.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommentObjectAttributesData {
    int id;

    String note;

    @JsonProperty("noteable_type")
    String noteableType;

    @JsonProperty("author_id")
    int authorId;

    @JsonProperty("created_at")
    String createdAt;

    @JsonProperty("updated_at")
    String updatedAt;

    @JsonProperty("project_id")
    int projectId;

    String attachment;

    @JsonProperty("line_code")
    String lineCode;

    @JsonProperty("commit_id")
    String commitId;

    @JsonProperty("noteable_id")
    int noteableId;

    boolean system;

    String url;
}
