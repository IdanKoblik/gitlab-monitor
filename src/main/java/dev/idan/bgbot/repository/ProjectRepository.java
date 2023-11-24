package dev.idan.bgbot.repository;

import dev.idan.bgbot.entities.Project;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    Optional<Project> findByProjectId(String projectId);

    List<Project> findByGuildId(long guildId);

    boolean existsByProjectId(String projectId);

    List<Project> findAllByGuildId(long guildId);

    @DeleteQuery
    void deleteByGuildId(long guildId);

    boolean existsByGuildId(long guildId);
}
