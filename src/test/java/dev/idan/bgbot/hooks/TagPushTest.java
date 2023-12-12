package dev.idan.bgbot.hooks;

import dev.idan.bgbot.data.WebhookProjectData;
import dev.idan.bgbot.data.combined.data.TagPushCommitData;
import dev.idan.bgbot.data.push.PushWebhookData;
import net.dv8tion.jda.api.EmbedBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static dev.idan.bgbot.data.combined.data.TagPushData.getTarget;
import static dev.idan.bgbot.utils.CommitHelper.EMPTY_COMMIT_SHA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class TagPushTest {

    @Autowired
    Endpoint endpoint;

    @Autowired
    PushWebhookData pushWebhookData;

    @Autowired
    WebhookProjectData webhookProjectData;

    @Autowired
    TagPushCommitData tagPushCommitData;

    @Test
    void testPush() {
        testWithRef("ref/heads/Test");
    }

    @Test
    void testTag() {
        testWithRef("ref/tag/Test");
    }

    private void testWithRef(String ref) {
        pushWebhookData.setRef(ref);
        getProjectBasics();

        testValidData();
        testValidCreate();
        testValidDelete();
    }

    private void testValidData() {
        pushWebhookData.setBefore(UUID.randomUUID().toString().trim());
        pushWebhookData.setAfter(UUID.randomUUID().toString().trim());

        validateEmbed("Pushed to " + getTarget(pushWebhookData.getRef()).toLowerCase());
    }

    private void testValidCreate() {
        pushWebhookData.setBefore(EMPTY_COMMIT_SHA);
        pushWebhookData.setAfter(UUID.randomUUID().toString().trim());

        validateEmbed(getTarget(pushWebhookData.getRef()) + " was created");
    }

    private void testValidDelete() {
        pushWebhookData.setBefore(UUID.randomUUID().toString().trim());
        pushWebhookData.setAfter(EMPTY_COMMIT_SHA);
        pushWebhookData.setCommits(Collections.emptyList());

        EmbedBuilder builder = new EmbedBuilder();
        endpoint.applyWebhookData(builder);

        assertEquals(getTarget(pushWebhookData.getRef()) + " was deleted", builder.build().getTitle());
        assertNull(builder.build().getDescription());
    }

    private void validateEmbed(String expectedTitle) {
        EmbedBuilder builder = new EmbedBuilder();
        endpoint.applyWebhookData(builder);

        assertEquals(expectedTitle, builder.build().getTitle());
        assertEquals(getCommits(), builder.build().getDescription());
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

        pushWebhookData.setCommits(createSampleCommits());
        pushWebhookData.setProject(webhookProjectData);
    }
}