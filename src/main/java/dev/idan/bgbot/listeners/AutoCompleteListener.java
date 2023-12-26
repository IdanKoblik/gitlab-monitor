package dev.idan.bgbot.listeners;

import dev.idan.bgbot.entities.Project;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.ProjectRepository;
import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.services.ProjectService;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutoCompleteListener extends ListenerAdapter {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    ProjectService projectService;

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if ((event.getName().equals("create-issue") || event.getName().equals("remove-issuer-project")) && event.getFocusedOption().getName().equals("project-id")) {
            List<Project> projectList = projectRepository.findAllByGuildId(event.getGuild().getIdLong());
            if (projectList.isEmpty()) return;

            List<Command.Choice> options = projectList.stream()
                    .filter(id -> projectService.getProjectWithNamespace(id.getProjectId()).startsWith(event.getFocusedOption().getValue()))
                    .map(id -> new Command.Choice(projectService.getProjectWithNamespace(id.getProjectId()), projectService.getProjectWithNamespace(id.getProjectId())))
                    .toList();

            event.replyChoices(options).queue();
            return;
        }

        if ((event.getName().equals("notify")) && event.getFocusedOption().getName().equals("secret-token")) {
            List<Token> tokenList = tokenRepository.findAllByGuildId(event.getGuild().getIdLong());
            if (tokenList.isEmpty()) return;

            List<Command.Choice> options = tokenList.stream()
                    .filter(token -> token.getSecretToken().startsWith(event.getFocusedOption().getValue()))
                    .map(token -> new Command.Choice(token.getSecretToken(), token.getSecretToken()))
                    .toList();

            event.replyChoices(options).queue();
            return;
        }
    }

}
