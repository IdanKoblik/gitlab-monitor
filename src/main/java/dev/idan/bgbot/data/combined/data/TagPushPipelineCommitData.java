package dev.idan.bgbot.data.combined.data;

import lombok.Getter;

@Getter
public class TagPushPipelineCommitData {

    String id;

    String message;

    String timestamp;

    String url;

    private TagPushPipelineCommitAuthorData author;
}
