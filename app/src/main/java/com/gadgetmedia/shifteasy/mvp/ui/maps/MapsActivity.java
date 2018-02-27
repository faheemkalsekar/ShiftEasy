package com.gadgetmedia.shifteasy.mvp.ui.maps;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.gadgetmedia.shifteasy.mvp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String ARG_SHIFT_DETAILS = "shift_details";
    private GoogleMap mMap;
    private String mLatitudeLabel;
    private String mLongitudeLabel;
    private String mTitle;
    private String TAG = MapsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mLatitudeLabel = extras.getString("LATITUDE_ID");
            mLongitudeLabel = extras.getString("LONGITUDE_ID");
            mTitle = extras.getString("TITLE");

        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d(TAG, mLatitudeLabel);
        // Add a marker in Sydney and move the camera
        LatLng mLatLng = new LatLng(Double.parseDouble(mLatitudeLabel), Double.parseDouble(mLongitudeLabel));
        mMap.addMarker(new MarkerOptions().position(mLatLng).title(mTitle));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, mMap.getMaxZoomLevel()));
    }
}
