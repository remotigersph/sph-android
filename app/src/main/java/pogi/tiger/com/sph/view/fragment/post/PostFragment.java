package pogi.tiger.com.sph.view.fragment.post;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.databinding.FragmentPostBinding;
import pogi.tiger.com.sph.databinding.FragmentPostItemBinding;
import pogi.tiger.com.sph.model.Post;
import pogi.tiger.com.sph.viewmodel.fragment.post.PostItemViewModel;
import pogi.tiger.com.sph.viewmodel.fragment.post.PostViewModel;

/**
 * A fragment representing a list of posts.
 * <p/>
 */
public class PostFragment extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         FragmentPostBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_post, container, false);

        binding.setViewModel(new PostViewModel(getChildFragmentManager(), binding.getRoot()));
        return binding.getRoot();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class PostPagerAdapter extends FragmentPagerAdapter {

        Fragment topPostFragment, freshPostFragment;

        private final int PAGER_TOTAL_COUNT = 2;
        private final int PAGER_INDEX_TOP   = 0;
        private final int PAGER_INDEX_FRESH = 1;

        public PostPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case PAGER_INDEX_TOP:
                    if(topPostFragment == null) {
                        topPostFragment = PostListFragment.newInstance(PostListFragment.MODE_TOP);
                    }
                    return topPostFragment;
                case PAGER_INDEX_FRESH:
                    if(freshPostFragment == null) {
                        freshPostFragment = PostListFragment.newInstance(PostListFragment.MODE_FRESH);
                    }
                    return freshPostFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGER_TOTAL_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case PAGER_INDEX_TOP:
                    return "Top";
                case PAGER_INDEX_FRESH:
                    return "Fresh";
            }
            return null;
        }
    }
}
