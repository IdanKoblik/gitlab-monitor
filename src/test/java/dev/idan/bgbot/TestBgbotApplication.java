package dev.idan.bgbot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class TestBgbotApplication {

    @BeforeAll
    static void startup() {
        log.info("Starting unit testing for Gitlab-monitor.");
    }
}
