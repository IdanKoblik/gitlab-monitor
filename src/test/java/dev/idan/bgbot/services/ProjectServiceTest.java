package dev.idan.bgbot.services;

import dev.idan.bgbot.exceptions.DontConnectedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    ProjectService projectService;

    @Test
    void existsByInvalidProjectId() {
        assertThrows(DontConnectedException.class, () -> {
            projectService.existsByProjectId("InvalidId");
        });
    }

}
