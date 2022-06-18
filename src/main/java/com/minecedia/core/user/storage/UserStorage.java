package com.minecedia.core.user.storage;

import com.minecedia.core.database.DatabaseObject;
import com.minecedia.core.user.User;
import org.bson.BsonBinary;
import org.bson.BsonBoolean;
import org.bson.BsonDocument;
import org.bson.BsonDouble;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.BsonString;
import org.bson.BsonValue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserStorage implements DatabaseObject {

    private final User user;
    private final Map<String, BsonValue> valueMap;

    public UserStorage(User user) {
        this.user = user;
        this.valueMap = new HashMap<>();
    }

    public UserStorage(User user, BsonDocument document) {
        this.user = user;
        this.valueMap = document;
    }

    public User getUser() {
        return this.user;
    }

    public Map<String, BsonValue> getContent() {
        return this.valueMap;
    }

    public Collection<BsonValue> getValues() {
        return this.valueMap.values();
    }

    public BsonValue getValue(String key) {
        return this.valueMap.get(key);
    }

    public void setValue(String key, BsonValue value) {
        this.valueMap.put(key, value);
    }

    public void setValue(String key, String value) {
        this.setValue(key, new BsonString(value));
    }

    public void setValue(String key, int value) {
        this.setValue(key, new BsonInt32(value));
    }

    public void setValue(String key, long value) {
        this.setValue(key, new BsonInt64(value));
    }

    public void setValue(String key, double value) {
        this.setValue(key, new BsonDouble(value));
    }

    public void setValue(String key, byte[] value) {
        this.setValue(key, new BsonBinary(value));
    }

    public void setValue(String key, UUID value) {
        this.setValue(key, new BsonBinary(value));
    }

    public void setValue(String key, boolean value) {
        this.setValue(key, new BsonBoolean(value));
    }

    public void removeValue(String key) {
        this.valueMap.remove(key);
    }


    /*
    DATABASE HANDLERS
     */
    @Override
    public BsonDocument toBsonDocument() {
        BsonDocument document = new BsonDocument();
        document.putAll(this.valueMap);
        return document;
    }
}