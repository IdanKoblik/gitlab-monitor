package dev.idan.bgbot.utils;

import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

import java.util.UUID;

public class TestHelper {

    public static void applyWebhookData(WebhookData webhookData, JDA jda, EmbedBuilder builder) {
        Token token = new Token();
        token.setSecretToken(UUID.randomUUID().toString().trim());
        token.setChannelId(1L);
        token.setGuildId(2L);

        webhookData.apply(builder, "gitlab.com", token, jda.getTextChannelById(1));
    }
}