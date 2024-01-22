package dev.idan.bgbot.controller;

import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.utils.PartialImage;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@RestController
public class WebhookEndpoint {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    JDA jda;

    @PostMapping(value = "webhook", consumes = {
            "application/json"
    })
    public void onWebhook(@RequestBody WebhookData data, @RequestHeader("X-Gitlab-Instance") String instanceURL,
                          @RequestHeader("X-Gitlab-Token") String secretToken) {
        if (secretToken == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secret token must be specified");

        Optional<Token> tokenOptional = tokenRepository.findById(secretToken);
        if (tokenOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Secret token not found");

        Token token = tokenOptional.get();

        if (!data.sendEmbed())
            return;

        TextChannel channel = jda.getTextChannelById(token.getChannelId());
        if (channel == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token does not registered inside the DB");

        EmbedBuilder builder = new EmbedBuilder();
        String userName = data.getAuthorName();
        String avatar = data.getAuthorAvatarUrl();

        if (token.isUseGravatar())
            avatar = PartialImage.getEmail(avatar, data.getEmail(), token);

        builder.setAuthor(userName, "https://idankoblik.github.io/gitlab-monitor", avatar);
        builder.setFooter(data.getProjectName());
        builder.setTimestamp(Instant.now());

        try {
            data.apply(builder, instanceURL, token, channel);
        } catch (Exception e) {
            log.error("Error while applying webhook data to embed builder", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        channel.sendMessageEmbeds(builder.build()).queue();

    }
}
