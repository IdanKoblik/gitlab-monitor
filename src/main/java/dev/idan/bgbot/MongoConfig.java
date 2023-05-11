package dev.idan.bgbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

@ComponentScan(basePackageClasses = { MongoConfig.class } )
@EnableMongoRepositories(basePackages = "dev.idan.bgbot.repository")
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @SneakyThrows
    @Override
    protected String getDatabaseName() {
        File file = new File("mongo.json");
        ObjectNode objectNode = (ObjectNode) new ObjectMapper().readTree(file);
        return objectNode.get("database").asText();
    }

    @SneakyThrows
    @Override
    public MongoClient mongoClient() {
        // we read the file twice
        // bad
        File file = new File("mongo.json");
        ObjectNode objectNode = (ObjectNode) new ObjectMapper().readTree(file);

        ConnectionString connectionString = new ConnectionString(objectNode.get("connection_string").asText());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("dev.idan.bgbot.entities");
    }

    @Bean
    @SneakyThrows
    public JDA jda() {
        Dotenv env = Dotenv.load();
        return JDABuilder.createDefault(env.get("TOKEN"))
                .setActivity(Activity.playing("with gitlab api"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();
    }
}