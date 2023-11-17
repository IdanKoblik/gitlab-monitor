package dev.idan.bgbot.listeners;

import dev.idan.bgbot.repository.ExternalTokenRepository;
import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GuildLeaveListener extends ListenerAdapter {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    ExternalTokenRepository externalTokenRepository;

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        if (!event.getJDA().getUsers().contains(event.getJDA().getSelfUser())) return;

        if (!tokenRepository.existsByGuildId(event.getGuild().getIdLong())) return;
        tokenRepository.deleteByGuildId(event.getGuild().getIdLong());

        if (!externalTokenRepository.existsByGuildId(event.getGuild().getIdLong())) return;
        externalTokenRepository.deleteByGuildId(event.getGuild().getIdLong());
    }
}
