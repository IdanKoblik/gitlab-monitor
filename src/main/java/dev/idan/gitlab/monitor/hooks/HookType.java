package dev.idan.gitlab.monitor.hooks;

import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.idan.gitlab.monitor.entities.Token;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public interface HookType {

    void process(ObjectNode objectNode, String instanceURL, Token token, TextChannel channel);

}
