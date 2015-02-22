package com.findbuddy.findbuddy.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.findbuddy.findbuddy.R;
import com.findbuddy.findbuddy.models.UserList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseUser;

import java.util.HashMap;

/**
 * Created by abhidhar on 2/19/15.
 */
public class MapViewFragment extends com.google.android.gms.maps.SupportMapFragment {

    private OnFragmentInteractionListener listener;

    private static MapViewFragment mapViewFragment;
    private GoogleMap mMap;
    private HashMap<String ,ParseUser> markerUsers = new HashMap<>();
    private UserList<ParseUser> users = null;

    public  MapViewFragment() {
        super();
    }

    public static MapViewFragment newInstance() {
        if(mapViewFragment == null) {
            mapViewFragment = new MapViewFragment();
        }
        return  mapViewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        //setUpMapIfNeeded();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //this.users = (UserList) getArguments().getSerializable("users");
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
    public void setUpMapIfNeeded(UserList<ParseUser> users) {
        this.users = users;
        if (mMap == null) {
            mMap = getMap();
        }
        if (mMap != null) {
            setUpMap();
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(37.2, -121.4)).title("Marker"));
        mMap.setMyLocationEnabled(true);

        setUpCustomInfoWidget();
        //users = User.getDummyValues();

        Marker marker;
        for(int i = 0; i<users.size();i++)
        {
            // TODO remove this hack
            double lat = Double.parseDouble(users.get(i).get("lat").toString());
            double lon = Double.parseDouble(users.get(i).get("lon").toString());
            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lon));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin));
            Bitmap bitmap;
            marker = mMap.addMarker(markerOptions);
            markerUsers.put(marker.getId(),users.get(i));
        }
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                setUpLocationsService();
            }
        });
    }
    private void setUpLocationsService()
    {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        if (location != null)
        {
            Double minLat = 90.0, minLon = 180.0, maxLat = -90.0,maxLon = -180.0;

            for(int i =0; i< users.size();i++)
            {
                ParseUser user = users.get(i);
                double lat = Double.parseDouble(users.get(i).get("lat").toString());
                double lon = Double.parseDouble(users.get(i).get("lon").toString());
                if(minLat > lat)
                    minLat = lat;
                if(minLon > lon)
                    minLon = lon;
                if(maxLat < lat)
                    maxLat = lat;
                if(maxLon < lon)
                    maxLon = lon;
            }
            if(minLat > location.getLatitude())
                minLat = location.getLatitude();
            if(minLon > location.getLongitude())
                minLon = location.getLongitude();
            if(maxLat < location.getLatitude())
                maxLat = location.getLatitude();
            if(maxLon < location.getLongitude())
                maxLon = location.getLongitude();
            LatLngBounds bounds= new LatLngBounds( new LatLng(minLat,minLon),new LatLng(maxLat,maxLon));
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 5));

            // get my location
            Location myLocation = mMap.getMyLocation();

            // call the listener to send my location up to the activity
            listener.sendCurrentLocationToParse(location);
        }
    }
    private void setUpCustomInfoWidget()
    {
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoContents(Marker args) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoWindow(Marker args) {

                // Getting view from the layout file info_window_layout
                View v = getActivity().getLayoutInflater().inflate(R.layout.info_widget_layout, null);
                TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
                TextView tvUserId = (TextView)v.findViewById(R.id.tvUserId);
                TextView tvUpdateTime = (TextView)v.findViewById(R.id.tvUpdateTime);
                ParseUser user = markerUsers.get(args.getId());
                tvUserName.setText("Name: " + user.getUsername());
                tvUserId.setText("ID: " + user.getUsername());
                tvUpdateTime.setText("Last Updated:" + user.getUpdatedAt());

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    public void onInfoWindowClick(Marker marker) {

                        Toast.makeText(getActivity(), "Clicked on Info Widget", Toast.LENGTH_SHORT).show();
                    }
                });

                // Returning the view containing InfoWindow contents
                return v;

            }
        });
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }
}
