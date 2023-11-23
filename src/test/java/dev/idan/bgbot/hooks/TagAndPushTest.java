package dev.idan.bgbot.hooks;

import dev.idan.bgbot.data.combined.data.TagPushData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TagAndPushTest {

    @Test
    void testGetTarget() {
        assertEquals("Branch `master`", TagPushData.getTarget("refs/heads/master"));

        assertEquals("Tag `v1.0`", TagPushData.getTarget("refs/tags/v1.0"));

        assertEquals("refs/some/other", TagPushData.getTarget("refs/some/other"));
    }
}
