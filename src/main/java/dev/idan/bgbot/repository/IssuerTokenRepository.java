package dev.idan.bgbot.repository;

import dev.idan.bgbot.entities.IssuerToken;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssuerTokenRepository extends MongoRepository<IssuerToken, Long> {

    @DeleteQuery
    void deleteByGuildId(long guildId);

    boolean existsByGuildId(long guildId);

    Optional<IssuerToken> findByGuildId(long guildId);
}
