package dev.idan.bgbot.commands.external;

import dev.idan.bgbot.repository.ExternalTokenRepository;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RemoveByProjectIdCommand extends Command {

    @Autowired
    ExternalTokenRepository externalTokenRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("remove-by-project-id")) return;

        long projectId = event.getOption("project-id").getAsLong();
        if (!externalTokenRepository.existsByProjectId(projectId)) {
            event.reply("This project does not connected to the Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        externalTokenRepository.deleteByProjectId(projectId);

        event.reply("This project has been successfully removed from Gitlab monitor. ✅").setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("remove-by-project-id", "Remove gitlab project from the Gitlab monitor")
                .addOption(OptionType.INTEGER, "project-id", "your project id", true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
