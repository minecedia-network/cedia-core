package com.minecedia.core.user;

import com.minecedia.core.country.Country;
import com.minecedia.core.database.DatabaseObject;
import com.minecedia.core.user.database.UserDatabase;
import com.minecedia.core.user.database.UserField;
import com.minecedia.core.user.skin.UserSkinData;
import com.minecedia.core.user.storage.UserStorage;
import com.minecedia.core.utils.CountryUtil;
import org.bson.BsonDocument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public class User implements DatabaseObject {

    private final UUID uid;
    private final String name;
    private final Country country;
    private final UserSkinData skinData;
    private final UserStorage storage;
    private final UserDatabase database;
    private final long firstPlayed;
    private long lastPlayed;

    public User(Player player) {
        this.uid = player.getUniqueId();
        this.name = player.getName();
        this.firstPlayed = player.getFirstPlayed();
        this.lastPlayed = player.getLastPlayed();
        this.country = Country.getByCode(CountryUtil.findCountryCode(player));
        this.skinData = new UserSkinData(this);
        this.storage = new UserStorage(this);
        this.database = new UserDatabase(this);
    }

    public User(BsonDocument document) {
        this.uid = document.getBinary(UserField.UID.getField()).asUuid();
        this.name = document.getString(UserField.NAME.getField()).getValue();
        this.firstPlayed = document.getInt64(UserField.FIRST_PLAYED.getField()).getValue();
        this.lastPlayed = document.getInt64(UserField.LAST_PLAYED.getField()).getValue();
        this.country = Country.getByCode(document.getString(UserField.COUNTRY.getField()).getValue());
        this.skinData = new UserSkinData(this, document.getDocument(UserField.SKIN_DATA.getField()));
        this.storage = new UserStorage(this, document.getDocument(UserField.STORAGE.getField()));
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

    public Country getCountry() {
        return this.country;
    }

    public UserSkinData getSkinData() {
        return this.skinData;
    }

    public UserStorage getStorage() {
        return this.storage;
    }

    public UserDatabase getDatabase() {
        return this.database;
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


    /*
    DATABASE HANDLERS
     */
    public BsonDocument toQueryDocument() {
        return new BsonDocument(UserField.UID.getField(), UserField.UID.getValue(this));
    }

    @Override
    public BsonDocument toBsonDocument() {
        BsonDocument document = new BsonDocument();
        for (UserField field : UserField.values())
            document.put(field.getField(), field.getValue(this));
        return document;
    }
}