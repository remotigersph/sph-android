package pogi.tiger.com.sph.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import pogi.tiger.com.sph.utils.FakeUserGenerator;

/**
 * Created by Pogi on 03/10/2016.
 */

public class User implements Serializable {

    public String id;
    public String username;
    public String photoUri;

    public String firstName;
    public String lastName;
    public Map<String, Boolean> votes;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("photoUri", photoUri);
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("votes", votes);

        return result;
    }

    @Override
    public String toString() {
        return username;
    }
}
