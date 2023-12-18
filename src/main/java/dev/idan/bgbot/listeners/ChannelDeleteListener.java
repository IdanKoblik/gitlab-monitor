package dev.idan.bgbot.listeners;

import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChannelDeleteListener extends ListenerAdapter {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void onChannelDelete(ChannelDeleteEvent event) {
        long deletedChannelId = event.getChannel().getIdLong();

        Optional<Token> tokenOptional = tokenRepository.findByChannelId(deletedChannelId);
        if (tokenOptional.isPresent() && tokenOptional.get().getChannelId() == deletedChannelId)
            tokenRepository.delete(tokenOptional.get());
    }
}
