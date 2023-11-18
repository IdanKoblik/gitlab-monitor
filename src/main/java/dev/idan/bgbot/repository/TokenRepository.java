package dev.idan.bgbot.repository;

import dev.idan.bgbot.entities.Token;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    @DeleteQuery
    void deleteByGuildId(long guildId);

    @DeleteQuery
    void deleteByChannelId(long channelId);

    @DeleteQuery
    void deleteByNotifyRoleId(long notifyRoleId);

    @DeleteQuery
    void deleteBySecretToken(String secretToken);

    boolean existsByChannelId(long channelId);

    boolean existsByNotifyRoleId(long notifyRoleId);

    boolean existsByGuildId(long guildId);

    Optional<Token> findByChannelId(long channelId);

    List<Token> findAllByChannelId(long channelId);

}
