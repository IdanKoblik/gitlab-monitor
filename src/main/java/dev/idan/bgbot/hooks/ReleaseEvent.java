package dev.idan.bgbot.hooks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.idan.bgbot.entities.Token;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ReleaseEvent implements HookType{

    public void process(ObjectNode objectNode, String instanceURL, Token token, TextChannel channel) {
        // analyze the json objects
        String name = objectNode.get("name").asText();
        String projectName = objectNode.get("project").get("path_with_namespace").asText();
        String url = objectNode.get("url").asText();
        List<JsonNode> sources = iteratorToList(objectNode.get("assets").get("sources").elements());
        List<JsonNode> links = iteratorToList(objectNode.get("assets").get("links").elements());

        EmbedBuilder builder = new EmbedBuilder();

        for (int i = 0; i < sources.size(); i++) {
            ObjectNode source = (ObjectNode) sources.get(i);
            String sourceName = source.get("format").asText();
            String sourceLink = source.get("url").asText();
            String format = String.format("[%s](%s)", "Source code", sourceLink);
            builder.addField(sourceName, format, true);
        }

        for (int i = 0; i < links.size(); i++) {
            ObjectNode link = (ObjectNode) links.get(i);
            String linkName = link.get("name").asText();
            String linkLink = link.get("url").asText();
            String format = String.format("[%s](%s)", linkName, linkLink);
            builder.addField("link", format, true);
        }

        builder.setAuthor(name,  url);
        builder.setTitle("New Release: " + name, url);
        builder.setFooter(projectName);
        builder.setTimestamp(Instant.now());
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    static <T> List<T> iteratorToList(Iterator<T> iter) {
        List<T> list = new ArrayList<>();
        while (iter.hasNext()) {
            list.add(iter.next());
        }
        return list;
    }
}
