package dev.idan.bgbot.commands.webhook.qol;

import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.entities.Role;
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
public class NotifyCommand extends Command {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("notify")) return;

        Role role = Optional.of(event.getOption("role").getAsRole()).orElse(null);
        String secretToken = Optional.of(event.getOption("secret-token").getAsString()).orElse(null);

        Optional<Token> tokenOptional = tokenRepository.findById(secretToken);

        if (tokenOptional.isEmpty()) {
            event.reply("This token is not connected to the Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        if (tokenOptional.get().getNotifyRoleId() != 0) {
            event.reply("This token is already connected to a role. ❌").setEphemeral(true).queue();
            return;
        }

        Token token = tokenOptional.get();

        token.setNotifyRoleId(role.getIdLong());
        tokenRepository.save(token);

        event.reply("The role has been added to the database. ✅").queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("notify", "The bot will mention a selected role when a pipeline fails")
                .addOption(OptionType.ROLE, "role", "The role that you would like to get mention when pipeline fails", true)
                .addOption(OptionType.STRING, "secret-token", "The secret token that you got when you ran the init command (use /tokens to find all the tokens)", true);
    }
}