package com.minecedia.core.user.bukkit;

import com.hakan.core.HCore;
import com.minecedia.core.database.DatabaseObject;
import com.minecedia.core.user.User;
import com.minecedia.core.utils.LocationUtils;
import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public class UserBukkit implements DatabaseObject {

    private final User user;
    private int food;
    private int experience;
    private double health;
    private double maxHealth;
    private float flySpeed;
    private float walkSpeed;
    private GameMode gameMode;
    private Location location;
    private Collection<PotionEffect> potion;
    private Map<Integer, ItemStack> inventory;

    public UserBukkit(User user, Player player) {
        this.user = user;
        this.loadFrom(player);
    }

    public UserBukkit(User user, BsonDocument document) {
        this.user = user;
        this.food = document.getInt32("food").getValue();
        this.experience = document.getInt32("experience").getValue();
        this.health = document.getDouble("health").getValue();
        this.maxHealth = document.getDouble("max_health").getValue();
        this.flySpeed = (float) document.getDouble("fly_speed").getValue();
        this.walkSpeed = (float) document.getDouble("walk_speed").getValue();
        this.gameMode = GameMode.valueOf(document.getString("game_mode").getValue());
        this.location = LocationUtils.deserialize(document.getString("location").getValue());
        this.potion = HCore.deserialize(document.getString("potions").getValue(), Collection.class);
        this.inventory = HCore.deserialize(document.getString("items").getValue(), Map.class);
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

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    public GameMode getGameMode() {
        return this.gameMode;
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
        player.setFlySpeed(this.flySpeed);
        player.setWalkSpeed(this.walkSpeed);
        player.setGameMode(this.gameMode);
        player.setFoodLevel(this.food);
        player.setTotalExperience(this.experience);
        player.teleport(this.location);
        player.addPotionEffects(this.potion);
        this.inventory.forEach((slot, item) -> player.getInventory().setItem(slot, item));
    }

    public void loadFrom(Player player) {
        this.health = player.getHealth();
        this.maxHealth = player.getHealthScale();
        this.walkSpeed = player.getWalkSpeed();
        this.flySpeed = player.getFlySpeed();
        this.gameMode = player.getGameMode();
        this.food = player.getFoodLevel();
        this.experience = player.getTotalExperience();
        this.location = player.getLocation();
        this.potion = player.getActivePotionEffects();
        this.inventory = new HashMap<>();

        for (int i = 0; i < player.getInventory().getSize() + 5; i++)
            this.inventory.put(i, player.getInventory().getItem(i));
    }


    /*
    DATABASE HANDLERS
     */
    @Override
    public BsonDocument toBsonDocument() {
        BsonDocument document = new BsonDocument();
        document.put("food", new BsonInt32(this.food));
        document.put("experience", new BsonInt32(this.experience));
        document.put("health", new BsonDouble(this.health));
        document.put("max_health", new BsonDouble(this.maxHealth));
        document.put("fly_speed", new BsonDouble(this.flySpeed));
        document.put("walk_speed", new BsonDouble(this.walkSpeed));
        document.put("game_mode", new BsonString(this.gameMode.name()));
        document.put("location", new BsonString(LocationUtils.serialize(this.location)));
        document.put("potions", new BsonString(HCore.serialize(this.potion)));
        document.put("items", new BsonString(HCore.serialize(this.inventory)));
        return document;
    }
}