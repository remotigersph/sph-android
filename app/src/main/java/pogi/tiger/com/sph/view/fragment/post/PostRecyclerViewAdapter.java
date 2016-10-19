package pogi.tiger.com.sph.view.fragment.post;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.databinding.FragmentPostListItemBinding;
import pogi.tiger.com.sph.model.Post;
import pogi.tiger.com.sph.view.fragment.post.PostFragment.OnListFragmentInteractionListener;
import pogi.tiger.com.sph.viewmodel.post.PostViewModel;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Post} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {

    private final List<Post> mValues;
    private final FragmentManager mFragmentManager;

    public PostRecyclerViewAdapter(List<Post> items, FragmentManager fragmentManager) {
        mValues = items;
        mFragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post_list_item, parent, false);
        return new ViewHolder(view);

//        FragmentPostListItemBinding binding = DataBindingUtil.inflate(
//                LayoutInflater.from(parent.getContext()), R.layout.fragment_post_list_item, parent, false);
//        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.getBinding().setViewModel(new PostViewModel(mValues.get(position), mFragmentManager));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final FragmentPostListItemBinding mBinding;
        public Post mItem;

        public ViewHolder(View view) {
            super(view);
            mBinding = DataBindingUtil.bind(view);
        }

        public FragmentPostListItemBinding getBinding() {
            return mBinding;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
