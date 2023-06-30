package dev.idan.bgbot.listeners;

import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReadyListener extends ListenerAdapter {

    private boolean run = false;

    @Autowired
    TokenRepository tokenRepository;

    public void onReady(ReadyEvent event) {
        if (run) return;
        run = true;

        CommandData setup = (Commands.slash(
                        "setup", "configure the bgbot as you like")
                .addOption(OptionType.CHANNEL, "channel", "The channel that you want to get updates on", true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
        );
        CommandData unset = (Commands.slash(
                "unset", "remove channel from bgbot")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
        );
        CommandData help = (Commands.slash(
                "help", "gitlab-monitor - docs")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
        );

        event.getJDA().upsertCommand(unset).queue();
        event.getJDA().upsertCommand(setup).queue();
        event.getJDA().upsertCommand(help).queue();
    }
}
