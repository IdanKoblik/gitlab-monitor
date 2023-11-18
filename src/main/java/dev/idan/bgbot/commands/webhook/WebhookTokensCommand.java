package dev.idan.bgbot.commands.webhook;

import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class WebhookTokensCommand extends Command {

    @Autowired
    private final TokenRepository tokenRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("webhook-tokens")) return;

        List<Token> tokenList = tokenRepository.findAllByChannelId(event.getChannel().getIdLong());

        if (tokenList.isEmpty()) {
            event.reply("This channel is not connected to the Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("```").append("Webhook tokens:").append("\n");
        for (Token token : tokenList) {
            sb.append(token.getSecretToken()).append(" - ").append(event.getJDA().getTextChannelById(token.getChannelId()).getName()).append("\n");
        }

        sb.append("```");

        event.reply(sb.toString()).setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("webhook-tokens", "Get all the webhook tokens that are connected to this channel");
    }
}