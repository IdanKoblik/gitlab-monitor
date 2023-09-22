package dev.idan.bgbot.commands;

import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokensCommand extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("tokens")) return;

        List<Token> tokenOptional = tokenRepository.findAllByChannelID(event.getChannel().getIdLong());

        if (tokenOptional.isEmpty()) {
            event.reply("This channel is not connected to the Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Token token : tokenOptional) {
            sb.append(token.getSecretToken()).append("\n");
        }

        event.reply(sb.toString()).setEphemeral(true).queue();
    }
}