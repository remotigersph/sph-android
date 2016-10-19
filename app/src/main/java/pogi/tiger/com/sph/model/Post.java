package pogi.tiger.com.sph.model;

import java.io.Serializable;

/**
 * Created by Pogi on 26/09/2016.
 */

public class Post implements Serializable {

    public final String id;
    public String photoUri;
    public String content;
    public long votes;
    public boolean isVotedByUser;

    public User user;

    public Post(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
