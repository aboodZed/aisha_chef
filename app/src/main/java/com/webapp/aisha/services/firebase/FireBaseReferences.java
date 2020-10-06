package com.webapp.aisha.services.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.webapp.aisha.utils.AppContent.Audio_REF;
import static com.webapp.aisha.utils.AppContent.IMAGE_REF;
import static com.webapp.aisha.utils.AppContent.MESSAGE_REF;

public class FireBaseReferences {

    private static FireBaseReferences firebaseReferences;
    private FirebaseDatabase db;

    public static FireBaseReferences getInstance() {
        if (firebaseReferences == null)
            return firebaseReferences = new FireBaseReferences();
        else return firebaseReferences;
    }

    private FireBaseReferences() {
        db = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getChatRef() {
        return db.getReference(MESSAGE_REF);
    }

    public StorageReference getImgRef() {
        return FirebaseStorage.getInstance().getReference().child(IMAGE_REF);
    }

    public StorageReference getMediaRef() {
        return FirebaseStorage.getInstance().getReference().child(Audio_REF);
    }
}
