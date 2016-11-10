package pogi.tiger.com.sph.view.fragment.post;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
     * {@link RecyclerView.Adapter} that can display a {@link Post}
     */
    public static class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {

        private final List<Post> mValues;
        private final FragmentManager mFragmentManager;

        public PostRecyclerViewAdapter(FragmentManager fragmentManager) {
            mValues = new ArrayList<>();
            mFragmentManager = fragmentManager;
        }

        @Override
        public PostRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            FragmentPostItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.fragment_post_item, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(final PostRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.getBinding().setViewModel(new PostItemViewModel(mValues.get(position), mFragmentManager));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final FragmentPostItemBinding mBinding;

            public ViewHolder(FragmentPostItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public FragmentPostItemBinding getBinding() {
                return mBinding;
            }
        }

        public void resetValues(List<Post> newList) {
            mValues.clear();
            mValues.addAll(newList);
        }
    }
}
