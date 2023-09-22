package dev.idan.bgbot.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
public class Token {

    public boolean useGravatar = false;

    @Id
    @MongoId
    @Field("_id")
    private String secretToken;

    @Field("_id")
    public String getSecretToken() {
        return secretToken;
    }

    @Field("_id")
    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    @Indexed(unique = true)
    private long channelID;

    private long guildID;

    private long notifyRoleID;

    @JsonCreator
    public Token() {
    }
}
