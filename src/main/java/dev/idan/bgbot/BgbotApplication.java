package dev.idan.bgbot;

import dev.idan.bgbot.repository.TokenRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BgbotApplication {

    @Autowired
    TokenRepository tokenRepository;

    @SneakyThrows
    public static void main(String[] args) {
        var context = SpringApplication.run(BgbotApplication.class, args);

        JDA jda = context.getBean(JDA.class);
        ListenerAdapter[] listeners = context.getBeansOfType(ListenerAdapter.class).values().toArray(new ListenerAdapter[0]);

        for (ListenerAdapter listener : listeners) {
            System.out.println("Registering listener '" + listener.getClass().getName() + "'");
            jda.addEventListener(listener);
        }
        jda.getRegisteredListeners().forEach(System.out::println);

        jda.awaitReady();
    }

}
