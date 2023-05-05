package dev.idan.bgbot.discord.commands;

import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Setup extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("setup")) return;

        GuildChannelUnion channel = event.getOption("channel").getAsChannel();
        long channelID = channel.getIdLong();

        Token token = new Token();
        token.setSecretToken(UUID.randomUUID().toString().trim());
        token.setChannelID(channelID);
        token.setGuildID(channel.getGuild().getIdLong());

        tokenRepository.insert(token);

        event.reply(token.getSecretToken() + " \nhttps://beta-gitlab.linuxwiz.net/webhook").setEphemeral(true).queue();
    }
}
