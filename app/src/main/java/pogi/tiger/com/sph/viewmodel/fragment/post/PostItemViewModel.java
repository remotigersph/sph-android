package pogi.tiger.com.sph.viewmodel.fragment.post;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.model.Post;
import pogi.tiger.com.sph.utils.ImageLoader;
import pogi.tiger.com.sph.view.fragment.post.PostDetailFragment;

/**
 * Created by Pogi on 04/11/2016.
 */

public class PostItemViewModel extends BaseObservable implements View.OnClickListener {

    //    Context context;
    private final FragmentManager mFragmentManager;
    private Post post;

    public PostItemViewModel(Post post, FragmentManager fragmentManager) {
//        this.context = context;
        this.post = post;
        this.mFragmentManager = fragmentManager;
    }

    @Bindable
    public void setPost(Post post) {
        this.post = post;
    }

    @Bindable
    public String getId() {
        return post.key;
    }

    @Bindable
    public String getContent() {
        return post.content;
    }

    @Bindable
    public String getPhotoUri() {
        return post.photoUri;
    }

    @BindingAdapter({"android:src"})
    public static void loadImage(ImageView view, String imageUri) {

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int calculatedWidth  = metrics.widthPixels/3;
        ImageLoader.load(view, imageUri, calculatedWidth,calculatedWidth);
    }

    @Override
    public void onClick(View v) {
        PostDetailFragment fragment = PostDetailFragment.newInstance(post);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.post_container, fragment)
                .addToBackStack("item")
                .commit();
    }
}

