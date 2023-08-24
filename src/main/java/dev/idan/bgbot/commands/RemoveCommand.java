package dev.idan.bgbot.commands;

import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveCommand extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("unset")) return;

        tokenRepository.deleteByGuildID(event.getGuild().getIdLong());
        event.reply("Ok").queue();
    }
}
