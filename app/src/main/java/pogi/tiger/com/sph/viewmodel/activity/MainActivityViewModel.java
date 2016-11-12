package pogi.tiger.com.sph.viewmodel.activity;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import pogi.tiger.com.sph.R;
import pogi.tiger.com.sph.databinding.NavigationHeaderBinding;
import pogi.tiger.com.sph.model.Category;
import pogi.tiger.com.sph.utils.FakeUserGenerator;
import pogi.tiger.com.sph.utils.FirebaseUtils;
import pogi.tiger.com.sph.view.activity.MainActivity;
import pogi.tiger.com.sph.view.dialog.ActionChooserDialogFragment;
import pogi.tiger.com.sph.viewmodel.NavigationDrawerViewModel;

/**
 * Created by Pogi on 11/11/2016.
 */

public class MainActivityViewModel extends BaseObservable implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    public MainActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    public ViewPager mViewPager;
    NavigationView navigationView;
    TabLayout tabLayout;
    AppCompatActivity mActivity;
    ActionBarDrawerToggle actionBarDrawerToggle;

    public MainActivityViewModel(AppCompatActivity activity, View parent) {
        this.fragmentManager = activity.getSupportFragmentManager();
        mActivity = activity;
        mSectionsPagerAdapter = new MainActivity.SectionsPagerAdapter(fragmentManager);
        toolbar = (Toolbar) parent.findViewById(R.id.toolbar);
        mActivity.setSupportActionBar(toolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActivity.getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) parent.findViewById(R.id.drawer_layout);
        mViewPager = (ViewPager) parent.findViewById(R.id.container);
        tabLayout = (TabLayout) parent.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        actionBarDrawerToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout,toolbar ,  R.string.drawer_open, R.string.drawer_close) {

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
        };
        navigationView = (NavigationView) parent.findViewById(R.id.navigation_view);
        NavigationHeaderBinding headerBinding = DataBindingUtil.bind(navigationView.getHeaderView(0));
        headerBinding.setViewModel(new NavigationDrawerViewModel(FakeUserGenerator.generateUser()));
        init();
    }

    public void init() {
        FirebaseUtils.generateCategoriesQuery().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Menu menu = navigationView.getMenu();
                for (DataSnapshot categorySnapshot: dataSnapshot.getChildren()) {
                    Category category = categorySnapshot.getValue(Category.class);
                    MenuItem menuItem = menu.add(R.id.drawer_categories, Integer.valueOf(categorySnapshot.getKey()),Menu.NONE, category.name);
                    menuItem.setCheckable(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public MainActivity.SectionsPagerAdapter getAdapter() {
        return mSectionsPagerAdapter;
    }

    public void showCreatePostActionChooser(View view) {
        ActionChooserDialogFragment actionChooserDialogFragment = new ActionChooserDialogFragment();
        actionChooserDialogFragment.show(fragmentManager, "fragment_action_chooser");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // TODO: code this properly
        Toast.makeText(mActivity, item.getTitle() + " selected", Toast.LENGTH_SHORT).show();
        mDrawerLayout.closeDrawers();
        if (item.getTitle().equals(mActivity.getString(R.string.logout))) {
            FirebaseAuth.getInstance().signOut();
        }
        return true;
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return actionBarDrawerToggle;
    }
}
