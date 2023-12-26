package dev.idan.bgbot.hooks;

import dev.idan.bgbot.data.merge.MergeWebhookData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MergeTest {

    @Autowired
    MergeWebhookData mergeWebhookData;

    /*
            builder.setTitle(String.format("%s merge request from branch %s to branch %s",
                objectAttributes.getAction().substring(0, 1).toUpperCase()
                        + objectAttributes.getAction().substring(1),
                objectAttributes.getSourceBranch(),
                objectAttributes.getTargetBranch()
        ), objectAttributes.getUrl());
     */


}
