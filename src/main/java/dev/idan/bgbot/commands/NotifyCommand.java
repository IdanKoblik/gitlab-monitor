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
public class NotifyCommand extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("notify")) return;

        Role role = Optional.of(event.getOption("role").getAsRole()).orElse(null);
        String secretToken = Optional.of(event.getOption("secret-token").getAsString()).orElse(null);

        Optional<Token> tokenOptional = tokenRepository.findById(secretToken);

        if (tokenOptional.isEmpty()) {
            event.reply("This token is not connected to the Gitlab monitor. ❌").setEphemeral(true).queue();
            return;
        }

        if (tokenOptional.get().getNotifyRoleID() != 0) {
            event.reply("This token is already connected to a role. ❌").setEphemeral(true).queue();
            return;
        }

        Token token = tokenOptional.get();

        token.setNotifyRoleID(role.getIdLong());
        tokenRepository.save(token);

        event.reply("The role has been added to the database. ✅").queue();
    }
}