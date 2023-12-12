package dev.idan.bgbot;

import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "dev.idan")
public class BgbotApplication {

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
