package com.findbuddy.findbuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by abhidhar on 2/19/15.
 */
public class MapViewFragment extends com.google.android.gms.maps.SupportMapFragment {

    private GoogleMap mMap;

    public  MapViewFragment() {
        super();
    }

    public static MapViewFragment newInstance() {
        MapViewFragment mapViewFragment = new MapViewFragment();
        return  mapViewFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setUpMapIfNeeded();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        //setUpMapIfNeeded();

        return v;
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
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
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.

            mMap = getMap();
            // Check if we were successful in obtaining the map.
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
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    public void onZoomInClick(View view) {
        //CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(17.385044, 78.486671)).zoom(21).build();
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        CameraPosition currentPosition = mMap.getCameraPosition();
        Toast.makeText(getActivity(), String.valueOf(currentPosition.zoom), Toast.LENGTH_SHORT).show();
        CameraPosition newPosition = new CameraPosition(currentPosition.target,(float)(currentPosition.zoom + 1),currentPosition.tilt,currentPosition.bearing);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newPosition));
    }

    public void onZoomOutClick(View view) {
        //CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(17.385044, 78.486671)).zoom(21).build();
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        CameraPosition currentPosition = mMap.getCameraPosition();
        Toast.makeText(getActivity(),String.valueOf(currentPosition.zoom),Toast.LENGTH_SHORT).show();
        CameraPosition newPosition = new CameraPosition(currentPosition.target,(float)(currentPosition.zoom - 1),currentPosition.tilt,currentPosition.bearing);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newPosition));
    }

    public void onCoord1SetClick(View view) {
        //MarkerOptions markerOptions = new MarkerOptions();
        //markerOptions.
    }

    public void onCoord2SetClick(View view) {
    }
}
