package dev.idan.bgbot.listeners;

import dev.idan.bgbot.config.ConfigData;
import dev.idan.bgbot.repository.ProjectRepository;
import dev.idan.bgbot.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GuildLeaveListener extends ListenerAdapter {

    @Autowired
    ConfigData configData;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        if (event.getGuild().getSelfMember().getIdLong() != configData.botId()) return;

        tokenRepository.deleteAllByGuildId(event.getGuild().getIdLong());
        projectRepository.deleteByGuildId(event.getGuild().getIdLong());
    }
}
