package com.minecedia.core.user;

import com.minecedia.core.country.Country;
import com.minecedia.core.user.texture.CediaUserTexture;
import com.minecedia.core.utils.CediaUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class CediaUser {

    private final UUID uid;
    private final String name;
    private final Country country;
    private final CediaUserTexture userTexture;

    public CediaUser(Player player) {
        this.uid = player.getUniqueId();
        this.name = player.getName();
        this.country = Country.getByCode(CediaUtils.getCountryCodeByPlayer(player));
        this.userTexture = CediaUserTexture.loadAndRegister(player.getName());
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

    public CediaUserTexture getUserTexture() {
        return this.userTexture;
    }

    public String getTexture() {
        return this.userTexture.getTexture();
    }

    public String getSignature() {
        return this.userTexture.getSignature();
    }

    public String getShortTexture() {
        return this.userTexture.getShortTexture();
    }

    @Nullable
    public Player asPlayer() {
        return Bukkit.getPlayer(this.uid);
    }

    @Nonnull
    public OfflinePlayer asOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.uid);
    }
}