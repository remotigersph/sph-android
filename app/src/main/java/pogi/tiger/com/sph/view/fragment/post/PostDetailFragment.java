package pogi.tiger.com.sph.view.fragment.post;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.databinding.FragmentPostDetailBinding;
import pogi.tiger.com.sph.model.Post;
import pogi.tiger.com.sph.viewmodel.fragment.post.PostDetailViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // TODO: Customize parameter argument names
    private static final String ARG_POST = "post";

    // TODO: Rename and change types of parameters
    private Post post;
    PostDetailViewModel viewModel;

    public PostDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param post The {@link Post} object to be displayed.
     * @return A new instance of fragment PostDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostDetailFragment newInstance(Post post) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_POST, post);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            post = (Post) getArguments().getSerializable(ARG_POST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPostDetailBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_post_detail, container, false);
        viewModel = new PostDetailViewModel(post, getContext());
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }
}
