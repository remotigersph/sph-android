package pogi.tiger.com.sph.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import pogi.tiger.com.sph.model.User;

/**
 * Created by Pogi on 04/10/2016.
 */

public class NavigationDrawerViewModel extends BaseObservable {

    private User user;

    public NavigationDrawerViewModel(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @BindingAdapter({"imageUri"})
    public static void loadImage(ImageView view, String imageUri) {
        Glide.with(view.getContext())
                .load(imageUri)
                .fitCenter()
                .crossFade()
                .into(view);
    }
}
