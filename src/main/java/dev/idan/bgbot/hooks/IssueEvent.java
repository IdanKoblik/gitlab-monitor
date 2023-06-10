package dev.idan.bgbot.hooks;

import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.utils.PartialImage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class IssueEvent implements HookType {

    public void process(ObjectNode objectNode, String instanceURL, Token token, TextChannel channel) {
        // analyze the json objects
        String userName = objectNode.get("user").get("username").asText();
        String userLink = instanceURL + "/" + userName;
        String userAvatar = objectNode.get("user").get("avatar_url").asText();
        String projectName = objectNode.get("project").get("path_with_namespace").asText();
        String action = objectNode.get("object_attributes").get("action").asText();
        String issueDescription = objectNode.get("object_attributes").get("description").asText();
        String issueUrl = objectNode.get("object_attributes").get("url").asText();
        String iid = objectNode.get("object_attributes").get("iid").asText();
        String issueTitle = objectNode.get("object_attributes").get("title").asText();
        String userMail = objectNode.get("user").get("email").asText();

        String avatar = PartialImage.getEmail(userAvatar, userMail, token);

        channel.sendMessageEmbeds(
                new EmbedBuilder()
                        .setAuthor(userName, userLink, avatar)
                        .setTitle(action + " Issue: " + '#' + iid + " " + issueTitle, issueUrl)
                        .setDescription(issueDescription)
                        .setFooter(projectName)
                        .setTimestamp(Instant.now())
                        .build()
        ).queue();
    }
}
