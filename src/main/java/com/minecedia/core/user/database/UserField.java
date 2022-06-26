package com.minecedia.core.user.database;

import com.minecedia.core.database.DatabaseField;
import com.minecedia.core.user.User;
import org.bson.*;
import org.jetbrains.annotations.NotNull;

public enum UserField implements DatabaseField<User> {

    UID("uid"),
    NAME("name"),
    COUNTRY("country"),
    LAST_PLAYED("last_played"),
    FIRST_PLAYED("first_played"),
    SKIN_DATA("skin_data"),
    STORAGE("storage"),
    ;


    private final @NotNull String field;

    UserField(@NotNull String field) {
        this.field = field;
    }

    @Override
    public @NotNull String field() {
        return this.field;
    }

    @Override
    public @NotNull BsonValue value(User user) {
        switch (this) {
            case UID:
                return new BsonBinary(user.uid());
            case NAME:
                return new BsonString(user.name());
            case COUNTRY:
                return new BsonString(user.country().code());
            case LAST_PLAYED:
                return new BsonInt64(user.lastPlayed());
            case FIRST_PLAYED:
                return new BsonInt64(user.firstPlayed());
            case SKIN_DATA:
                return user.skin().toBsonDocument();
            case STORAGE:
                return user.storage().toBsonDocument();
            default:
                return new BsonDocument();
        }
    }

}