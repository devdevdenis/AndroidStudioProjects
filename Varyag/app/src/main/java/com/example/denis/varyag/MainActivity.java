package com.example.denis.varyag;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NewsFragment.OnFragmentInteractionListener, GalleryFragment.OnFragmentInteractionListener, ObjectsFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener {

    BottomNavigationView bottomNavigationView;
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment NEWS_FRAGMENT;
    Fragment GALLERY_FRAGMENT;
    Fragment OBJECTS_FRAGMENT;
    Fragment ACCOUNT_FRAGMENT;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        Fragments
        NEWS_FRAGMENT = fragmentManager.findFragmentById(R.id.news_fragment);
        GALLERY_FRAGMENT = fragmentManager.findFragmentById(R.id.gallery_fragment);
        OBJECTS_FRAGMENT = fragmentManager.findFragmentById(R.id.objects_fragment);
        ACCOUNT_FRAGMENT = fragmentManager.findFragmentById(R.id.account_fragment);

        fragmentManager.beginTransaction()
                .hide(GALLERY_FRAGMENT)
                .hide(OBJECTS_FRAGMENT)
                .hide(ACCOUNT_FRAGMENT)
                .show(NEWS_FRAGMENT)
                .commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.action_events:
                                hideFragments();
                                fragmentManager.beginTransaction()
                                        .show(NEWS_FRAGMENT)
                                        .commit();
                                setTitle("События");
                                break;
                            case R.id.action_gallery:
                                hideFragments();
                                fragmentManager.beginTransaction()
                                        .show(GALLERY_FRAGMENT)
                                        .commit();
                                setTitle("Галлерея");
                                break;
                            case R.id.action_objects:
                                hideFragments();
                                fragmentManager.beginTransaction()
                                        .show(OBJECTS_FRAGMENT)
                                        .commit();
                                setTitle("Объекты");
                                break;
                            case R.id.action_account:
                                hideFragments();
                                fragmentManager.beginTransaction()
                                        .show(ACCOUNT_FRAGMENT)
                                        .commit();
                                setTitle("Аккаунт");
                                break;
                            default: return false;
                        }

                        updateNavigationBarState(item.getItemId());
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateNavigationBarState(int actionId){
        Menu menu = bottomNavigationView.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == actionId);
        }
    }

    private void hideFragments(){
        fragmentManager.beginTransaction()
                .hide(NEWS_FRAGMENT)
                .hide(ACCOUNT_FRAGMENT)
                .hide(GALLERY_FRAGMENT)
                .hide(OBJECTS_FRAGMENT)
                .commit();
    }
}


