package dev.idan.bgbot.repository;

import dev.idan.bgbot.entities.ExternalToken;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalTokenRepository extends MongoRepository<ExternalToken, Long> {

    @DeleteQuery
    void deleteByGuildId(long guildId);

    boolean existsByGuildId(long guildId);

    Optional<ExternalToken> findByGuildId(long guildId);
}
