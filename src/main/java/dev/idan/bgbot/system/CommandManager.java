package dev.idan.bgbot.system;

import dev.idan.bgbot.commands.*;
import dev.idan.bgbot.config.ConfigData;
import dev.idan.bgbot.repository.TokenRepository;
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

    private final JDA jda;
    private final String guildId;
    private final Map<String, Command> commands = new HashMap<>();
    private final TokenRepository tokenRepository;
    private final ConfigData configData;

    public CommandManager(JDA jda, String guildId, TokenRepository tokenRepository, ConfigData configData) {
        this.jda = jda;
        this.guildId = guildId;
        this.tokenRepository = tokenRepository;
        this.configData = configData;
    }

    public void initCommands() {
        addCommand(new HelpCommand());
        addCommand(new InitCommand(tokenRepository, configData));
        addCommand(new NotifyCommand(tokenRepository));
        addCommand(new RemoveBySecretTokenCommand(tokenRepository));
        addCommand(new TokensCommand(tokenRepository));
        addCommand(new RemoveNotifyCommand(tokenRepository));
        addCommand(new RemoveCommand(tokenRepository));

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
