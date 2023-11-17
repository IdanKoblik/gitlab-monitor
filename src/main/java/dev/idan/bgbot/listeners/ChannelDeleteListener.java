package dev.idan.bgbot.listeners;

import dev.idan.bgbot.repository.TokenRepository;
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
        System.out.println("1");
        tokenRepository.deleteByChannelId(event.getChannel().getIdLong());
    }
}
