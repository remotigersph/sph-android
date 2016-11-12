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
import pogi.tiger.com.sph.databinding.FragmentPostItemBinding;
import pogi.tiger.com.sph.databinding.FragmentPostListBinding;
import pogi.tiger.com.sph.model.Post;
import pogi.tiger.com.sph.viewmodel.fragment.post.PostItemViewModel;
import pogi.tiger.com.sph.viewmodel.fragment.post.PostListViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MODE = "mode";

    public static final byte MODE_TOP   = 0x01;
    public static final byte MODE_FRESH = 0x10;

    private byte mMode;


    public PostListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mMode Defines the mode of the fragment, either MODE_TOP or MODE_FRESH.
     * @return A new instance of fragment PostListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostListFragment newInstance(byte mMode) {
        PostListFragment fragment = new PostListFragment();
        Bundle args = new Bundle();
        args.putByte(ARG_MODE, mMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMode = getArguments().getByte(ARG_MODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentPostListBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_post_list, container, false);

        binding.setViewModel(new PostListViewModel(getFragmentManager(), binding.getRoot(), mMode));
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
