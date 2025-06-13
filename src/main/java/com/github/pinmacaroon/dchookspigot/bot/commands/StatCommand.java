package com.github.pinmacaroon.dchookspigot.bot.commands;

import com.github.pinmacaroon.dchookspigot.Dchookspigot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class StatCommand {
    public static void run(SlashCommandInteractionEvent event) {

        String list = "Stats of this instance:\n```" +
                "uptime = ?min\n" +
                "keepinventory = ?\n" +
                "mcversion = %s%n".formatted(Dchookspigot.getPlugin(Dchookspigot.class).getServer().getVersion()) +
                "bukkitversion = %s%n".formatted(Dchookspigot.getPlugin(Dchookspigot.class).getServer().getBukkitVersion()) +
                "dchookversion = %s%n".formatted(Dchookspigot.VERSION) +
                "players = %d/%d%n".formatted(
                        Dchookspigot.getPlugin(Dchookspigot.class).getServer().getOnlinePlayers().toArray().length,
                        Dchookspigot.getPlugin(Dchookspigot.class).getServer().getMaxPlayers()) +
                "memory = ~%smb%n".formatted(Math.rint(
                        (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024
                )) +
                "overworld_border = ?\n```";
        event.reply(list).setEphemeral(event.getOption("ephemeral", false, OptionMapping::getAsBoolean))
                .queue();
    }
}
