package dev.idan.bgbot.commands;

import dev.idan.bgbot.config.ConfigData;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InitCommand extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    ConfigData configData;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("init")) return;

        GuildChannelUnion channel = event.getOption("channel").getAsChannel();
        if (channel.getType() != ChannelType.TEXT) return;

        Token token = new Token();
        token.setSecretToken(UUID.randomUUID().toString().trim());
        token.setChannelID(channel.getIdLong());
        token.setGuildID(channel.getGuild().getIdLong());

        tokenRepository.insert(token);

        event.reply(token.getSecretToken() + '\n' + configData.webhookURL()).setEphemeral(true).queue();
    }
}
