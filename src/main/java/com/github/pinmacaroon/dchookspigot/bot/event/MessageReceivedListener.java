package com.github.pinmacaroon.dchookspigot.bot.event;

import com.github.pinmacaroon.dchook.Hook;
import com.github.pinmacaroon.dchook.bot.Bot;
import com.github.pinmacaroon.dchook.conf.ModConfigs;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
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
        if (event.getChannel().getIdLong() == this.BOT.getCHANNEL_ID() && Hook.getGameServer() != null) {
            if (event.getMessage().getContentStripped().endsWith("//") && ModConfigs.FUNCTIONS_ALLOWOOCMESSAGES) return;
            Hook.getGameServer().getPlayerManager().broadcast(renderMessage(event.getMessage()), false);
        }
    }

    private static MutableText renderMessage(Message message) {
        final String raw_message = message.getContentDisplay();
        MutableText signature;
        MutableText reply;
        MutableText content;

        if (message.getMessageReference() != null) {
            reply = Text.literal("<@%s -> ".formatted(
                    message.getReferencedMessage().getAuthor().getName()
            ));
        } else {
            reply = Text.literal("<");
        }

        signature = Text.literal("@%s> ".formatted(
                message.getAuthor().getName()
        ));

        content = (raw_message.isBlank())
                ? Text.literal("[embed]")
                : Text.literal(raw_message);

        return reply.append(signature).append(content).formatted(Formatting.BLUE);
    }
}
