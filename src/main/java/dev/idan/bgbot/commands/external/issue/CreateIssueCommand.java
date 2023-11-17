package dev.idan.bgbot.commands.external.issue;

import dev.idan.bgbot.entities.ExternalToken;
import dev.idan.bgbot.repository.ExternalTokenRepository;
import dev.idan.bgbot.services.IssueService;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CreateIssueCommand extends Command {

    @Autowired
    ExternalTokenRepository externalTokenRepository;

    @Autowired
    IssueService issueService;

    @Override
    @SneakyThrows
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("create-issue")) return;

        String issueTitle = event.getOption("issue-title").getAsString();
        String issueDescription = event.getOption("issue-description").getAsString();
        Optional<ExternalToken> guildIdOptional = externalTokenRepository.findByGuildId(event.getGuild().getIdLong());

        if (guildIdOptional.isEmpty()) {
            event.reply("This project does not connected to the Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        issueService.createIssue(guildIdOptional.get().getProjectId(), issueTitle, issueDescription);
        event.reply("You have successfully created an issue. ✅").setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("create-issue", "Create issue via discord")
                .addOption(OptionType.STRING, "issue-title", "The issue title", true)
                .addOption(OptionType.STRING, "issue-description", "The issue description", true)
                .addOption(OptionType.INTEGER, "project-id", "Project ID", true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
