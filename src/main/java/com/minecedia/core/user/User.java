package com.minecedia.core.user;

import com.minecedia.core.country.Country;
import com.minecedia.core.database.DatabaseObject;
import com.minecedia.core.user.bukkit.UserBukkit;
import com.minecedia.core.user.database.UserDatabase;
import com.minecedia.core.user.database.UserField;
import com.minecedia.core.user.utils.UserUtils;
import com.minecedia.core.utils.CediaUtils;
import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public class User implements DatabaseObject {

    private final UUID uid;
    private final String name;
    private final long firstPlayed;
    private long lastPlayed;
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
        this.firstPlayed = player.getFirstPlayed();
        this.lastPlayed = player.getLastPlayed();
        this.texture = textureAndSignature[0];
        this.signature = textureAndSignature[1];
        this.shortTexture = textureAndSignature[2];
        this.country = Country.getByCode(CediaUtils.getCountryCodeByPlayer(player));
        this.bukkit = new UserBukkit(this, player);
        this.database = new UserDatabase(this);
    }

    public User(BsonDocument document) {
        this.uid = document.getBinary(UserField.UID.getField()).asUuid();
        this.name = document.getString(UserField.NAME.getField()).getValue();
        this.firstPlayed = document.getInt64(UserField.FIRST_PLAYED.getField()).getValue();
        this.lastPlayed = document.getInt64(UserField.LAST_PLAYED.getField()).getValue();
        this.texture = document.getString(UserField.TEXTURE.getField()).getValue();
        this.signature = document.getString(UserField.SIGNATURE.getField()).getValue();
        this.shortTexture = document.getString(UserField.SHORT_TEXTURE.getField()).getValue();
        this.country = Country.getByCode(document.getString(UserField.COUNTRY.getField()).getValue());
        this.bukkit = new UserBukkit(this, document.getDocument(UserField.BUKKIT.getField()));
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

    public long getFirstPlayed() {
        return this.firstPlayed;
    }

    public long getLastPlayed() {
        return this.lastPlayed;
    }

    public void setLastPlayed(long time) {
        this.lastPlayed = time;
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
        return new BsonDocument(UserField.UID.getField(), new BsonBinary(this.uid));
    }

    @Override
    public BsonDocument toBsonDocument() {
        BsonDocument document = new BsonDocument();
        for (UserField field : UserField.values())
            document.put(field.getField(), field.getValue(this));
        return document;
    }
}