package dev.idan.bgbot.commands;

import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RemoveBySecretTokenCommand extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("removebysecrettoken")) return;

        String secretToken = Optional.of(event.getOption("secret-token").getAsString()).orElse(null);
        GuildChannelUnion channel = event.getOption("channel").getAsChannel();
        if (channel.getType() != ChannelType.TEXT) return;

        Optional<Token> tokenOptional = tokenRepository.findById(secretToken);

        if (tokenOptional.isEmpty()) {
            event.reply("This token is not connected to the Gitlab monitor.").setEphemeral(true).queue();
            return;
        }

        if (tokenOptional.get().getChannelID() != channel.getIdLong()) {
            event.reply("This channel is not connected to the secret token.").setEphemeral(true).queue();
            return;
        }

        tokenRepository.deleteBySecretToken(secretToken);

        event.reply("This channel has been disconnected from the Gitlab monitor.").setEphemeral(true).queue();
    }
}
