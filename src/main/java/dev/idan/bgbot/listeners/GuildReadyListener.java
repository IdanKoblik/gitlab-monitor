package dev.idan.bgbot.listeners;

import dev.idan.bgbot.config.ConfigData;
import dev.idan.bgbot.repository.ProjectRepository;
import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.services.IssueService;
import dev.idan.bgbot.services.ProjectService;
import dev.idan.bgbot.system.CommandManager;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GuildReadyListener extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    IssueService issueService;

    @Autowired
    ConfigData configData;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRepository projectRepository;

    private final Map<String, CommandManager> commandManagerMap = new HashMap<>();

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        CommandManager commandManager = new CommandManager(event.getJDA(), event.getGuild().getId(), configData,issueService, projectService, tokenRepository, projectRepository);
        commandManagerMap.put(
                event.getGuild().getId(),
                commandManager
        );
        commandManager.initCommands();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getGuild() == null) return;
        CommandManager commandManager = commandManagerMap.get(event.getGuild().getId());
        if (commandManager == null) return;
        commandManager.handle(event);
    }

}
