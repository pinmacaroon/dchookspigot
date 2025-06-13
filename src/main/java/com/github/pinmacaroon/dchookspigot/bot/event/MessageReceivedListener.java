package com.github.pinmacaroon.dchookspigot.bot.event;

import com.github.pinmacaroon.dchookspigot.Dchookspigot;
import com.github.pinmacaroon.dchookspigot.bot.Bot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageReceivedListener extends ListenerAdapter {
    private final Bot BOT;

    public MessageReceivedListener(Bot bot) {
        this.BOT = bot;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getGuild().getIdLong() != this.BOT.getGUILD_ID()) return;
        if (event.getMessage().getAuthor().isBot()) return;
        if (event.getChannel().getIdLong() == this.BOT.getCHANNEL_ID()) {
            if(event.getMessage().getContentStripped().endsWith("//")
                    && Dchookspigot.CONFIG.getBoolean("functions.allow_ooc_messages")) return;
            Dchookspigot.getPlugin(Dchookspigot.class).getServer().broadcastMessage(renderMessage(event.getMessage()));
        }
    }

    private static String renderMessage(Message message) {
        final String raw_message = message.getContentDisplay();
        String signature;
        String reply;
        String content;

        if (message.getMessageReference() != null) {
            reply = "§9<@%s -> ".formatted(
                    message.getReferencedMessage().getAuthor().getName()
            );
        } else {
            reply = "§9<";
        }

        signature = "@%s> ".formatted(
                message.getAuthor().getName()
        );

        content = (raw_message.isBlank())
                ? "[embed]"
                : raw_message;

        return reply + signature + content;
    }
}
