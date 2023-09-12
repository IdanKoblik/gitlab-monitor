package dev.idan.bgbot.hooks;

import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.utils.PartialImage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class MergeEvent implements HookType{

    public void process(ObjectNode objectNode, String instanceURL, Token token, TextChannel channel) {
        if (channel == null) {
            System.out.println("Log channel was not found");
            return;
        }

        // analyze the json objects
        String userName = objectNode.get("user").get("username").asText();
        String userLink = instanceURL + "/" + userName;
        String userAvatar = objectNode.get("user").get("avatar_url").asText();
        String sourceBranch = objectNode.get("object_attributes").get("source_branch").asText();
        String targetBranch = objectNode.get("object_attributes").get("target_branch").asText();
        String action = objectNode.get("object_attributes").get("action").asText();
        String mergeUrl = objectNode.get("object_attributes").get("url").asText();
        String projectName = objectNode.get("project").get("path_with_namespace").asText();
        String userMail = objectNode.get("user").get("email").asText();

        String avatar = PartialImage.getEmail(userAvatar, userMail, token);

        channel.sendMessageEmbeds(
                new EmbedBuilder()
                        .setAuthor(userName, userLink, avatar)
                        .setFooter(projectName)
                        .setTitle(userName + " " + action + " merge request from " + sourceBranch + " to " + targetBranch, mergeUrl)
                        .setTimestamp(Instant.now())
                        .build()
        ).queue();
    }
}
