package dev.idan.bgbot.controller;

import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import dev.idan.bgbot.utils.PartialImage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

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
        Optional<Token> tokenOptional = secretToken == null ? Optional.empty() : tokenRepository.findById(secretToken);
        if (tokenOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secret discordToken must be specified");

        Token token = tokenOptional.get();

        if (!data.sendEmbed()) return;

        if (jda.getTextChannelById(token.getChannelID()) == null) return;

        EmbedBuilder builder = new EmbedBuilder();
        String userName = data.getAuthorName();
        String userLink = instanceURL + "/" + userName;
        String avatar = data.getAuthorAvatarUrl();

        if (token.isUseGravatar()) avatar = PartialImage.getEmail(avatar, data.getEmail(), token);

        builder.setAuthor(userName, userLink, avatar);
        builder.setFooter(data.getProjectName());
        builder.setTimestamp(Instant.now());
        data.apply(builder, instanceURL, token, jda.getTextChannelById(token.getChannelID()));

        jda.getTextChannelById(token.getChannelID()).sendMessageEmbeds(builder.build()).queue();
    }

    /*@PostMapping(value = "webhook", consumes = {
            "application/json"
    })
    public void onWebhook(@RequestBody ObjectNode objectNode, @RequestHeader("X-Gitlab-Instance") String instanceURL,
                          @RequestHeader("X-Gitlab-Token") String secretToken) {
        Optional<Token> tokenOptional = secretToken == null ? Optional.empty() : tokenRepository.findById(secretToken);
        if (tokenOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secret discordToken must be specified");
        }

        Token token = tokenOptional.get();

        String objectKind = objectNode.get("object_kind").asText();
        HookType handler = switch (objectKind) {
            case "push" -> pushEvent;
            case "tag_push" -> tagEvent;
            case "issue" -> issueEvent;
            case "note" -> commentEvent;
            case "merge_request" -> mergeEvent;
            case "pipeline" -> pipelineEvent;
            case "release" -> releaseEvent;
            default -> null;
        };
        if (handler == null) return;

        handler.process(objectNode, instanceURL, token, jda.getTextChannelById(token.getChannelID()));
    }*/
}