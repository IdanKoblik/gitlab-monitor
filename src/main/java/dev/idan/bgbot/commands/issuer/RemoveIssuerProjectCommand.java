package dev.idan.bgbot.commands.issuer;

import dev.idan.bgbot.entities.Project;
import dev.idan.bgbot.repository.ProjectRepository;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RemoveIssuerProjectCommand extends Command {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("remove-issuer-project")) return;

        String projectId = event.getOption("project-id").getAsString();

        List<Project> projectList = projectRepository.findAllByGuildId(event.getGuild().getIdLong());
        if (projectList.isEmpty()) {
            event.reply("This server is not connected to the Gitlab-monitor. ❌").setEphemeral(true).queue();
            return;
        }

        if (!projectRepository.existsByProjectId(projectId)) {
            event.reply("This project is not connected to the Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        projectList.removeIf(project -> project.getProjectId().equals(projectId));
        projectRepository.delete(projectList.get(0));

        event.reply("This project has been successfully removed from Gitlab monitor. ✅").setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("remove-issuer-project", "Remove gitlab project from the Gitlab monitor")
                .addOption(OptionType.STRING, "project-id", "your project id", true, true);
    }
}
