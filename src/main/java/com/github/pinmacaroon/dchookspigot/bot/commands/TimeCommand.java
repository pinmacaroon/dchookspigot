package com.github.pinmacaroon.dchookspigot.bot.commands;

import com.github.pinmacaroon.dchookspigot.Dchookspigot;
import com.github.pinmacaroon.dchookspigot.util.TimeConverter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class TimeCommand {
    public static void run(SlashCommandInteractionEvent event) {
        String response = "The current in-game time in the overworld is **%s**! The weather is %s%s!".formatted(
                TimeConverter.timeOfDayToHoursMinutes2(Dchookspigot.getPlugin(Dchookspigot.class).getServer().getWorlds().get(0).getTime()),
                (!Dchookspigot.getPlugin(Dchookspigot.class).getServer().getWorlds().get(0).isClearWeather()) ? "rainy" : "clear",
                (Dchookspigot.getPlugin(Dchookspigot.class).getServer().getWorlds().get(0).isThundering()) ? " and it is thundering!" : ""
        );
        event.reply(response).setEphemeral(event.getOption("ephemeral", false, OptionMapping::getAsBoolean)).queue();
    }
}
