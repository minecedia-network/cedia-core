package com.minecedia.core.user.bukkit;

import com.hakan.core.HCore;
import com.minecedia.core.user.User;
import com.minecedia.core.utils.LocationUtils;
import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public class UserBukkit {

    private final User user;
    private int food;
    private int experience;
    private double health;
    private double maxHealth;
    private Location location;
    private Collection<PotionEffect> potion;
    private Map<Integer, ItemStack> inventory;

    public UserBukkit(User user, Player player) {
        this.user = user;
        this.load(player);
    }

    public UserBukkit(User user, BsonDocument document) {
        this.user = user;
        this.food = document.getInt32("food").getValue();
        this.experience = document.getInt32("experience").getValue();
        this.health = document.getDouble("health").getValue();
        this.maxHealth = document.getDouble("maxHealth").getValue();
        this.location = LocationUtils.deserialize(document.getString("location").getValue());
        this.potion = HCore.deserialize(document.getString("potion").getValue(), Collection.class);
        this.inventory = HCore.deserialize(document.getString("inventory").getValue(), Map.class);
    }

    public User getUser() {
        return this.user;
    }

    public double getHealth() {
        return this.health;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public int getFood() {
        return this.food;
    }

    public int getExperience() {
        return this.experience;
    }

    public Location getLocation() {
        return this.location;
    }

    public Collection<PotionEffect> getPotion() {
        return this.potion;
    }

    public Map<Integer, ItemStack> getInventory() {
        return this.inventory;
    }

    public void update(Player player) {
        player.setHealth(this.health);
        player.setHealthScale(this.maxHealth);
        player.setFoodLevel(this.food);
        player.setTotalExperience(this.experience);
        player.teleport(this.location);
        player.addPotionEffects(this.potion);
        this.inventory.forEach((slot, item) -> player.getInventory().setItem(slot, item));
    }

    public void load(Player player) {
        this.health = player.getHealth();
        this.maxHealth = player.getHealthScale();
        this.food = player.getFoodLevel();
        this.experience = player.getTotalExperience();
        this.location = player.getLocation();
        this.potion = player.getActivePotionEffects();
        this.inventory = new HashMap<>();

        for (int i = 0; i < player.getInventory().getSize() + 4; i++)
            this.inventory.put(i, player.getInventory().getItem(i));
    }

    public BsonDocument toBsonDocument() {
        BsonDocument document = new BsonDocument();
        document.put("food", new BsonInt32(this.food));
        document.put("experience", new BsonInt32(this.experience));
        document.put("health", new BsonDouble(this.health));
        document.put("maxHealth", new BsonDouble(this.maxHealth));
        document.put("location", new BsonString(LocationUtils.serialize(this.location)));
        document.put("potion", new BsonString(HCore.serialize(this.potion)));
        document.put("inventory", new BsonString(HCore.serialize(this.inventory)));
        return document;
    }
}