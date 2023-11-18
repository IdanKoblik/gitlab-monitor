package dev.idan.bgbot.system;

import dev.idan.bgbot.commands.HelpCommand;
import dev.idan.bgbot.commands.TokensCommand;
import dev.idan.bgbot.commands.issuer.IssuerInitCommand;
import dev.idan.bgbot.commands.issuer.RemoveIssuerProjectCommand;
import dev.idan.bgbot.commands.issuer.issue.CreateIssueCommand;
import dev.idan.bgbot.commands.webhook.RemoveWebhookChannelCommand;
import dev.idan.bgbot.commands.webhook.RemoveWebhookProjectCommand;
import dev.idan.bgbot.commands.webhook.WebhookInitCommand;
import dev.idan.bgbot.commands.webhook.qol.NotifyCommand;
import dev.idan.bgbot.commands.webhook.qol.RemoveNotifyCommand;
import dev.idan.bgbot.config.ConfigData;
import dev.idan.bgbot.repository.IssuerTokenRepository;
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
    private final IssuerTokenRepository issuerTokenRepository;
    private final IssueService issueService;
    private final ProjectService projectService;

    public CommandManager(JDA jda, String guildId, TokenRepository tokenRepository, IssuerTokenRepository issuerTokenRepository, ConfigData configData, IssueService issueService, ProjectService projectService) {
        this.jda = jda;
        this.guildId = guildId;
        this.tokenRepository = tokenRepository;
        this.issuerTokenRepository = issuerTokenRepository;
        this.configData = configData;
        this.issueService = issueService;
        this.projectService = projectService;
    }

    public void initCommands() {
        addCommand(new HelpCommand());
        addCommand(new WebhookInitCommand(tokenRepository, configData));
        addCommand(new NotifyCommand(tokenRepository));
        addCommand(new RemoveWebhookProjectCommand(tokenRepository));
        addCommand(new TokensCommand(projectService, tokenRepository));
        addCommand(new RemoveNotifyCommand(tokenRepository));
        addCommand(new RemoveWebhookChannelCommand(tokenRepository));
        addCommand(new IssuerInitCommand(issuerTokenRepository, projectService));
        addCommand(new RemoveIssuerProjectCommand(issuerTokenRepository));
        addCommand(new CreateIssueCommand(issuerTokenRepository, issueService));

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
