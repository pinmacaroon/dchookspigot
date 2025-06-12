package com.github.pinmacaroon.dchookspigot.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.time.Instant;

public class AboutCommand {
    public static void run(SlashCommandInteractionEvent event) {
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("dchook", "https://modrinth.com/mod/dchook")
                .setAuthor("pinmacaroon", "https://pinmacaroon.github.io/",
                        "https://pinmacaroon.github.io/res/favicon.png")
                .setDescription("""
                        This bot is operated by [Discord Webhook Wizard](https://modrinth.com/mod/dchook), aka \
                        **dchook**, a mod that allows **minecraft chat integration** with discord with simple \
                        configuration and straightforward features. Implemented both as a **fabric mod** and a \
                        **bukkit/spigot plugin**! All this for free and as an open-source service!""")
                .addField("Get started", """
                        Want to integrate this with your own server? Want to experiment? Get started by visiting the \
                        mod's Modrinth page at <https://modrinth.com/mod/dchook>!""", false)
                .setThumbnail("https://pinmacaroon.github.io/hook/res/b8b3416f1d61e8679b10cf07dff4e5eeb9a2e86e_96.webp")
                .setImage("https://cdn.modrinth.com/data/qJ9ZfKma/images/0e822ef2aec1062fd27973191cb2cf85a5734910.png")
                .setTimestamp(Instant.now())
                .setFooter("This message was triggered by %s using the /%s command!".formatted(
                        event.getMember().getUser().getName(), event.getFullCommandName()
                ))
                .setColor(Color.BLUE)
                .build();
        event.replyEmbeds(embed).setEphemeral(event.getOption("ephemeral", false, OptionMapping::getAsBoolean)).queue();
    }
}
