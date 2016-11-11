package pogi.tiger.com.sph.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import pogi.tiger.com.sph.model.Post;

/**
 * Created by Pogi on 01/11/2016.
 */

public class FirebaseUtils {

    private static final String FIREBASE_STORAGE_REFERENCE_URL  = "gs://fir-ph-6976c.appspot.com";
    private static final String FIREBASE_DATABASE_REFERENCE_URL = "https://fir-ph-6976c.firebaseio.com/";

    private static final int MAX_POST = 100;

    public static UploadTask createUploadTask(Uri uri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl(FIREBASE_STORAGE_REFERENCE_URL);

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        // Upload file and metadata to the path 'images/mountains.jpg'
        UploadTask uploadTask = storageRef.child("images/"+uri.getLastPathSegment()).putFile(uri, metadata);

        return uploadTask;
    }

    public static String getUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static void writeNewPost(String photoUri, String content) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(getUserId(), photoUri, content);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + getUserId() + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    public static Query generateTopPostQuery() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("posts")
            .orderByChild("votes")
            .limitToLast(MAX_POST);

        return query;
    }

    public static Query generateCategoriesQuery() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("categories").orderByKey();
        return query;
    }

    public static StorageReference createStorageReferenceFromUrl(String url) {
        StorageReference reference = null;
        try {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            reference = storage.getReferenceFromUrl(url);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
        return reference;
    }

    public static boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }
}
