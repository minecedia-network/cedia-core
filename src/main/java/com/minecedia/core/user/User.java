package com.minecedia.core.user;

import com.minecedia.core.country.Country;
import com.minecedia.core.user.bukkit.UserBukkit;
import com.minecedia.core.user.database.UserDatabase;
import com.minecedia.core.user.utils.UserUtils;
import com.minecedia.core.utils.CediaUtils;
import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public class User {

    private final UUID uid;
    private final String name;
    private final String texture;
    private final String signature;
    private final String shortTexture;
    private final Country country;
    private final UserBukkit bukkit;
    private final UserDatabase database;

    public User(Player player) {
        String[] textureAndSignature = UserUtils.loadTexture(this);

        this.uid = player.getUniqueId();
        this.name = player.getName();
        this.texture = textureAndSignature[0];
        this.signature = textureAndSignature[1];
        this.shortTexture = textureAndSignature[2];
        this.country = Country.getByCode(CediaUtils.getCountryCodeByPlayer(player));
        this.bukkit = new UserBukkit(this, player);
        this.database = new UserDatabase(this);
    }

    public User(BsonDocument document) {
        this.uid = document.getBinary("uid").asUuid();
        this.name = document.getString("name").getValue();
        this.texture = document.getString("texture").getValue();
        this.signature = document.getString("signature").getValue();
        this.shortTexture = document.getString("shortTexture").getValue();
        this.country = Country.getByCode(document.getString("country").getValue());
        this.bukkit = new UserBukkit(this, document.getDocument("bukkit"));
        this.database = new UserDatabase(this);
    }

    @Nullable
    public Player asPlayer() {
        return Bukkit.getPlayer(this.uid);
    }

    public OfflinePlayer asOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.uid);
    }

    public UUID getUID() {
        return this.uid;
    }

    public String getName() {
        return this.name;
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

    public Country getCountry() {
        return this.country;
    }

    public UserBukkit getBukkit() {
        return this.bukkit;
    }

    public UserDatabase getDatabase() {
        return this.database;
    }

    public BsonDocument toQueryDocument() {
        return new BsonDocument("uid", new BsonBinary(this.uid));
    }

    public BsonDocument toBsonDocument() {
        BsonDocument document = this.toQueryDocument();
        document.put("name", new BsonString(this.name));
        document.put("country", new BsonString(this.country.getCode()));
        document.put("texture", new BsonString(this.texture));
        document.put("signature", new BsonString(this.signature));
        document.put("shortTexture", new BsonString(this.shortTexture));
        document.put("bukkit", this.bukkit.toBsonDocument());
        return document;
    }
}