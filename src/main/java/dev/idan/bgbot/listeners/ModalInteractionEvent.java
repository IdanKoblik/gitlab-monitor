package dev.idan.bgbot.listeners;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class ModalInteractionEvent extends ListenerAdapter {

    @Override
    public void onModalInteraction(net.dv8tion.jda.api.events.interaction.ModalInteractionEvent event) {
        if (event.getModalId().equals("ticket-modal")) {
            String subject = event.getValue("subject").getAsString();
            String body = event.getValue("body").getAsString();

            // Maintainer discord ID - 429212281914785793
            User maintainer = event.getJDA().getUserById(429212281914785793L);
            if (maintainer == null) {
                log.error("Maintainer not found");
                return;
            }

            User user = event.getUser();
            User bot = event.getJDA().getSelfUser();
            maintainer.openPrivateChannel()
                    .flatMap(message -> message.sendMessageEmbeds(
                            new EmbedBuilder()
                                    .setAuthor(user.getName(), "https://discord.com/user" + user.getId(), user.getEffectiveAvatarUrl())
                                    .setTitle(subject)
                                    .setDescription(body)
                                    .setTimestamp(Instant.now())
                                    .setFooter(bot.getName(), bot.getEffectiveAvatarUrl())
                                    .build()
                    ))
                    .queue();

            event.reply("Thanks for your report.").setEphemeral(true).queue();
            return;
        }
    }
}
