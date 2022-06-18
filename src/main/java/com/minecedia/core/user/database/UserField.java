package com.minecedia.core.user.database;

import com.minecedia.core.database.DatabaseField;
import com.minecedia.core.user.User;
import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.BsonString;
import org.bson.BsonValue;

public enum UserField implements DatabaseField<User> {

    UID("uid"),
    NAME("name"),
    COUNTRY("country"),
    LAST_PLAYED("last_played"),
    FIRST_PLAYED("first_played"),
    SKIN_DATA("skin_data"),
    BUKKIT("bukkit"),
    ;


    private final String field;

    UserField(String field) {
        this.field = field;
    }

    @Override
    public String getField() {
        return this.field;
    }

    @Override
    public BsonValue getValue(User user) {
        switch (this) {
            case UID:
                return new BsonBinary(user.getUID());
            case NAME:
                return new BsonString(user.getName());
            case COUNTRY:
                return new BsonString(user.getCountry().getCode());
            case LAST_PLAYED:
                return new BsonInt64(user.getLastPlayed());
            case FIRST_PLAYED:
                return new BsonInt64(user.getFirstPlayed());
            case SKIN_DATA:
                return user.getSkinData().toBsonDocument();
            case BUKKIT:
                return user.getBukkit().toBsonDocument();
            default:
                return new BsonDocument();
        }
    }
}