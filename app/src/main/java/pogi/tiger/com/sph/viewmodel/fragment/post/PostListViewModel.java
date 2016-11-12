package pogi.tiger.com.sph.viewmodel.fragment.post;

import android.databinding.BaseObservable;
import android.support.v4.app.FragmentManager;
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
import pogi.tiger.com.sph.view.fragment.post.PostListFragment;

/**
 * Created by Pogi on 12/11/2016.
 */

public class PostListViewModel extends BaseObservable implements FireworkyPullToRefreshLayout.OnRefreshListener {

    private ValueEventListener valueEventListener;
    private PostListFragment.PostRecyclerViewAdapter adapter;
    private FireworkyPullToRefreshLayout swipeRefreshLayout;
    private byte mode;

    public PostListViewModel(FragmentManager fragmentManager, View parent, byte mode) {
        this.mode = mode;
        adapter = new PostListFragment.PostRecyclerViewAdapter(fragmentManager);
        swipeRefreshLayout = (FireworkyPullToRefreshLayout) parent.findViewById(R.id.swipeRefreshLayout);
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Post> postList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    post.key = postSnapshot.getKey();
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
        switch (mode) {
            case PostListFragment.MODE_TOP:
                FirebaseUtils.generateTopPostQuery().addListenerForSingleValueEvent(valueEventListener);
                break;
            case PostListFragment.MODE_FRESH:
                FirebaseUtils.generateFreshPostQuery().addListenerForSingleValueEvent(valueEventListener);
                break;
        }
    }

    public PostListFragment.PostRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onRefresh() {
        fetchAndLoadData();
    }
}

