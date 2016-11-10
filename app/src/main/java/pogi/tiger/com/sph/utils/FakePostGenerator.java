package pogi.tiger.com.sph.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pogi.tiger.com.sph.model.Post;
import pogi.tiger.com.sph.model.User;

/**
 * Created by Pogi on 02/10/2016.
 */

public class FakePostGenerator {

    public static List<Post> generatePostList(int size) {
        List<Post> postList = new ArrayList<>();
        for(int i = 0 ; i < size ; i++) {
            postList.add(generatePost(i));
        }
        return postList;
    }

    public static Post generatePost(int id) {
        User generatedUser = FakeUserGenerator.generateUser();
        Post post = new Post(String.valueOf(id), generatePhotoUri(), "this is post #" + id);
        post.key = String.valueOf(id);
        post.author = generatedUser.toString();
        post.votes =(long)(Math.random() * 100 + 1);
        return post;
    }

    private static String generatePhotoUri() {
        switch ((int )(Math.random() * 5 + 1)) {
            case 1:
                return "http://www.wired.com/images_blogs/gadgetlab/2011/12/new-prof.png";
            case 2:
                return "http://static.trustedreviews.com/94/000039bf9/f93f_orh370w630/n.jpg";
            case 3:
                return "http://previewcf.turbosquid.com/Preview/2014/12/21__10_13_26/1.jpg752be1a3-b303-4be2-98f8-6eff70149336Original.jpg";
            case 4:
                return "https://news.androidlist-russia.com/wp-content/uploads/sites/9/sites/9/2016/06/KitKat_HD-AndroidHD-Android-trampt-121601o.jpg";
            default:
                return "http://www.androidcentral.com/sites/androidcentral.com/files/styles/large/public/postimages/108579/12_-_1.jpg";
        }
    }
}
