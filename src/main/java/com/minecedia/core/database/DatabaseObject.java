package com.minecedia.core.database;

import org.bson.BsonDocument;
import org.jetbrains.annotations.NotNull;

public interface DatabaseObject {

    @NotNull BsonDocument toBsonDocument();

}