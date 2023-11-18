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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class InitExternalCommand extends Command {

    @Autowired
    ExternalTokenRepository externalTokenRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("init-external")) return;

        long projectId = event.getOption("project-id").getAsLong();

        Optional<ExternalToken> externalTokenOptional = externalTokenRepository.findByGuildId(event.getGuild().getIdLong());
        if (externalTokenOptional.isEmpty()) {
            // First time registering
            Set<Long> ids = new HashSet<>();
            ids.add(projectId);

            ExternalToken token = new ExternalToken(event.getGuild().getIdLong(), ids);
            externalTokenRepository.insert(token);

            event.reply("You successfully registered this project. ✅").setEphemeral(true).queue();
            return;
        }

        Set<Long> ids = externalTokenOptional.get().getProjectIds();
        if (ids != null && ids.contains(projectId)) {
            event.reply("You already registered that project. ❌").setEphemeral(true).queue();
            return;
        }

        if (ids == null) ids = new HashSet<>();
        ids.add(projectId);
        externalTokenOptional.get().setProjectIds(ids);

        externalTokenRepository.save(externalTokenOptional.get());
        event.reply("You successfully registered this project. ✅").setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("init-external", "Use this command if you planning to create issues via the bot")
                .addOption(OptionType.INTEGER, "project-id", "your project id", true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
