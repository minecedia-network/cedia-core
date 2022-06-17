package com.minecedia.core.user.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtils {

    public static String[] loadTexture(String playerName) {
        try {
            Objects.requireNonNull(playerName, "player name cannot be null!");

            URL url1 = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            InputStreamReader reader_0 = new InputStreamReader(url1.openStream());
            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader read = new InputStreamReader(url2.openStream());
            JsonObject textureProperty = new JsonParser().parse(read).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();

            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();

            Matcher matcher = Pattern.compile("\"(http://textures\\.minecraft\\.net/texture/)(?<shortTexture>\\w+)\"")
                    .matcher(new String(Base64.getDecoder().decode(texture.getBytes())));
            String shortTexture = matcher.find() ? matcher.group("shortTexture") : "";

            return new String[]{texture, signature, shortTexture};
        } catch (Exception e) {
            return new String[]{"", "", ""};
        }
    }
}