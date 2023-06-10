package dev.idan.gitlab.monitor.hooks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.idan.gitlab.monitor.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class PushEvent implements HookType {

    @Override
    public void process(ObjectNode objectNode, String instanceURL, Token token, TextChannel channel) {
        if (channel == null) {
            System.out.println("Log channel was not found");
            return;
        }

        // analyze the json objects
        String userName = objectNode.get("user_username").asText();
        String userLink = instanceURL + "/" + userName;
        String userAvatar = objectNode.get("user_avatar").asText();
        String projectName = objectNode.get("project").get("path_with_namespace").asText();
        String ref = objectNode.get("ref").asText();
        String after = objectNode.get("after").asText();
        String before = objectNode.get("before").asText();

        List<JsonNode> commits = iteratorToList(objectNode.get("commits").elements());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(5, commits.size()); i++) {
            ObjectNode commit = (ObjectNode) commits.get(i);
            String commitID = commit.get("id").asText().substring(0, 7);
            String commitMessage = commit.get("message").asText();
            String commitLink = commit.get("url").asText();

            sb.append(String.format("[`%s`](%s)", commitID, commitLink));
            sb.append(" ");
            sb.append(commitMessage);
            sb.append("\n");
        }

        String target = getTarget(ref);

        if (after.equals("0000000000000000000000000000000000000000")) {
            channel.sendMessageEmbeds(
                    new EmbedBuilder()
                            .setTitle(String.format("Branch %s was deleted", target))
                            .setAuthor(userName, userLink, userAvatar)
                            .setDescription(sb.toString())
                            .setFooter(projectName)
                            .setTimestamp(Instant.now())
                            .build()
            ).queue();
        } else if (before.equals("0000000000000000000000000000000000000000")) {
            channel.sendMessageEmbeds(
                    new EmbedBuilder()
                            .setTitle(String.format("Branch %s was created", target))
                            .setAuthor(userName, userLink, userAvatar)
                            .setDescription(sb.toString())
                            .setFooter(projectName)
                            .setTimestamp(Instant.now())
                            .build()
            ).queue();
        }else {
            channel.sendMessageEmbeds(
                    new EmbedBuilder()
                            .setTitle(String.format("Pushed to %s", target))
                            .setAuthor(userName, userLink, userAvatar)
                            .setDescription(sb.toString())
                            .setFooter(projectName)
                            .setTimestamp(Instant.now())
                            .build()
            ).queue();
        }
    }

    static String getTarget(String ref) {
        if (ref.startsWith("refs/heads/"))
            return String.format("branch %s", ref.replace("refs/heads/", ""));
        else if (ref.startsWith("refs/tags"))
            return String.format("tag %s", ref.replace("refs/tags/", ""));
        return ref;
    }

    static <T> List<T> iteratorToList(Iterator<T> iter) {
        List<T> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(iter.next());
        }
        return list;
    }
}