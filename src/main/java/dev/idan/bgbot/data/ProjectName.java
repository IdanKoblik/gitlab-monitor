package dev.idan.bgbot.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ProjectName {

    @JsonIgnore
    String getProjectName();
}
