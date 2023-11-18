package dev.idan.bgbot.system;

import dev.idan.bgbot.commands.*;
import dev.idan.bgbot.commands.external.InitExternalCommand;
import dev.idan.bgbot.commands.external.RemoveProjectCommand;
import dev.idan.bgbot.commands.external.issue.CreateIssueCommand;
import dev.idan.bgbot.commands.qol.NotifyCommand;
import dev.idan.bgbot.commands.qol.RemoveNotifyCommand;
import dev.idan.bgbot.config.ConfigData;
import dev.idan.bgbot.repository.ExternalTokenRepository;
import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.services.IssueService;
import dev.idan.bgbot.services.ProjectService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Setter
public class CommandManager {

    private final Map<String, Command> commands = new HashMap<>();

    private final JDA jda;
    private final String guildId;
    private final ConfigData configData;
    private final TokenRepository tokenRepository;
    private final ExternalTokenRepository externalTokenRepository;
    private final IssueService issueService;
    private final ProjectService projectService;

    public CommandManager(JDA jda, String guildId, TokenRepository tokenRepository, ExternalTokenRepository externalTokenRepository, ConfigData configData, IssueService issueService, ProjectService projectService) {
        this.jda = jda;
        this.guildId = guildId;
        this.tokenRepository = tokenRepository;
        this.externalTokenRepository = externalTokenRepository;
        this.configData = configData;
        this.issueService = issueService;
        this.projectService = projectService;
    }

    public void initCommands() {
        addCommand(new HelpCommand());
        addCommand(new InitCommand(tokenRepository, configData));
        addCommand(new NotifyCommand(tokenRepository));
        addCommand(new RemoveBySecretTokenCommand(tokenRepository));
        addCommand(new TokensCommand(projectService, tokenRepository));
        addCommand(new RemoveNotifyCommand(tokenRepository));
        addCommand(new RemoveCommand(tokenRepository));
        addCommand(new InitExternalCommand(externalTokenRepository));
        addCommand(new RemoveProjectCommand(externalTokenRepository));
        addCommand(new CreateIssueCommand(externalTokenRepository, issueService));

        Guild guild = jda.getGuildById(guildId);
        if (guild == null) {
            log.error("Cannot find the guild: " + guildId);
            return;
        }

        guild.updateCommands().addCommands(commands.values().stream().map(Command::commandData).toList()).queue();
    }


    public void handle(SlashCommandInteractionEvent event) {
        Command command = commands.get(event.getName().toLowerCase());
        if (command == null) return;
        command.execute(event);
    }

    private void addCommand(Command command) {
        commands.put(command.commandData().getName().toLowerCase(), command);
    }
}
