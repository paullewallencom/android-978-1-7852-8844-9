package com.example.kyle.mapactivity;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMapClickListener {

    private static final String DEBUG_TAG = "tag";
    private TextView textView;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.kyle.mapactivity.R.layout.activity_maps);

        textView = (TextView) findViewById(com.example.kyle.mapactivity.R.id.text_view);

        setUpMapIfNeeded();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(30000)
                .setFastestInterval(5000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        googleApiClient.connect();
        Log.d(DEBUG_TAG, "Reconnected");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
            Log.d(DEBUG_TAG, "Disconnected");
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(com.example.kyle.mapactivity.R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.178844, -1.826189))
                .title("Stonehenge")
                .snippet("3000 BC")
                .icon(BitmapDescriptorFactory.fromResource(com.example.kyle.mapactivity.R.mipmap.ic_launcher)));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);

        mMap.setOnMapClickListener(this);
    }

    public void onMapClick(LatLng latLng) {
        textView.setText("Clicked, position = " + latLng);
        CameraPosition position = new CameraPosition.Builder().target(
        latLng).zoom(6).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location loc = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        if (loc != null) {
            textView.setText(loc.toString());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        textView.setText("connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        textView.setText("connection failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        textView.setText(location.toString());
    }
}
