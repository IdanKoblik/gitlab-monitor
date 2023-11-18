package dev.idan.bgbot.commands.issuer;

import dev.idan.bgbot.entities.IssuerToken;
import dev.idan.bgbot.repository.IssuerTokenRepository;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class RemoveIssuerProjectCommand extends Command {

    @Autowired
    IssuerTokenRepository issuerTokenRepository;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("remove-issuer-project")) return;

        long projectId = event.getOption("project-id").getAsLong();

        Optional<IssuerToken> externalTokenOptional = issuerTokenRepository.findByGuildId(event.getGuild().getIdLong());
        if (externalTokenOptional.isEmpty()) {
            event.reply("This server is not connected to the Gitlab-monitor. ❌").setEphemeral(true).queue();
            return;
        }

        Set<Long> ids = externalTokenOptional.get().getProjectIds();
        if (!ids.contains(projectId)) {
            event.reply("This project does not connected to the Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        ids.remove(projectId);
        issuerTokenRepository.save(externalTokenOptional.get());
        event.reply("This project has been successfully removed from Gitlab monitor. ✅").setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("remove-issuer-project", "Remove gitlab project from the Gitlab monitor")
                .addOption(OptionType.INTEGER, "project-id", "your project id", true);
    }
}
