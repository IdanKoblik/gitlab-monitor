package dev.idan.bgbot.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface UserNameAndAvatar {

    @JsonIgnore
    String getAuthorName();

    @JsonIgnore
    String getAuthorAvatarUrl();

}
