package dev.idan.bgbot.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Collation(value = "token")
public class Token {

    @Field("use_gravatar")
    private boolean useGravatar = false;

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

    @Field("channel_id")
    @Indexed(unique = true)
    private long channelId;

    @Field("guild_id")
    @Indexed(unique = true)
    private long guildId;

    @Field("notify_role_id")
    private long notifyRoleId;

    @JsonCreator
    public Token() {
    }
}
