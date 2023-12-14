package dev.idan.bgbot.hooks;

import dev.idan.bgbot.data.issue.IssueWebhookData;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static dev.idan.bgbot.utils.TestHelper.applyWebhookData;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IssueTest {

    @Autowired
    JDA jda;

    @Autowired
    IssueWebhookData issueWebhookData;

    @Test
    void testIssue() {
        issueWebhookData.getObjectAttributes().setIid(1);
        issueWebhookData.getObjectAttributes().setTitle("TestIssue");

        openIssue("open");
    }

    private void openIssue(String action) {
        issueWebhookData.getObjectAttributes().setAction(action);

        validateEmbed("Open issue: #1 TestIssue");
    }

    private void validateEmbed(String expectedTitle) {
        EmbedBuilder builder = new EmbedBuilder();
        applyWebhookData(issueWebhookData, jda, builder);

        assertEquals(expectedTitle, builder.build().getTitle());
        assertEquals(issueWebhookData.getObjectAttributes().getDescription(), builder.build().getDescription());
    }
}