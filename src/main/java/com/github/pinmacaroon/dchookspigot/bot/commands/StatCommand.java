package com.github.pinmacaroon.dchookspigot.bot.commands;

import com.github.pinmacaroon.dchook.Hook;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.world.GameRules;

import java.math.BigDecimal;

public class StatCommand {
    public static void run(SlashCommandInteractionEvent event) {

        String list = "Stats of this instance:\n```" +
                "uptime = %dmin%n".formatted(Hook.getGameServer().getTicks() / 20 / 60) +
                "keepinventory = %b%n".formatted(Hook.getGameServer().getGameRules().get(GameRules.KEEP_INVENTORY)
                        .get()) +
                "mcversion = %s%n".formatted(Hook.getGameServer().getVersion()) +
                "fabricmcversion = %s%n".formatted(FabricLoaderImpl.VERSION) +
                "dchookversion = %s%n".formatted(Hook.VERSION) +
                "players = %d/%d%n".formatted(Hook.getGameServer().getCurrentPlayerCount(),
                        Hook.getGameServer().getMaxPlayerCount()) +
                "memory = ~%smb%n".formatted(Math.rint(
                        (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024
                )) +
                "overworld_border = %s%n".formatted(
                        BigDecimal.valueOf((int) Hook.getGameServer().getOverworld().getWorldBorder().getSize() / 2)
                                .toPlainString()
                ) +
                "```";
        event.reply(list).setEphemeral(event.getOption("ephemeral", false, OptionMapping::getAsBoolean))
                .queue();
    }
}
