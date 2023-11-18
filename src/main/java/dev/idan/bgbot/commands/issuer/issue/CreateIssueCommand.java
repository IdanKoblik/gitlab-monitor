package dev.idan.bgbot.commands.issuer.issue;

import dev.idan.bgbot.repository.IssuerTokenRepository;
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
import org.springframework.web.client.HttpClientErrorException;

@Component
@AllArgsConstructor
public class CreateIssueCommand extends Command {

    @Autowired
    IssuerTokenRepository issuerTokenRepository;

    @Autowired
    IssueService issueService;

    @Override
    @SneakyThrows
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("create-issue")) return;

        String issueTitle = event.getOption("issue-title").getAsString();
        String issueDescription = event.getOption("issue-description").getAsString();
        String projectId = event.getOption("project-id").getAsString();

        if (issueTitle.isEmpty()) {
            // There is an issue with issue title.
            event.reply("There is an issue with the title of the issue. ❌").setEphemeral(true).queue();
            return;
        }

        if (issueTitle.length() > 255) {
            event.reply("Issue title is too long (maximum is 255 characters). ❌").setEphemeral(true).queue();
            return;
        }

        try {
            issueService.createIssue(projectId, issueTitle, issueDescription);
            event.reply("You have successfully created an issue. ✅").setEphemeral(true).queue();
        } catch (HttpClientErrorException e) {
            event.reply("Invalid project id. ❌").setEphemeral(true).queue();
        }
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("create-issue", "Create issue via discord")
                .addOption(OptionType.STRING, "issue-title", "The issue title", true)
                .addOption(OptionType.STRING, "issue-description", "The issue description", true)
                .addOption(OptionType.STRING, "project-id", "Project ID", true, true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}
