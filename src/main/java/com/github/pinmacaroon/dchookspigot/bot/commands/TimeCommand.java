package com.github.pinmacaroon.dchookspigot.bot.commands;

import com.github.pinmacaroon.dchook.Hook;
import com.github.pinmacaroon.dchook.util.TimeConverter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class TimeCommand {
    public static void run(SlashCommandInteractionEvent event) {
        String response = "The current in-game time in the overworld is **%s**! The weather is %s%s!".formatted(
                TimeConverter.timeOfDayToHoursMinutes2(Hook.getGameServer().getOverworld().getTimeOfDay()),
                (Hook.getGameServer().getOverworld().isRaining()) ? "rainy" : "clear",
                (Hook.getGameServer().getOverworld().isThundering()) ? " and it is thundering!" : ""
        );
        event.reply(response).setEphemeral(event.getOption("ephemeral", false, OptionMapping::getAsBoolean)).queue();
    }
}
