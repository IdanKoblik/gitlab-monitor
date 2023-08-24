package dev.idan.bgbot.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

public class Token {

    @Getter
    @Setter
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

    @Getter
    @Setter
    private long channelID;

    @Getter
    @Setter
    private long guildID;

    @Override
    public String toString() {
        return "Token{" +
                "channelID=" + channelID +
                '}';
    }

    @JsonCreator
    public Token() {
    }
}
