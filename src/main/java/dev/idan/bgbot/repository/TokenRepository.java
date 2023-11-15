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
    void deleteByGuildID(long guildID);

    @DeleteQuery
    void deleteByChannelID(long channelID);

    @DeleteQuery
    void deleteByNotifyRoleID(long notifyRoleID);

    @DeleteQuery
    void deleteBySecretToken(String secretToken);

    boolean existsByChannelID(long channelID);

    boolean existsByNotifyRoleID(long notifyRoleID);

    Optional<Token> findByChannelID(long channelID);

    List<Token> findAllByChannelID(long channelID);
}
