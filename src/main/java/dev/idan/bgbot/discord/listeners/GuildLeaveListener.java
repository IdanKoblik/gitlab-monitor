package dev.idan.bgbot.discord.listeners;

import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuildLeaveListener extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        if (!event.getJDA().getUsers().contains(event.getJDA().getSelfUser())) return;
        tokenRepository.deleteAll();
    }
}
