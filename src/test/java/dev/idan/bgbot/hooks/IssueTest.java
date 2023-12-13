package dev.idan.bgbot.hooks;

import dev.idan.bgbot.data.issue.IssueWebhookData;
import net.dv8tion.jda.api.EmbedBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IssueTest {

    @Autowired
    Endpoint endpoint;

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
        endpoint.applyWebhookData(builder);

        assertEquals(expectedTitle, builder.build().getTitle());
        assertEquals(issueWebhookData.getObjectAttributes().getDescription(), builder.build().getDescription());
    }
}
