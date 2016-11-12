package pogi.tiger.com.sph.viewmodel.fragment.post;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import pogi.tiger.com.sph.BR;
import pogi.tiger.com.sph.SPHApplication;
import pogi.tiger.com.sph.model.Post;
import pogi.tiger.com.sph.model.User;
import pogi.tiger.com.sph.utils.FakeUserGenerator;
import pogi.tiger.com.sph.utils.FirebaseUtils;
import pogi.tiger.com.sph.utils.ImageLoader;
import pogi.tiger.com.sph.utils.SharedPreferenceUtils;

/**
 * Created by Pogi on 26/09/2016.
 */

public class PostDetailViewModel extends BaseObservable {

    private Post post;
    private Context context;

    DatabaseReference postReference;
    ValueEventListener valueEventListener;

    public PostDetailViewModel(Post post, Context context) {
        this.post = post;
        this.context = context;
    }

    public void setPost(Post post) {
        this.post = post;
        notifyChange();
        postReference = FirebaseUtils.createPostVotesReference(post);
        init();
    }

    public void init() {
        if(post != null && postReference == null) {
            postReference = FirebaseUtils.createPostVotesReference(post);
            User currentUser = SharedPreferenceUtils.getUser(context);
            if(currentUser != null) {
                post.updateIfVotedByUser(currentUser);
            }
        }
        if(valueEventListener == null) {
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    post.votes = dataSnapshot.getValue(Integer.class);
                    notifyPropertyChanged(BR.voteCount);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }
        postReference.addValueEventListener(valueEventListener);
    }


    public void destroy() {
        postReference.removeEventListener(valueEventListener);
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
            FirebaseUtils.upvotePost(post);
            notifyPropertyChanged(BR.votedByUser);
            notifyPropertyChanged(BR.voteCount);
        }
    }
}
