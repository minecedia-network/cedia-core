package com.minecedia.core.user.texture;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CediaUserTexture {

    private static final Map<String, CediaUserTexture> skullDataMap = new HashMap<>();

    public static Optional<CediaUserTexture> findByPlayer(String playerName) {
        Objects.requireNonNull(playerName, "player name cannot be null!");
        return Optional.ofNullable(skullDataMap.get(playerName));
    }

    public static CediaUserTexture getByPlayer(String playerName) {
        return findByPlayer(playerName).orElseThrow(() -> new IllegalArgumentException("skull texture not found!"));
    }

    public static CediaUserTexture register(String playerName, String texture, String signature) {
        CediaUserTexture skullData = new CediaUserTexture(playerName, texture, signature);
        skullDataMap.put(playerName, skullData);
        return skullData;
    }

    public static CediaUserTexture loadAndRegister(String playerName) {
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
            return register(playerName, texture, signature);
        } catch (Exception e) {
            return new CediaUserTexture("", "", "");
        }
    }


    private final String playerName;
    private final String texture;
    private final String signature;
    private final String shortTexture;

    public CediaUserTexture(String playerName, String texture, String signature) {
        this.playerName = playerName;
        this.texture = texture;
        this.signature = signature;

        Matcher matcher = Pattern.compile("\"(http://textures\\.minecraft\\.net/texture/)(?<shortTexture>\\w+)\"")
                .matcher(new String(Base64.getDecoder().decode(texture.getBytes())));
        this.shortTexture = matcher.find() ? matcher.group("shortTexture") : "";
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getTexture() {
        return this.texture;
    }

    public String getSignature() {
        return this.signature;
    }

    public String getShortTexture() {
        return this.shortTexture;
    }
}