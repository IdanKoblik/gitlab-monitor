package dev.idan.bgbot.commands.issuer;

import dev.idan.bgbot.entities.IssuerToken;
import dev.idan.bgbot.repository.IssuerTokenRepository;
import dev.idan.bgbot.services.ProjectService;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class IssuerInitCommand extends Command {

    @Autowired
    IssuerTokenRepository issuerTokenRepository;

    @Autowired
    ProjectService projectService;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("issuer-init")) return;

        long projectId = event.getOption("project-id").getAsLong();
        if (!(projectService.existsByProjectId(projectId).getStatusCode() == HttpStatusCode.valueOf(200))) {
            event.reply("Invalid project id. ❌").setEphemeral(true).queue();
            return;
        }

        Optional<IssuerToken> externalTokenOptional = issuerTokenRepository.findByGuildId(event.getGuild().getIdLong());
        if (externalTokenOptional.isEmpty()) {
            // First time registering
            Set<Long> ids = new HashSet<>();
            ids.add(projectId);

            IssuerToken token = new IssuerToken(event.getGuild().getIdLong(), ids);
            issuerTokenRepository.insert(token);

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

        issuerTokenRepository.save(externalTokenOptional.get());
        event.reply("You successfully registered this project. ✅").setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("issuer-init", "Use this command if you planning to create issues via the bot")
                .addOption(OptionType.INTEGER, "project-id", "your project id", true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
