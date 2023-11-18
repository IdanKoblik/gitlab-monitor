package dev.idan.bgbot.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;

@Getter
@Setter
@Collation(value = "externalToken")
@AllArgsConstructor
public class IssuerToken {

    @Id
    @MongoId
    @Field("_id")
    private long guildId;

    @Field("_id")
    public long getGuildId() {
        return guildId;
    }

    @Field("_id")
    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    @Indexed(unique = true)
    private Set<Long> projectIds;
}
