package dev.idan.bgbot.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Collation(value = "externalToken")
@AllArgsConstructor
public class ExternalToken {

    @Id
    @MongoId
    @Field("_id")
    private long projectId;

    @Field("_id")
    public long getProjectId() {
        return projectId;
    }

    @Field("_id")
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    @Indexed(unique = true)
    private long guildId;
}
