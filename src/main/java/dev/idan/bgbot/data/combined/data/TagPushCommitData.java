package dev.idan.bgbot.data.combined.data;

import lombok.Getter;

@Getter
public class TagPushCommitData extends TagPushPipelineCommitData {

    String title;

    private TagPushPipelineCommitAuthorData author;
}
