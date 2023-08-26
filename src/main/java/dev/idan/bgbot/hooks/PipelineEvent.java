package dev.idan.bgbot.hooks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.idan.bgbot.entities.Token;
import dev.idan.bgbot.utils.PartialImage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Component
public class PipelineEvent implements HookType {

    public void process(ObjectNode objectNode, String instanceURL, Token token, TextChannel channel) {
        if (channel == null) {
            System.out.println("Log channel was not found");
            return;
        }

        // analyze the json objects
        String projectName = objectNode.get("project").get("path_with_namespace").asText();
        String userName = objectNode.get("user").get("username").asText();
        String userAvatar = objectNode.get("user").get("avatar_url").asText();
        String userLink = instanceURL + "/" + userName;
        String iid = objectNode.get("object_attributes").get("iid").asText();
        String ref = objectNode.get("object_attributes").get("ref").asText();
        String status = objectNode.get("object_attributes").get("status").asText();
        String duration = objectNode.get("object_attributes").get("duration").asText();
        String pipelineId = objectNode.get("object_attributes").get("id").asText();
        String url = instanceURL + "/" + projectName + "/-/pipelines/" + pipelineId;
        String userMail = objectNode.get("user").get("email").asText();

        String avatar = PartialImage.getEmail(userAvatar, userMail, token);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(userName, userLink, avatar);
        builder.setFooter(projectName);
        builder.setTimestamp(Instant.now());

        if (status.equals("success")) {
            builder.setTitle("Pipeline " + "#" + iid + " of branch " + ref + " by " + userName + " has passed in: " + duration + " seconds", url);
            channel.sendMessageEmbeds(builder.build()).queue();
        }

        if (status.equals("skipped")) {
            builder.setTitle("Pipeline " + "#" + iid + " of branch " + ref + " by " + userName + " has been skipped", url);
            channel.sendMessageEmbeds(builder.build()).queue();
        }

        if (status.equals("failed")) {
            String failedReason = Optional.ofNullable(objectNode.get("builds"))
                    .map(obj -> obj.get("failure_reason")).map(JsonNode::asText).orElse("No reason provided");
            builder.setTitle("Pipeline " + "#" + iid + " of branch " + ref + " by " + userName + " has been failed", url);

            Role role = channel.getGuild().getRoleById(token.getNotifyRoleID());
            builder.setDescription("Reason: " + failedReason + "\n");
            if (role != null) builder.appendDescription(role.getAsMention());

            channel.sendMessageEmbeds(builder.build()).queue();
        }
    }
}
