package dev.idan.bgbot.repository;

import dev.idan.bgbot.entities.ExternalToken;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExternalTokenRepository extends MongoRepository<ExternalToken, String> {

    @DeleteQuery
    void deleteByGuildId(long guildId);

    @DeleteQuery
    void deleteByProjectId(long projectId);

    boolean existsByProjectId(long projectId);

    boolean existsByGuildId(long guildId);

    Optional<ExternalToken> findByProjectId(long projectId);

    Optional<ExternalToken> findByGuildId(long guildId);
}
