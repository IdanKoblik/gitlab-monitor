package dev.idan.bgbot.commands;

import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
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
        if (!event.getName().equals("remove")) return;

        GuildChannelUnion channel = event.getOption("channel").getAsChannel();

        if (!tokenRepository.existsByChannelID(channel.getIdLong())) {
            event.reply("This channel is not connected to the Gitlab monitor.").setEphemeral(true).queue();
            return;
        }

        tokenRepository.deleteByChannelID(channel.getIdLong());
        event.reply("This channel has been disconnected from the Gitlab monitor.").setEphemeral(true).queue();
    }
}
