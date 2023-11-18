package dev.idan.bgbot.listeners;

import dev.idan.bgbot.entities.ExternalToken;
import dev.idan.bgbot.repository.ExternalTokenRepository;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AutoCompleteListener extends ListenerAdapter {

    @Autowired
    ExternalTokenRepository externalTokenRepository;

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        Optional<ExternalToken> externalTokenOptional = externalTokenRepository.findByGuildId(event.getGuild().getIdLong());
        if (externalTokenOptional.isEmpty()) return;

        Set<Long> projectIds = externalTokenOptional.get().getProjectIds();
        if (event.getName().equals("create-issue") && event.getFocusedOption().getName().equals("project-id")) {
            List<Command.Choice> options = projectIds.stream()
                    .filter(projectId -> projectId.toString().startsWith(event.getFocusedOption().getValue()))
                    .map(projectId -> new Command.Choice(projectId.toString(), projectId.toString()))
                    .collect(Collectors.toList());

            event.replyChoices(options).queue();
        }
    }
}
