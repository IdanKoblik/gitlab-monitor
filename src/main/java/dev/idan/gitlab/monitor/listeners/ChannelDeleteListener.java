package dev.idan.gitlab.monitor.listeners;

import dev.idan.gitlab.monitor.repository.TokenRepository;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChannelDeleteListener extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void onChannelDelete(ChannelDeleteEvent event) {
        tokenRepository.deleteByGuildID(event.getGuild().getIdLong());
    }
}
