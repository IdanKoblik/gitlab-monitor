package dev.idan.bgbot.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface AuthorEmail {

    @JsonIgnore
    String getEmail();
}
