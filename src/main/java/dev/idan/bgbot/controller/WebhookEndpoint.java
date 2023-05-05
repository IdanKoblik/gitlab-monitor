package dev.idan.bgbot.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.hooks.*;
import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class WebhookEndpoint {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    JDA jda;

    @Autowired
    PushEvent pushEvent;

    @Autowired
    IssueEvent issueEvent;

    @Autowired
    MergeEvent mergeEvent;

    @Autowired
    CommentEvent commentEvent;

    @Autowired
    PipelineEvent pipelineEvent;

    @Autowired
    ReleaseEvent releaseEvent;

    @PostMapping(value = "webhook", consumes = {
            "application/json"
    })
    public void onWebhook(@RequestBody ObjectNode objectNode, @RequestHeader("X-Gitlab-Instance") String instanceURL,
                          @RequestHeader("X-Gitlab-Token") String secretToken) {
        Optional<Token> tokenOptional = secretToken == null ? Optional.empty() : tokenRepository.findById(secretToken);
        if (tokenOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secret token must be specified");
        }

        Token token = tokenOptional.get();

        String objectKind = objectNode.get("object_kind").asText();
        HookType handler = switch (objectKind) {
            case "push" -> pushEvent;
            case "issue" -> issueEvent;
            case "note" -> commentEvent;
            case "merge_request" -> mergeEvent;
            case "pipeline" -> pipelineEvent;
            case "release" -> releaseEvent;
            default -> null;
        };
        if (handler == null) return;

        handler.process(objectNode, instanceURL, token, jda.getTextChannelById(token.getChannelID()));
    }
}