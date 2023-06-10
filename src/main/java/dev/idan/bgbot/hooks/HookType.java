package dev.idan.bgbot.hooks;

import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public interface HookType {

    void process(ObjectNode objectNode, String instanceURL, Token token, TextChannel channel);

}
