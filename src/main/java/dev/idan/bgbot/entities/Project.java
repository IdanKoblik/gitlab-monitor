package dev.idan.bgbot.entities;

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
    private String token;

    @Field("_id")
    public String getToken() {
        return token;
    }

    @Field("_id")
    public void setToken(String token) {
        this.token = token;
    }

    @Indexed(unique = true)
    private String projectId;

    @Indexed(unique = true)
    private String accessToken;

    @Indexed(unique = true)
    private long guildId;
}
