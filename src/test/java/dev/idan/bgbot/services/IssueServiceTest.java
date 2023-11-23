package dev.idan.bgbot.services;

import dev.idan.bgbot.exceptions.DontConnectedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class IssueServiceTest {

    @Autowired
    IssueService issueService;

    @Test
    void createIssueValidProjectId() {
        assertEquals("Successfully created an issue. ✅",
                issueService.createIssueResponse("52349384", "test", "unit test test").getBody());
    }

    @Test
    void createIssueTestInvalidProjectId() {
        assertThrows(DontConnectedException.class, () -> {
            issueService.createIssue("InvalidId", "test", "unit test test");
        });
    }
}
