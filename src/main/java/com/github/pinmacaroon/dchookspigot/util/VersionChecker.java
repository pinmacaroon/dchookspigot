package com.github.pinmacaroon.dchookspigot.util;

import com.github.pinmacaroon.dchookspigot.Dchookspigot;
import com.github.zafarkhaja.semver.Version;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class VersionChecker {
    public static void checkVersion(){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api.modrinth.com/v2/project/qJ9ZfKma/version"))
                    .build();

            HttpResponse<String> response;
            try{
                response = Dchookspigot.HTTPCLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception exception) {
                System.out.println("%s:%S".formatted(exception.getClass().getName(), exception.getMessage()));
                return;
            }
            int status = response.statusCode();
            JsonArray body = Dchookspigot.GSON.fromJson(response.body(), JsonArray.class);
            if(status != 200){
                System.out.println("the version list couldn't be received, modrinth said sent a 404");
                return;
            }
            Optional<Version> remoteVersion = Version.tryParse(body.get(0).getAsJsonObject().get("version_number")
                    .getAsString());
            if(remoteVersion.isEmpty()){
                System.out.println("remote version number is invalid");
                return;
            }
            if(Dchookspigot.VERSION.withoutBuildMetadata().isLowerThan(remoteVersion.get().withoutBuildMetadata())){
                System.out.println("""
                        you are running an older version of the mod! please update to %s! \
                        link: %s""".formatted(
                        remoteVersion.get(),
                        body.get(0).getAsJsonObject()
                                .get("files").getAsJsonArray()
                                .get(0).getAsJsonObject()
                                .get("url").getAsString()
                ));
            }
            else if(Dchookspigot.VERSION.withoutBuildMetadata().isHigherThan(remoteVersion.get().withoutBuildMetadata())){
                System.out.println("""
						
						!!!!!!!!!!!!!!!!!
						you are running an unreleased version! please do not use this unless you know what you are \
						doing
						!!!!!!!!!!!!!!!!!""");
            }
        } catch (Exception exception) {
            System.out.println("%s:%S".formatted(exception.getClass().getName(), exception.getMessage()));
        }
    }
}
