package dev.idan.bgbot.commands.qol;

import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
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

        Role role = Optional.of(event.getOption("role").getAsRole()).orElse(null);
        String secretToken = Optional.of(event.getOption("secret-token").getAsString()).orElse(null);

        Optional<Token> tokenOptional = tokenRepository.findById(secretToken);

        if (tokenOptional.isEmpty()) {
            event.reply("This token is not connected to the Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        if (tokenOptional.get().getNotifyRoleId() != role.getIdLong()) {
            event.reply("This role is not connected to the secret token. ❌").setEphemeral(true).queue();
            return;
        }

        tokenRepository.deleteByNotifyRoleId(role.getIdLong());

        event.reply("The role has been removed from this repository ✅").queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("remove-notify", "The bot will stop mentioning a selected role when a pipeline fails")
                .addOption(OptionType.ROLE, "role", "The role that you would like to stop mention when pipeline fails", true)
                .addOption(OptionType.STRING, "secret-token", "The secret token that you got when you ran the init command (use /tokens to find all the tokens)", true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}