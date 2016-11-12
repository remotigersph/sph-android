package pogi.tiger.com.sph.viewmodel.fragment.post;

import android.databinding.BaseObservable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.view.fragment.post.PostFragment;

/**
 * Created by Pogi on 26/09/2016.
 */

public class PostViewModel extends BaseObservable {

    public PostFragment.PostPagerAdapter postPagerAdapter;

    TabLayout tabLayout;
    ViewPager mViewPager;

    public PostViewModel(FragmentManager fragmentManager, View parent) {
        postPagerAdapter = new PostFragment.PostPagerAdapter(fragmentManager);

        mViewPager = (ViewPager) parent.findViewById(R.id.post_pager);
        tabLayout = (TabLayout) parent.findViewById(R.id.post_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public PostFragment.PostPagerAdapter getAdapter() {
        return postPagerAdapter;
    }
}
