package dev.idan.bgbot.config;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdaConfig {

    @Autowired
    ConfigData configData;

    @Bean
    @SneakyThrows
    public JDA jda() {
        return JDABuilder.createDefault(configData.discordToken())
                .setActivity(Activity.playing("with gitlab api"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .build();
    }
}
