package dev.idan.bgbot.commands;

import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RemoveNotifyCommand extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("removenotify")) return;

        Role role = Optional.of(event.getOption("role").getAsRole()).orElse(null);
        String secretToken = Optional.of(event.getOption("secret-token").getAsString()).orElse(null);

        Optional<Token> tokenOptional = tokenRepository.findById(secretToken);

        if (tokenOptional.isEmpty()) {
            event.reply("This token is not connected to the Gitlab monitor.").setEphemeral(true).queue();
            return;
        }

        if (tokenOptional.get().getNotifyRoleID() != role.getIdLong()) {
            event.reply("This role is not connected to the secret token.").setEphemeral(true).queue();
            return;
        }

        tokenRepository.deleteByNotifyRoleID(role.getIdLong());

        event.reply("The role has been removed from this repository").queue();
    }
}
