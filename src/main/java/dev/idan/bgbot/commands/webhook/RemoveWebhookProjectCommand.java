package dev.idan.bgbot.commands.webhook;

import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class RemoveWebhookProjectCommand extends Command {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("remove-webhook-project")) return;

        String secretToken = Optional.of(event.getOption("secret-token").getAsString()).orElse(null);
        GuildChannelUnion channel = event.getOption("channel").getAsChannel();
        if (channel.getType() != ChannelType.TEXT) return;

        Optional<Token> tokenOptional = tokenRepository.findById(secretToken);

        if (tokenOptional.isEmpty()) {
            event.reply("This token is not connected to the Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        if (tokenOptional.get().getChannelId() != channel.getIdLong()) {
            event.reply("This channel is not connected to the secret token. ❌").setEphemeral(true).queue();
            return;
        }

        tokenRepository.deleteBySecretToken(secretToken);

        event.reply("This channel has been disconnected from the Gitlab monitor. ✅").setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("remove-webhook-project", "Disconnects a webhook project from a discord channel.")
                .addOption(OptionType.STRING, "secret-token", "The secret token that you got when you ran the init command (use /tokens to find all the tokens)", true)
                .addOption(OptionType.CHANNEL, "channel", "The channel that you want to disconnect from the Gitlab monitor", true);
    }
}