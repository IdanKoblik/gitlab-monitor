package dev.idan.bgbot.listeners;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class ReadyListener extends ListenerAdapter {

    private boolean run = false;

    public void onReady(@NotNull ReadyEvent event) {
        if (run) return;
        run = true;
    }
}