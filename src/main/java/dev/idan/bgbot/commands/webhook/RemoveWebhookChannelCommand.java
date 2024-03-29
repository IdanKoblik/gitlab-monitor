package dev.idan.bgbot.commands.webhook;

import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RemoveWebhookChannelCommand extends Command {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("remove-webhook-channel")) return;

        GuildChannelUnion channel = event.getOption("channel").getAsChannel();

        if (!tokenRepository.existsByChannelId(channel.getIdLong())) {
            event.reply("This channel is not connected to the Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        tokenRepository.deleteByChannelId(channel.getIdLong());
        event.reply("This channel has been disconnected from the Gitlab monitor. ✅").setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("remove-webhook-channel", "Disconnects a channel from the Gitlab monitor.")
                .addOption(OptionType.CHANNEL, "channel", "The channel that you want to disconnect from the Gitlab monitor", true);
    }
}