package dev.idan.bgbot.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface UserEmail {

    @JsonIgnore
    String getEmail();
}
