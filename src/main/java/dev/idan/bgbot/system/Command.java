package dev.idan.bgbot.system;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

public abstract class Command {
    protected abstract void execute(@NotNull SlashCommandInteractionEvent event);
    protected abstract CommandData commandData();
}
