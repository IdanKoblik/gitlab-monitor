package dev.idan.bgbot.repository;

import dev.idan.bgbot.entities.Token;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    @DeleteQuery
    void deleteByGuildID(long id);
}
