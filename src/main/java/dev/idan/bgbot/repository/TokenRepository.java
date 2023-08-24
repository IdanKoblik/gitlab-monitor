package dev.idan.bgbot.repository;

import dev.idan.bgbot.entities.Token;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    @DeleteQuery
    void deleteByGuildID(long id);

    @DeleteQuery
    void deleteByChannelID(long id);

    boolean existsByChannelID(long id);

    List<Token> findAllByGuildID(long id);
}
