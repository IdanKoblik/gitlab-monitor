package dev.idan.bgbot.commands.issuer;

import dev.idan.bgbot.entities.Project;
import dev.idan.bgbot.repository.ProjectRepository;
import dev.idan.bgbot.services.ProjectService;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
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
public class IssuerInitCommand extends Command {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("issuer-init")) return;

        String accessToken = event.getOption("access-token").getAsString();
        String projectId = event.getOption("project-id").getAsString();

        try {
            projectService.existsByProjectId(projectId);
        } catch (Exception e) {
            event.reply("Invalid project id. ❌").setEphemeral(true).queue();
        }

        Optional<Project> projectOptional = projectRepository.findByGuildId(event.getGuild().getIdLong());
        if (projectOptional.isEmpty()) {
            // First time registering
            Project project = new Project(projectId, accessToken, event.getGuild().getIdLong());
            projectRepository.insert(project);

            event.reply("You successfully registered this project. ✅").setEphemeral(true).queue();
            return;
        }

        if (projectRepository.existsByProjectId(projectId)) {
            event.reply("You already registered that project. ❌").setEphemeral(true).queue();
            return;
        }

        projectOptional.get().setProjectId(projectId);
        projectOptional.get().setAccessToken(accessToken);
        projectOptional.get().setGuildId(event.getGuild().getIdLong());

        projectRepository.save(projectOptional.get());
        event.reply("You successfully registered this project. ✅").setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("issuer-init", "Use this command if you planning to create issues via the bot")
                .addOption(OptionType.STRING, "project-id", "your project id", true)
                .addOption(OptionType.STRING, "access-token", "your application token", true);
    }
}
