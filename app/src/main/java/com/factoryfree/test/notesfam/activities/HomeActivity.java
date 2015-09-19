package com.factoryfree.test.notesfam.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.factoryfree.test.notesfam.R;
import com.factoryfree.test.notesfam.fragments.FirstFragment;
import com.factoryfree.test.notesfam.fragments.SecondFragment;
import com.factoryfree.test.notesfam.fragments.ThirdFragment;
import com.parse.ParseAnalytics;

/**
 * Created by test on 30/08/2015.
 */

public class HomeActivity extends AppCompatActivity{

    /** AppCompatActivity - Adds an application activity class
     *  that can be used as a base class for activities that use the Support Library action bar implementation.
     *  This library includes support for material design user interface implementations.
     *  "ActionBarActivity ==> This class is deprecated.
     *  Use AppCompatActivity instead"
     */


    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mnvDrawer;
    private ActionBarDrawerToggle mdrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        setContentView(R.layout.activity_home);


        // Set a Toolbar to replace the ActionBar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Tie DrawerLayout events to the ActionBarToggle
        mdrawerToggle = setupDrawerToggle();
        mDrawer.setDrawerListener(mdrawerToggle);

        // Set the menu icon instead of the launcher icon.
        final ActionBar ab = getSupportActionBar();
        // To enable the app icon as an Up button, call setDisplayHomeAsUpEnabled()
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white);
        ab.setDisplayHomeAsUpEnabled(true);

        // Setup drawer view
        mnvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(mnvDrawer);

        // Create a new Fragment to be placed in the activity layout
        FirstFragment runfragment = new FirstFragment();

        /*// In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        runfragment.setArguments(getIntent().getExtras());*/

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction().add(R.id.flContent, runfragment)
                .commit();
    }


    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {

        android.support.v4.app.Fragment fragment = null;

        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = FirstFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = SecondFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = ThirdFragment.class;
                break;
            default:
                fragmentClass = FirstFragment.class;
        }

        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        android.support.v4.app.FragmentTransaction fragmenttransaction = getSupportFragmentManager().beginTransaction();
        fragmenttransaction
                .replace(R.id.flContent, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        if (mdrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mdrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mdrawerToggle.onConfigurationChanged(newConfig);
    }


}
