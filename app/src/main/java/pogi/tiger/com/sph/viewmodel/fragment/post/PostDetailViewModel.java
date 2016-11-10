package pogi.tiger.com.sph.viewmodel.fragment.post;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import pogi.tiger.com.sph.BR;
import pogi.tiger.com.sph.model.Post;
import pogi.tiger.com.sph.utils.FakeUserGenerator;
import pogi.tiger.com.sph.utils.ImageLoader;

/**
 * Created by Pogi on 26/09/2016.
 */

public class PostDetailViewModel extends BaseObservable {

    private Post post;
    private Context context;

    public PostDetailViewModel(Post post, Context context) {
        this.post = post;
        this.context = context;
    }

    public void setPost(Post post) {
        this.post = post;
        notifyChange();
    }

    public String getPhotoUri() {
        return post.photoUri;
    }

    public String getUserPhotoUri() {
        return FakeUserGenerator.generateUser().photoUri;
    }

    public String getContent() {
        return post.content;
    }

    @Bindable
    public String getVoteCount() {
        return post.votes==0 ? "Be the first" : String.valueOf(post.votes);
    }

    @Bindable
    public boolean isVotedByUser() {
        return post.isVotedByUser;
    }

    public String getUsername() {
        return post.author;
    }

    @BindingAdapter({"imageUri"})
    public static void loadImage(final ImageView view, String imageUri) {
        ImageLoader.load(view, imageUri);
    }

    public void vote(View view) {
        if(!post.isVotedByUser) {
            post.isVotedByUser = true;
            post.votes++;
            notifyPropertyChanged(BR.votedByUser);
            notifyPropertyChanged(BR.voteCount);
        }
    }
}
