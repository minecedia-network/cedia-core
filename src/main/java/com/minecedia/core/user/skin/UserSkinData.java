package com.minecedia.core.user.skin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.minecedia.core.database.DatabaseObject;
import com.minecedia.core.user.User;
import org.bson.BsonDocument;
import org.bson.BsonString;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSkinData implements DatabaseObject {

    public static String[] loadSkinData(User user) {
        return UserSkinData.loadSkinData(user.getName());
    }

    public static String[] loadSkinData(String playerName) {
        try {
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


    private final User user;
    private String texture;
    private String signature;
    private String shortTexture;

    public UserSkinData(User user) {
        String[] textureAndSignature = UserSkinData.loadSkinData(user);

        this.user = user;
        this.texture = textureAndSignature[0];
        this.signature = textureAndSignature[1];
        this.shortTexture = textureAndSignature[2];
    }

    public UserSkinData(User user, BsonDocument document) {
        this.user = user;
        this.texture = document.getString("texture").getValue();
        this.signature = document.getString("signature").getValue();
        this.shortTexture = document.getString("short_texture").getValue();
    }

    public User getUser() {
        return this.user;
    }

    public String getTexture() {
        return this.texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getShortTexture() {
        return this.shortTexture;
    }

    public void setShortTexture(String shortTexture) {
        this.shortTexture = shortTexture;
    }


    /*
    DATABASE HANDLERS
     */
    @Override
    public BsonDocument toBsonDocument() {
        BsonDocument document = new BsonDocument();
        document.put("texture", new BsonString(this.texture));
        document.put("signature", new BsonString(this.signature));
        document.put("short_texture", new BsonString(this.shortTexture));
        return document;
    }
}