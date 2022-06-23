package com.minecedia.core.user;

import com.minecedia.core.CediaCore;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class User implements DatabaseObject {

    private final @NotNull UUID uid;
    private final @NotNull String name;

    private final @NotNull Country country;

    private final @NotNull UserSkinData skinData;
    private final @NotNull UserStorage storage;
    private final @NotNull UserDatabase database;

    private final long firstPlayed;
    private long lastPlayed;

    public User(@NotNull CediaCore core, @NotNull Player player) {
        this.uid = player.getUniqueId();
        this.name = player.getName();
        this.firstPlayed = player.getFirstPlayed();
        this.lastPlayed = player.getLastPlayed();
        this.country = Objects.requireNonNull(core.countryManager().get(CountryUtil.findCountryCode(player)));
        this.skinData = new UserSkinData(this);
        this.storage = new UserStorage(this);
        this.database = new UserDatabase(this);
    }

    public User(@NotNull CediaCore core, BsonDocument document) {
        this.uid = document.getBinary(UserField.UID.field()).asUuid();
        this.name = document.getString(UserField.NAME.field()).getValue();
        this.firstPlayed = document.getInt64(UserField.FIRST_PLAYED.field()).getValue();
        this.lastPlayed = document.getInt64(UserField.LAST_PLAYED.field()).getValue();
        this.country = Objects.requireNonNull(core.countryManager().get(document.getString(UserField.COUNTRY.field()).getValue()));
        this.skinData = new UserSkinData(this, document.getDocument(UserField.SKIN_DATA.field()));
        this.storage = new UserStorage(this, document.getDocument(UserField.STORAGE.field()));
        this.database = new UserDatabase(this);
    }

    public @Nullable Player asPlayer() {
        return Bukkit.getPlayer(this.uid);
    }

    public @NotNull OfflinePlayer asOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.uid);
    }

    public @NotNull UUID uid() {
        return this.uid;
    }

    public @NotNull String name() {
        return this.name;
    }

    public @NotNull Country country() {
        return this.country;
    }

    public @NotNull UserSkinData skin() {
        return this.skinData;
    }

    public @NotNull UserStorage storage() {
        return this.storage;
    }

    public @NotNull UserDatabase database() {
        return this.database;
    }

    public long firstPlayed() {
        return this.firstPlayed;
    }

    public long lastPlayed() {
        return this.lastPlayed;
    }

    public void setLastPlayed(long time) {
        this.lastPlayed = time;
    }


    /*
    DATABASE HANDLERS
     */
    public @NotNull BsonDocument toQueryDocument() {
        return new BsonDocument(UserField.UID.field(), UserField.UID.value(this));
    }

    @Override
    public @NotNull BsonDocument toBsonDocument() {
        BsonDocument document = new BsonDocument();

        for (UserField field : UserField.values()) {
            document.put(field.field(), field.value(this));
        }

        return document;
    }
}