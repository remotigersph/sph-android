package pogi.tiger.com.sph.model;

import java.io.Serializable;

/**
 * Created by Pogi on 03/10/2016.
 */

public class User implements Serializable {

    public String id;
    public String username;
    public String photoUri;

    public String firstName;
    public String lastName;

    @Override
    public String toString() {
        return username;
    }
}
