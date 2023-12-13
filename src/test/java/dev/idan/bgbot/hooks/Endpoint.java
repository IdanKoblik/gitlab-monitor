package dev.idan.bgbot.hooks;

import dev.idan.bgbot.data.WebhookData;
import dev.idan.bgbot.data.combined.data.TagPushData;
import dev.idan.bgbot.data.issue.IssueWebhookData;
import dev.idan.bgbot.data.push.PushWebhookData;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class Endpoint {

    @Value("${spring.testing.guild_id}")
    private long guildId;

    @Value(("${spring.testing.instance_url}"))
    private String instanceUrl;

    @Value("${spring.testing.secret_token}")
    private String secretToken;

    @Autowired
    private WebhookData webhookData;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JDA jda;

    public void applyWebhookData(EmbedBuilder builder) {
        Optional<Token> tokenOptional = tokenRepository.findById(secretToken);
        if (tokenOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secret token must be specified");

        webhookData.apply(builder, instanceUrl, tokenOptional.get(), jda.getTextChannelById(guildId));
    }
}