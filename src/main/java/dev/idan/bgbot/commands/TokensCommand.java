package dev.idan.bgbot.commands;

import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TokensCommand extends Command {

    @Autowired
    private final TokenRepository tokenRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("tokens")) return;

        List<Token> tokenOptional = tokenRepository.findAllByChannelId(event.getChannel().getIdLong());

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

    @Override
    protected CommandData commandData() {
        return Commands.slash("tokens", "Get all the tokens that are connected to this channel")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}