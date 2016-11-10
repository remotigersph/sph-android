package pogi.tiger.com.sph.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.component.SPHActivity;
import pogi.tiger.com.sph.databinding.ActivityMainBinding;
import pogi.tiger.com.sph.databinding.NavigationHeaderBinding;
import pogi.tiger.com.sph.utils.FakeUserGenerator;
import pogi.tiger.com.sph.utils.FirebaseUtils;
import pogi.tiger.com.sph.view.dialog.ActionChooserDialogFragment;
import pogi.tiger.com.sph.view.fragment.map.MapFragment;
import pogi.tiger.com.sph.view.fragment.post.PostFragment;
import pogi.tiger.com.sph.view.fragment.WallFragment;
import pogi.tiger.com.sph.viewmodel.NavigationDrawerViewModel;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(this), R.layout.activity_main, null, false);
        setContentView(binding.getRoot());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//        mDrawerLayout.addDrawerListener(mDrawerToggle);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);


        tabLayout = (TabLayout) findViewById(R.id.tabs);

        fab = (FloatingActionButton) findViewById(R.id.fab);


        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        NavigationHeaderBinding headerBinding = DataBindingUtil.bind(navigationView.getHeaderView(0));
        headerBinding.setViewModel(new NavigationDrawerViewModel(FakeUserGenerator.generateUser()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDrawerLayout.addDrawerListener(new ActionBarDrawerToggle(this, mDrawerLayout,toolbar ,  R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreatePostActionChooser();
            }
        });

        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // TODO: code this properly
                Toast.makeText(getApplicationContext(), item.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
                if (item.getTitle().equals(getString(R.string.logout))) {
                    FirebaseAuth.getInstance().signOut();
                }
                return true;
            }
        });
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

//        Fragment mapFragment, postFragment, notificationFragment, wallFragment;
    Fragment postFragment;

        private final int PAGER_TOTAL_COUNT        = 1;
//        private final int PAGER_INDEX_MAP          = 0;
        private final int PAGER_INDEX_POST         = 0;
//        private final int PAGER_INDEX_NOTIFICATION = 2;
        private final int PAGER_INDEX_WALL         = 2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
//                case PAGER_INDEX_MAP:
//                    if(mapFragment == null) {
//                        mapFragment = new MapFragment();
//                    }
//                    return mapFragment;
                case PAGER_INDEX_POST:
                    if(postFragment == null) {
                        postFragment = new PostFragment();
                    }
                    return postFragment;
//                case PAGER_INDEX_NOTIFICATION:
//                    if(notificationFragment == null) {
//                        notificationFragment = NotificationFragment.newInstance("one", "two");
//                    }
//                    return notificationFragment;
//                case PAGER_INDEX_WALL:
//                    if(wallFragment == null) {
//                        wallFragment = WallFragment.newInstance("one", "two");
//                    }
//                    return wallFragment;
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
//                case PAGER_INDEX_MAP:
//                    return "Map";
                case PAGER_INDEX_POST:
                    return "Post";
//                case PAGER_INDEX_NOTIFICATION:
//                    return "Notification";
//                case PAGER_INDEX_WALL:
//                    return "Wall";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem());
        if(fragment instanceof PostFragment &&
                fragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
            fragment.getChildFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    private void showCreatePostActionChooser() {
        FragmentManager fm = getSupportFragmentManager();
        ActionChooserDialogFragment actionChooserDialogFragment = new ActionChooserDialogFragment();
        actionChooserDialogFragment.show(fm, "fragment_action_chooser");
    }
}
