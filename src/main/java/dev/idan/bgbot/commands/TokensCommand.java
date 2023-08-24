package dev.idan.bgbot.commands;

import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokensCommand extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("tokens")) return;

        event.reply(tokenRepository.findAllByGuildID(event.getGuild().getIdLong()).toString()).queue();
    }
}
