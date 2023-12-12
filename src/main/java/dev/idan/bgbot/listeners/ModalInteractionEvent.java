package dev.idan.bgbot.listeners;

import dev.idan.bgbot.services.MailService;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModalInteractionEvent extends ListenerAdapter {

    @Autowired
    MailService mailService;

    @Override
    public void onModalInteraction(net.dv8tion.jda.api.events.interaction.ModalInteractionEvent event) {
        if (event.getModalId().equals("ticket-modal")) {
            String subject = event.getValue("subject").getAsString();
            String body = event.getValue("body").getAsString();

            mailService.sendEmail("idankob@gmail.com", subject + " - " + event.getUser().getName(), body);

            event.reply("Thanks for your request!").setEphemeral(true).queue();
            return;
        }
    }
}
