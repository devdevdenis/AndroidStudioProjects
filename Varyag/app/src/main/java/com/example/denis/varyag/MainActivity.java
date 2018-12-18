package com.example.denis.varyag;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NewsFragment.OnFragmentInteractionListener, ObjectsFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener, ProviderFragment.OnFragmentInteractionListener {

    BottomNavigationView bottomNavigationView;
    android.support.v4.app.FragmentManager fragmentManager;

    GalleryFragment galleryFragment;
    NewsFragment newsFragment;
    ObjectsFragment objectsFragment;
    AccountFragment accountFragment;
    ProviderFragment providerFragment;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                        Fragment fragment;
                        switch (item.getItemId()) {
                            case R.id.action_events:
                                fragment = (NewsFragment) fragmentManager.findFragmentById(R.id.news_fragment);

                                if (fragment == null)
                                {
                                    FragmentTransaction fragmentTransaction = fragmentManager
                                            .beginTransaction();
                                    fragmentTransaction.replace(R.id.fragments_container, newsFragment);
                                    fragmentTransaction.commit();
                                    setTitle("События");
                                }
                                break;
                            case R.id.action_gallery:
                                fragment = (GalleryFragment) fragmentManager.findFragmentById(R.id.gallery_fragment);

                                if (fragment == null)
                                {
                                    FragmentTransaction fragmentTransaction = fragmentManager
                                            .beginTransaction();
                                    fragmentTransaction.replace(R.id.fragments_container, galleryFragment);
                                    fragmentTransaction.commit();
                                    setTitle("Галлерея");
                                }
                                break;
                            case R.id.action_objects:
                                fragment = (ObjectsFragment) fragmentManager.findFragmentById(R.id.objects_fragment);

                                if (fragment == null)
                                {
                                    FragmentTransaction fragmentTransaction = fragmentManager
                                            .beginTransaction();
                                    fragmentTransaction.replace(R.id.fragments_container, objectsFragment);
                                    fragmentTransaction.commit();
                                    setTitle("Объекты");
                                }
                                break;
                            case R.id.action_account:
                                fragment = (AccountFragment) fragmentManager.findFragmentById(R.id.account_fragment);

                                if (fragment == null)
                                {
                                    FragmentTransaction fragmentTransaction = fragmentManager
                                            .beginTransaction();
                                    fragmentTransaction.replace(R.id.fragments_container, accountFragment);
                                    fragmentTransaction.commit();
                                    setTitle("Аккаунт");
                                }
                                break;
                            case R.id.action_provider:
                                fragment = (ProviderFragment) fragmentManager.findFragmentById(R.id.provider_fragment);

                                if (fragment == null)
                                {
                                    FragmentTransaction fragmentTransaction = fragmentManager
                                            .beginTransaction();
                                    fragmentTransaction.replace(R.id.fragments_container, providerFragment);
                                    fragmentTransaction.commit();
                                    setTitle("Контент-провайдер");
                                }
                                break;
                            default: return false;
                        }
                        updateNavigationBarState(item.getItemId());
                        return true;
                    }
                });

        fragmentManager = getSupportFragmentManager();
        newsFragment = new NewsFragment();
        accountFragment = new AccountFragment();
        objectsFragment = new ObjectsFragment();
        galleryFragment = new GalleryFragment();
        providerFragment = new ProviderFragment();

        // При первом запуске
        if (savedInstanceState == null) {
            // получаем экземпляр FragmentTransaction
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();

            // добавляем фрагмент
            NewsFragment newsFragment = new NewsFragment();
            fragmentTransaction.add(R.id.fragments_container, newsFragment);
            fragmentTransaction.commit();
        }
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
}


