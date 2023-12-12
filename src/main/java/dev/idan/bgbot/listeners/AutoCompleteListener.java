package dev.idan.bgbot.listeners;

import dev.idan.bgbot.entities.Project;
import dev.idan.bgbot.repository.ProjectRepository;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AutoCompleteListener extends ListenerAdapter {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        List<Project> projectList = projectRepository.findAllByGuildId(event.getGuild().getIdLong());
        if (projectList.isEmpty()) return;

        if (event.getName().equals("create-issue") && event.getFocusedOption().getName().equals("project-id") || event.getName().equals("remove-issuer-project") && event.getFocusedOption().getName().equals("project-id")) {
            List<Command.Choice> options = projectList.stream()
                    .filter(id -> id.getProjectId().startsWith(event.getFocusedOption().getValue()))
                    .map(id -> new Command.Choice(id.getProjectId(), id.getProjectId()))
                    .collect(Collectors.toList());

            event.replyChoices(options).queue();
        }
    }
}
