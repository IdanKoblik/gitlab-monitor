package dev.idan.bgbot.listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GuildJoinListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        if (!event.getJDA().getUsers().contains(event.getJDA().getSelfUser())) return;

        List<Role> roleList = event.getGuild().getRolesByName("bgbot", true);
        if (roleList.isEmpty()) event.getGuild().createRole().setName("bgbot").queue();
    }
}
