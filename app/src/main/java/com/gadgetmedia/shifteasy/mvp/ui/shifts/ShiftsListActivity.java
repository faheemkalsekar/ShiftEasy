package com.gadgetmedia.shifteasy.mvp.ui.shifts;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gadgetmedia.shifteasy.mvp.R;
import com.gadgetmedia.shifteasy.mvp.api.ShiftRequestData;
import com.gadgetmedia.shifteasy.mvp.data.Business;
import com.gadgetmedia.shifteasy.mvp.data.Shift;
import com.gadgetmedia.shifteasy.mvp.util.EmptyStateRecyclerView;
import com.gadgetmedia.shifteasy.mvp.util.ScrollChildSwipeRefreshLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ShiftsListActivity extends DaggerAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ShiftsContract.View, EasyPermissions.PermissionCallbacks {

    private static final String[] LOCATION =
            {Manifest.permission.ACCESS_COARSE_LOCATION};

    private static final int RC_LOCATION_PERM = 911;
    private static final String TAG = ShiftsListActivity.class.getSimpleName();
    final AtomicBoolean isTaskStarted = new AtomicBoolean(false);
    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    @Inject
    Lazy<ShiftsContract.Presenter> mPresenterProvider;
    ShiftsContract.Presenter mPresenter;
    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ShiftRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Object presenter = getLastCustomNonConfigurationInstance();
        if (presenter != null) {
            mPresenter = (ShiftsContract.Presenter) presenter;
        } else {
            mPresenter = mPresenterProvider.get();
        }
        setContentView(R.layout.activity_shifts_list);
        Toolbar toolbar = findViewById(R.id.listtoolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        final EmptyStateRecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setEmptyView(findViewById(R.id.noShifts));
        recyclerView.setHasFixedSize(true);
        mAdapter = new ShiftRecyclerViewAdapter(this,
                Picasso.with(this),
                new ArrayList<Shift>(),
                mTwoPane);
        recyclerView.setAdapter(mAdapter);

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadShifts(true);
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.start_shift) {
            isTaskStarted.set(true);
            locationTask();

        } else if (id == R.id.end_shift) {
            isTaskStarted.set(false);
            locationTask();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startShift(final String lat, final String lon) {

        final ShiftRequestData shiftRequestData = new ShiftRequestData(ISO8601Utils.format(new Date()), lat, lon);

        mPresenter.startShift(shiftRequestData, true);
    }

    private void endShift(final String lat, final String lon) {

        final ShiftRequestData shiftRequestData = new ShiftRequestData(ISO8601Utils.format(new Date()), lat, lon);

        mPresenter.endShift(shiftRequestData, true);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (isFinishing()) {
            return;
        }

        final SwipeRefreshLayout srl = findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showShifts(final List<Shift> shiftList) {
        mAdapter.replaceData(shiftList);
    }

    @Override
    public boolean isActive() {
        return !isFinishing();
    }

    @Override
    public void showBusinessInfo(List<Business> businessList) {
        final NavigationView navigationView = findViewById(R.id.nav_view);
        final View header = navigationView.getHeaderView(0);
        final TextView txtView = header.findViewById(R.id.business_name);
        final ImageView imageView = header.findViewById(R.id.business_logo);
        txtView.setText(businessList.get(0).getName());

        Picasso.with(this).load(businessList.get(0).getLogo()).
                resize(160, 0).
                into(imageView);
    }

    @Override
    public void showNoBusinessInfo() {
//        showMessage("Business Info Not Available");
    }

    @Override
    public void showServerMessage(final String message) {
        showMessage(message);
    }

    @Override
    public void showNoShift() {
        showMessage("No Shifts");
    }

    @Override
    public void reloadShifts() {
        mPresenter.loadShifts(true);

    }

    private void showMessage(final String message) {
        if (isFinishing()) {
            return;
        }
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.takeView(this);
    }

    @Override
    protected void onStop() {
        //prevent leaking activity in case presenter is orchestrating a long running task
        super.onStop();
        mPresenter.dropView();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    public void locationTask() {
        if (hasLocationPermissions()) {
            // Have permissions, do the thing!
            getLastLocation();
        } else {
            // Ask for permissions
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location),
                    RC_LOCATION_PERM,
                    LOCATION);
        }
    }

    private boolean hasLocationPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION);
    }

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {

        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Log.d(TAG, "onComplete");
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            // In some rare cases the location returned can be null
                            if (mLastLocation == null) {
                                return;
                            }

                            final String latitude = String.valueOf(mLastLocation.getLatitude());
                            final String longitude = String.valueOf(mLastLocation.getLongitude());

                            if (isTaskStarted.get()) {
                                startShift(latitude, longitude);
                            } else {
                                endShift(latitude, longitude);
                            }

                            showMessage("Lat: " + latitude + " Lon: " + longitude);

                        } else {

                            showMessage("No Location detected");

                            if (isTaskStarted.get()) {
                                startShift(null, null);
                            } else {
                                endShift(null, null);
                            }
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    this,
                    getString(R.string.returned_from_app_settings_to_activity, hasLocationPermissions() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }


}
