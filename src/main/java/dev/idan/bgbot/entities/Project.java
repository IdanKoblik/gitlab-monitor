package dev.idan.bgbot.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Collation(value = "project")
public class Project {

    @Id
    @MongoId
    @Field("_id")
    private String projectId;

    @Field("_id")
    public String getProjectId() {
        return projectId;
    }

    @Field("_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @JsonProperty("access_token")
    @Indexed(unique = true)
    private String accessToken;

    @JsonProperty("guild_id")
    @Indexed(unique = true)
    private long guildId;
}
