package dev.idan.bgbot.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateIssueRequest {

    String title;
    String description;
}
