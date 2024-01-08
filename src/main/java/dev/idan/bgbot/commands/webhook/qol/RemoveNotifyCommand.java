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
public class RemoveNotifyCommand extends Command {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("remove-notify")) return;

        Role role = event.getOption("role").getAsRole();
        String secretToken = event.getOption("secret-token").getAsString();

        Optional<Token> tokenOptional = tokenRepository.findById(secretToken);

        if (tokenOptional.isEmpty()) {
            event.reply("This project is not connected to Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        if (tokenOptional.get().getNotifyRoleId() != role.getIdLong()) {
            event.reply("This role is not saved in the notify feature. ❌").setEphemeral(true).queue();
            return;
        }

        Token token = tokenOptional.get();
        token.setNotifyRoleId(0);

        tokenRepository.save(token);
        event.reply("You have successfully enabled notify feature on this project. ✅").setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("remove-notify", "The bot will stop mentioning a selected role when a pipeline fails")
                .addOption(OptionType.ROLE, "role", "The role that you would like to stop mention when pipeline fails", true)
                .addOption(OptionType.STRING, "secret-token", "Project secret token", true, true);
    }
}