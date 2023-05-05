package dev.idan.bgbot.discord.listeners;

import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OnReady extends ListenerAdapter {

    private boolean run = false;

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void onReady(ReadyEvent event) {
        if (run) return;
        run = true;

        System.out.println("test");
        CommandData setup = (Commands.slash(
                        "setup", "configure the bgbot as you like")
                .addOption(OptionType.CHANNEL, "channel", "The channel that you want to get updates on", true)
        );

        CommandData unset = (Commands.slash(
                "unset", "soon")
        );

        event.getJDA().upsertCommand(unset).queue();
        event.getJDA().upsertCommand(setup).queue();
    }
}
