package dev.idan.bgbot.listeners;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.springframework.stereotype.Component;

@Component
public class ButtonInteractionListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("mail")) {
            TextInput subject = TextInput.create("subject", "Subject", TextInputStyle.SHORT)
                    .setPlaceholder("Issue")
                    .setMinLength(5)
                    .setMaxLength(100)
                    .build();

            TextInput body = TextInput.create("body", "Body", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Issue description")
                    .setMinLength(5)
                    .setMaxLength(3000)
                    .build();

            Modal modal = Modal.create("support-modal", "Support ticket")
                    .addComponents(ActionRow.of(subject), ActionRow.of(body))
                    .build();

            event.replyModal(modal).queue();
            return;
        }
    }
}
