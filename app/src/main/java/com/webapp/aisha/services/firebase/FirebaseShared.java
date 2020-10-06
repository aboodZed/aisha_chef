package com.webapp.aisha.services.firebase;

import com.webapp.aisha.services.firebase.fun.MessageData;
import com.webapp.aisha.services.firebase.fun.StorageData;
import com.webapp.aisha.services.firebase.fun.TokenData;

public class FirebaseShared {

    private static final FirebaseShared ourInstance = new FirebaseShared();

    public static FirebaseShared getInstance() {
        return ourInstance;
    }

    private MessageData messageData;
    private StorageData storageData;
    private TokenData tokenData;

    public FirebaseShared() {
        messageData = new MessageData();
        storageData = new StorageData();
        tokenData = new TokenData();
    }

    public MessageData getMessageData() {
        return messageData;
    }

    public StorageData getStorageData() {
        return storageData;
    }

    public TokenData getTokenData() {
        return tokenData;
    }
}
