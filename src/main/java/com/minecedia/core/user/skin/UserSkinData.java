package com.minecedia.core.user.skin;

import com.minecedia.core.database.DatabaseObject;
import com.minecedia.core.user.User;
import com.minecedia.core.user.utils.UserUtils;
import org.bson.BsonDocument;
import org.bson.BsonString;

public class UserSkinData implements DatabaseObject {

    private final User user;
    private final String texture;
    private final String signature;
    private final String shortTexture;

    public UserSkinData(User user) {
        String[] textureAndSignature = UserUtils.loadSkinData(user);

        this.user = user;
        this.texture = textureAndSignature[0];
        this.signature = textureAndSignature[1];
        this.shortTexture = textureAndSignature[2];
    }

    public UserSkinData(User user, BsonDocument document) {
        this.user = user;
        this.texture = document.getString("texture").getValue();
        this.signature = document.getString("signature").getValue();
        this.shortTexture = document.getString("shortTexture").getValue();
    }

    public User getUser() {
        return this.user;
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


    /*
    DATABASE HANDLERS
     */
    @Override
    public BsonDocument toBsonDocument() {
        BsonDocument document = new BsonDocument();
        document.put("texture", new BsonString(this.texture));
        document.put("signature", new BsonString(this.signature));
        document.put("shortTexture", new BsonString(this.shortTexture));
        return document;
    }
}