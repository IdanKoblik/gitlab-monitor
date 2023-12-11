package dev.idan.bgbot.hooks;

import dev.idan.bgbot.config.UnitTestConfigData;
import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.combined.data.TagPushCommitData;
import dev.idan.bgbot.data.push.PushWebhookData;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.repository.TokenRepository;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static dev.idan.bgbot.utils.CommitHelper.EMPTY_COMMIT_SHA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class PushTest {

    @Autowired
    PushWebhookData pushWebhookData;

    @Autowired
    WebhookProjectData webhookProjectData;

    @Autowired
    TagPushCommitData tagPushCommitData;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UnitTestConfigData unitTestConfigData;

    @Autowired
    JDA jda;

    @Test
    void testValidPush() {
        getProjectBasics();
        pushWebhookData.setBefore(UUID.randomUUID().toString().trim());
        pushWebhookData.setAfter(UUID.randomUUID().toString().trim());

        EmbedBuilder builder = new EmbedBuilder();
        applyWebhookData(builder);

        assertEquals("Pushed to branch `main`", builder.build().getTitle());
        assertEquals(getCommits(), builder.build().getDescription());
    }

    @Test
    void testValidCreateBranch() {
        getProjectBasics();
        pushWebhookData.setBefore(EMPTY_COMMIT_SHA);
        pushWebhookData.setAfter(UUID.randomUUID().toString().trim());

        EmbedBuilder builder = new EmbedBuilder();
        applyWebhookData(builder);

        assertEquals("Branch `main` was created", builder.build().getTitle());
        assertEquals(getCommits(), builder.build().getDescription());
    }

    @Test
    void testValidDeleteBranch() {
        getProjectBasics();
        pushWebhookData.setBefore(UUID.randomUUID().toString().trim());
        pushWebhookData.setAfter(EMPTY_COMMIT_SHA);
        pushWebhookData.setCommits(Collections.emptyList());

        EmbedBuilder builder = new EmbedBuilder();
        applyWebhookData(builder);

        assertEquals("Branch `main` was deleted", builder.build().getTitle());
        assertNull(builder.build().getDescription());
    }

    private String getCommits() {
        List<TagPushCommitData> commits = pushWebhookData.getCommits();
        if (commits.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(5, commits.size()); i++) {
            TagPushCommitData commit = commits.get(i);
            String commitID = commit.getId().substring(0, 7);

            sb.append(String.format("[`%s`](%s)", commitID, commit.getUrl()));
            sb.append(" ");
            sb.append(commit.getTitle());
            sb.append("\n");
        }

        return sb.toString();
    }

    private List<TagPushCommitData> createSampleCommits() {
        List<TagPushCommitData> commits = new ArrayList<>();

        tagPushCommitData.setId("Commit1");
        tagPushCommitData.setUrl("https://gitlab.com/commit1");
        tagPushCommitData.setTitle("commit1");

        commits.add(tagPushCommitData);

        return commits;
    }

    private void getProjectBasics() {
        webhookProjectData.setName("SampleProject");
        webhookProjectData.setWebUrl("https://example.com/sample");

        pushWebhookData.setRef("refs/heads/main");
        pushWebhookData.setCommits(createSampleCommits());
        pushWebhookData.setProject(webhookProjectData);
    }

    private void applyWebhookData(EmbedBuilder builder) {
        Optional<Token> tokenOptional = tokenRepository.findById(unitTestConfigData.secretToken());
        if (tokenOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Secret discordToken must be specified");

        pushWebhookData.apply(builder, unitTestConfigData.instanceUrl(), tokenOptional.get(), jda.getTextChannelById(unitTestConfigData.guildId()));
    }
}