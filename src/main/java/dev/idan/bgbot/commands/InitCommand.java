package dev.idan.bgbot.commands;

import dev.idan.bgbot.config.ConfigData;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class InitCommand extends Command {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    ConfigData configData;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
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

    @Override
    protected CommandData commandData() {
        return Commands.slash("init", "Configure the Gitlab monitor as you wish")
                .addOption(OptionType.CHANNEL, "channel", "The channel that you want to get updates on", true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
