package dev.idan.bgbot.commands.external;

import dev.idan.bgbot.entities.ExternalToken;
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
public class InitExternalCommand extends Command {

    @Autowired
    ExternalTokenRepository externalTokenRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("init-external")) return;

        long projectId = event.getOption("project-id").getAsLong();

        if (externalTokenRepository.existsByProjectId(projectId)) {
            event.reply("You already registered that project. ❌").setEphemeral(true).queue();
            return;
        }

        ExternalToken token = new ExternalToken(projectId, event.getGuild().getIdLong());
        externalTokenRepository.insert(token);

        event.reply("You successfully registered this project. ✅").setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("init-external", "Use this command if you planning to create issues via the bot")
                .addOption(OptionType.INTEGER, "project-id", "your project id", true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
