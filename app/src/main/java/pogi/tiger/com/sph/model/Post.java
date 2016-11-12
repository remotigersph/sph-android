package pogi.tiger.com.sph.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pogi on 26/09/2016.
 */

@IgnoreExtraProperties
public class Post implements Serializable {

    public String key;
    public String userId;
    public String author;
    public String photoUri;
    public String content;
    public double latitude, longitude;
    public long createdAt;
    public long votes;
    public boolean isVotedByUser;

    public String category;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String userId, String photoUri, String content) {
        this.userId = userId;
        this.photoUri = photoUri;
        this.content = content;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("author", author);
        result.put("photoUri", photoUri);
        result.put("content", content);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("createdAt", ServerValue.TIMESTAMP);
        result.put("votes", votes);
        result.put("isVotedByUser", isVotedByUser);
        result.put("category", category);

        return result;
    }
}
