package dev.idan.bgbot.commands.webhook.qol;

import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RemoveNotifyCommand extends Command {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("remove-notify")) return;
        // TODO
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("remove-notify", "The bot will stop mentioning a selected role when a pipeline fails")
                .addOption(OptionType.ROLE, "role", "The role that you would like to stop mention when pipeline fails", true)
                .addOption(OptionType.STRING, "secret-token", "The secret token that you got when you ran the init command (use /tokens to find all the tokens)", true);
    }
}