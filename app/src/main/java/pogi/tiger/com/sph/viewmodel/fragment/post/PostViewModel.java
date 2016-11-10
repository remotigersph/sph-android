package pogi.tiger.com.sph.viewmodel.fragment.post;

import android.databinding.BaseObservable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.cleveroad.pulltorefresh.firework.FireworkyPullToRefreshLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.model.Post;
import pogi.tiger.com.sph.utils.FirebaseUtils;
import pogi.tiger.com.sph.view.fragment.post.PostFragment;

/**
 * Created by Pogi on 26/09/2016.
 */

public class PostViewModel extends BaseObservable implements FireworkyPullToRefreshLayout.OnRefreshListener {

    private ValueEventListener valueEventListener;
    private PostFragment.PostRecyclerViewAdapter adapter;
    private FireworkyPullToRefreshLayout swipeRefreshLayout;

    public PostViewModel(FragmentManager fragmentManager, View parent) {

        adapter = new PostFragment.PostRecyclerViewAdapter(fragmentManager);
        swipeRefreshLayout = (FireworkyPullToRefreshLayout) parent.findViewById(R.id.swipeRefreshLayout);
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Post> postList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    postList.add(post);
                }
                Collections.reverse(postList);
                adapter.resetValues(postList);
                adapter.notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        fetchAndLoadData();
    }

    private void fetchAndLoadData() {
        FirebaseUtils.generateTopPostQuery().addListenerForSingleValueEvent(valueEventListener);
    }

    public PostFragment.PostRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onRefresh() {
        fetchAndLoadData();
    }
}
