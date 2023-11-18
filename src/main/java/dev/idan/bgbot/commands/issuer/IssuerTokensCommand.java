package dev.idan.bgbot.commands.issuer;

import dev.idan.bgbot.entities.IssuerToken;
import dev.idan.bgbot.repository.IssuerTokenRepository;
import dev.idan.bgbot.services.ProjectService;
import dev.idan.bgbot.system.Command;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class IssuerTokensCommand extends Command {

    @Autowired
    IssuerTokenRepository issuerTokenRepository;

    @Autowired
    ProjectService projectService;

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("issuer-tokens")) return;

        List<IssuerToken> issuerTokenList = issuerTokenRepository.findAllByGuildId(event.getGuild().getIdLong());
        if (issuerTokenList.isEmpty()) {
            event.reply("This server has 0 connected projects. ❌").setEphemeral(true).queue();
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("```").append("Issuer tokens:").append("\n");
        for (IssuerToken token : issuerTokenList) {
            token.getProjectIds().forEach(id -> sb.append(id).append(" - ").append(projectService.getProjectName(id)));
        }

        sb.append("```");

        event.reply(sb.toString()).setEphemeral(true).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("issuer-tokens", "Get all issuer tokens that are connected to the guild");
    }
}
