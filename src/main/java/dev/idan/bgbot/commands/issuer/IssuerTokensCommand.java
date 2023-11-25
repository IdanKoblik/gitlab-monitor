package dev.idan.bgbot.commands.issuer;

import dev.idan.bgbot.entities.Project;
import dev.idan.bgbot.repository.ProjectRepository;
import dev.idan.bgbot.services.ProjectService;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class IssuerTokensCommand extends Command {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("issuer-tokens")) return;

        List<Project> projectList = projectRepository.findAllByGuildId(event.getGuild().getIdLong());
        if (projectList.isEmpty()) {
            event.reply("This server has 0 connected projects. ❌").setEphemeral(true).queue();
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("```").append("Issuer tokens:").append("\n");

        for (Project project : projectList)
            sb.append(project.getProjectId()).append(" - ").append(projectService.getProjectName(project.getProjectId())).append("\n");

        sb.append("```");

        event.reply(sb.toString()).setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("issuer-tokens", "Get all issuer tokens that are connected to the guild");
    }
}
