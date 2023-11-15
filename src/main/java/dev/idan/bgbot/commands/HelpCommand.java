package dev.idan.bgbot.commands;

import dev.idan.bgbot.system.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.Instant;

@Component
public class HelpCommand extends Command {

    @Override
    protected void execute(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("help")) return;

        event.replyEmbeds(
                new EmbedBuilder()
                        .setAuthor("Gitlab monitor - How to use", "https://github.com/BETAIDK/gitlab-monitor/edit/main/README.md"
                                , event.getJDA().getSelfUser().getAvatarUrl())
                        .setTitle("Here's a little documentation for how to use gitlab monitor.")
                        .setDescription (
                                """
                                ```
                                1. write in any channel /setup and provide on which\s
                                channel you want to get updates of your project
                                ```
                                ```
                                2. the discord bot will response with secret token and
                                url that you need to put inside webhook token section in\s
                                the settings of your project.
                                the given url put inside thr url section and the secret token
                                inside the secret token section
                                ```
                                ```
                                3. Done! enjoy gitlab monitor and if you find any bug or have
                                any suggestion feel free to mail me (idankob@gmail.com)
                                ```
                                """
                        )
                        .setColor(Color.white)
                        .setFooter("Made by Beta#8084")
                        .setTimestamp(Instant.now())
                        .build()
        ).queue();
    }

    @Override
    protected CommandData commandData() {
        return Commands.slash("help", "Gitlab monitor documentation")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR));
    }
}