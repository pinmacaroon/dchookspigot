package com.github.pinmacaroon.dchookspigot.util;

import com.github.pinmacaroon.dchookspigot.Dchookspigot;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldLoadEvent;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class EventListeners implements Listener {

    public static void onInit() {
        if (!Dchookspigot.CONFIG.getBoolean("messages.server.starting.allowed")) return;
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + Dchookspigot.CONFIG.getString("messages.server.starting.message", "") + "**");
        request_body.put("username", "server");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + MarkdownSanitizer.escape(event.getJoinMessage()) + "**");
        request_body.put("username", "game");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + MarkdownSanitizer.escape(event.getQuitMessage()) + "**");
        request_body.put("username", "game");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + MarkdownSanitizer.escape(event.getDeathMessage()) + "**");
        request_body.put("username", "game");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "** %s has done the advancement [%s]!**".formatted(
                MarkdownSanitizer.escape(event.getPlayer().getName()),
                event.getAdvancement().getDisplay().getTitle()
        ));
        request_body.put("username", "game");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", MarkdownSanitizer.escape(event.getMessage()));
        request_body.put("username", MarkdownSanitizer.escape(event.getPlayer().getName()));
        request_body.put("avatar_url", "https://crafthead.net/helm/" + event.getPlayer().getUniqueId());

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        if (!Dchookspigot.CONFIG.getBoolean("messages.server.started.allowed")) return;
        if (!event.getWorld().isBedWorks()) return;
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + Dchookspigot.CONFIG.getString("messages.server.started.message", "") + "**");
        request_body.put("username", "server");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
