package dev.idan.bgbot.hooks;

import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.utils.PartialImage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CommentEvent implements HookType {
    public void process(ObjectNode objectNode, String instanceURL, Token token, TextChannel channel) {
        if (channel == null) {
            System.out.println("Log channel was not found");
            return;
        }

        // analyze the json objects
        String userName = objectNode.get("user").get("username").asText();
        String userAvatar = objectNode.get("user").get("avatar_url").asText();
        String projectName = objectNode.get("project").get("path_with_namespace").asText();
        String userLink = instanceURL + "/" + userName;
        String noteableType = objectNode.get("object_attributes").get("noteable_type").asText();
        String note = objectNode.get("object_attributes").get("note").asText();
        String url = objectNode.get("object_attributes").get("url").asText();
        String userMail = objectNode.get("user").get("email").asText();

        String avatar = PartialImage.getEmail(userAvatar, userMail, token);

        EmbedBuilder builder = new EmbedBuilder();
        if (!noteableType.equals("Issue"))
            builder.setTitle(userName + " Commented on " + noteableType, url);

        String issueTitle = objectNode.get("issue").get("title").asText();
        String issueIid = objectNode.get("issue").get("iid").asText();
        builder.setTitle(userName + " Commented on " + noteableType + ": " + issueTitle + " (#" + issueIid + ")", url);

        builder.setAuthor(userName, userLink, avatar);
        builder.setDescription(note);
        builder.setFooter(projectName);
        builder.setTimestamp(Instant.now());

        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
