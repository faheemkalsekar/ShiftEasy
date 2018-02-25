package com.gadgetmedia.shifteasy.mvp.ui.shifts;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gadgetmedia.shifteasy.mvp.R;
import com.gadgetmedia.shifteasy.mvp.data.Business;
import com.gadgetmedia.shifteasy.mvp.data.Shift;
import com.gadgetmedia.shifteasy.mvp.util.ActivityUtils;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

import static com.gadgetmedia.shifteasy.mvp.ui.shifts.ShiftsFragment.OnBusinessChangedListener;
import static com.gadgetmedia.shifteasy.mvp.ui.shifts.ShiftsFragment.OnListFragmentInteractionListener;

public class ShiftsActivity extends DaggerAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnBusinessChangedListener, OnListFragmentInteractionListener {

    @Inject
    Lazy<ShiftsFragment> shiftsFragmentProvider;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ShiftsFragment shiftsFragment =
                (ShiftsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (shiftsFragment == null) {
            // Get the fragment from dagger
            shiftsFragment = shiftsFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), shiftsFragment, R.id.contentFrame);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.start_shift) {

        } else if (id == R.id.end_shift) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onShowBusinessInfo(final Business business) {
        final NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView txtView = header.findViewById(R.id.business_name);
        ImageView imageView = header.findViewById(R.id.business_logo);
        txtView.setText(business.getName());
        Picasso.with(this).load(business.getLogo()).resize(160,0).into(imageView);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onListFragmentInteraction(final Shift shift) {

    }
}
