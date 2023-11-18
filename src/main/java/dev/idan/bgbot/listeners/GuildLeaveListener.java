package dev.idan.bgbot.listeners;

import dev.idan.bgbot.repository.IssuerTokenRepository;
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
    IssuerTokenRepository issuerTokenRepository;

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        if (!event.getJDA().getUsers().contains(event.getJDA().getSelfUser())) return;

        if (!tokenRepository.existsByGuildId(event.getGuild().getIdLong())) return;
        tokenRepository.deleteByGuildId(event.getGuild().getIdLong());

        if (!issuerTokenRepository.existsByGuildId(event.getGuild().getIdLong())) return;
        issuerTokenRepository.deleteByGuildId(event.getGuild().getIdLong());
    }
}
