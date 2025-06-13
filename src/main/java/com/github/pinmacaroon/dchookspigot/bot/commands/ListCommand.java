package com.github.pinmacaroon.dchookspigot.bot.commands;

import com.github.pinmacaroon.dchookspigot.Dchookspigot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class ListCommand {
    public static void run(SlashCommandInteractionEvent event) {
        StringBuilder list = new StringBuilder();
        list.append("""
                There are currently **%d**/%d players online:\s""".formatted(
                Dchookspigot.getPlugin(Dchookspigot.class).getServer().getOnlinePlayers().toArray().length,
                Dchookspigot.getPlugin(Dchookspigot.class).getServer().getMaxPlayers()
        ));
        Dchookspigot.getPlugin(Dchookspigot.class).getServer().getOnlinePlayers().forEach(
                player -> list.append("`").append(player.getName()).append("` ")
        );
        event.reply(list.toString()).setEphemeral(event.getOption("ephemeral", false, OptionMapping::getAsBoolean))
                .queue();
    }
}
