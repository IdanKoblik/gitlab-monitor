package dev.idan.gitlab.monitor;

import dev.idan.gitlab.monitor.listeners.ReadyListener;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
        context.getBean(ReadyListener.class).onReady(new ReadyEvent(jda));
    }

}
