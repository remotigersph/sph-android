package pogi.tiger.com.sph.utils;

import pogi.tiger.com.sph.model.User;

/**
 * Created by Pogi on 03/10/2016.
 */

public class FakeUserGenerator {

    public static User generateUser() {
        User user = new User();
        switch ((int )(Math.random() * 5 + 1)) {
            case 1:
                user.id=String.valueOf(1);
                user.username="petergriffin";
                user.photoUri="http://0.tqn.com/d/animatedtv/1/L/B/d/1/peter_2008_v2F_hires1.jpg";
                user.firstName="Peter";
                user.lastName="Griffin";
                return user;
            case 2:
                user.id=String.valueOf(2);
                user.username="chuck";
                user.photoUri="http://sublime99.com/wp-content/uploads/2015/02/chuck-norris-02-sublime99.jpg";
                user.firstName="Chuck";
                user.lastName="Norris";
                return user;
            case 3:
                user.id=String.valueOf(3);
                user.username="vlady";
                user.photoUri="http://cdn.images.express.co.uk/img/dynamic/78/590x/secondary/Putin-358895.jpg";
                user.firstName="Vladymir";
                user.lastName="Putin";
                return user;
            case 4:
                user.id=String.valueOf(4);
                user.username="queen";
                user.photoUri="http://i.dailymail.co.uk/i/pix/2014/03/25/article-2588813-1C8CE9F000000578-25_634x923.jpg";
                user.firstName="Emma";
                user.lastName="Watson";
                return user;
            default:
                user.id=String.valueOf(5);
                user.username="donald.trump";
                user.photoUri="http://ichef.bbci.co.uk/news/1024/cpsprodpb/1BC2/production/_88160170_trump-promo.jpg";
                user.firstName="Donald";
                user.lastName="Trump";
                return user;
        }
    }
}
