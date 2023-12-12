package dev.idan.bgbot.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ProjectNameAndUrl {

    @JsonIgnore
    String getProjectName();

    @JsonIgnore
    String getProjectUrl();
}
