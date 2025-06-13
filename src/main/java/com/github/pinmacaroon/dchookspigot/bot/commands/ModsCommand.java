package com.github.pinmacaroon.dchookspigot.bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class ModsCommand {
    public static void run(SlashCommandInteractionEvent event) {
        event.reply("The server currently has no required mods, you can join with a vanilla client!")
                .setEphemeral(event.getOption("ephemeral", false, OptionMapping::getAsBoolean)).queue();
    }
}
