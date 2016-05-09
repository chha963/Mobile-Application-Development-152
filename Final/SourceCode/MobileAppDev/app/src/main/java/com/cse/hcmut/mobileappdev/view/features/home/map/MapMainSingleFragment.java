package com.cse.hcmut.mobileappdev.view.features.home.map;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cse.hcmut.mobileappdev.R;
import com.cse.hcmut.mobileappdev.base.views.fragment.MyBaseSingleFragment;
import com.cse.hcmut.mobileappdev.models.cameraposition.MyCameraPosition;
import com.cse.hcmut.mobileappdev.models.tinydb.TinyDB;
import com.cse.hcmut.mobileappdev.utils.DeviceUtils;
import com.cse.hcmut.mobileappdev.view.features.home.search.MapSearchViewSingleFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapMainSingleFragment extends MyBaseSingleFragment {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static final LatLng SYDNEY = new LatLng(-34, 151);
    private static final LatLng VIETNAM = new LatLng(21.0333, 105.85);

    public static final String KEY_LOCAL_MAP_STATE =
            "com.cse.hcmut.mobileappdev.view.features.home.Main.Map.MapMainFragment.map_state";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static MapMainSingleFragment newInstance() {

        Bundle args = new Bundle();

        MapMainSingleFragment fragment = new MapMainSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private CameraPosition mCurrentCameraPosition = null;

    private GoogleMap mMap;

    private SupportMapFragment mSupportMapFragment;

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public MapMainSingleFragment() {
        // Required empty public constructor
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map_main;
    }

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            bindWidgets();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWidgets(savedInstanceState);
    }

    private void bindWidgets() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mSupportMapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView_MapMainSingleFragment);
    }

    private void setupWidgets(Bundle savedInstanceState) {

        replaceMapSearchViewFragmentWithTransaction();

        if (mSupportMapFragment == null) {
            replaceSupportMapFragmentWithTransaction();
        } else {
            setupGoogleMap(savedInstanceState);
        }
    }

    private void replaceMapSearchViewFragmentWithTransaction() {

        Fragment searchFragment = new MapSearchViewSingleFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.searchView_MapMainFragment, searchFragment);

        transaction.commit();
    }

    private void replaceSupportMapFragmentWithTransaction() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        mSupportMapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.replace(R.id.mapView_MapMainSingleFragment, mSupportMapFragment);

        fragmentTransaction.commit();
    }

    private void setupGoogleMap(final Bundle savedInstanceState) {
        mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (googleMap != null) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    mMap = googleMap;
                    // change map type, default just grid
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    // disable indoor mode (mode u can see inside airport, shopping malls, ... not support VN)
                    mMap.setIndoorEnabled(false);

                    Toast.makeText(getContext(), "Map Ready", Toast.LENGTH_SHORT).show();
                    // enable find my location button
                    mMap.setMyLocationEnabled(true);

                    int paddingTop = (int) (DeviceUtils.getScreenHeight(getContext()) * 0.86);

                    mMap.setPadding(0, paddingTop, 0, 0);

                    BitmapDescriptor descriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                    MarkerOptions options = new MarkerOptions().position(SYDNEY).title("Sydney").snippet("Hi Dinh, Welcome to Sydney").icon(descriptor);
                    MarkerOptions optionsVN = new MarkerOptions().position(VIETNAM).title("Vietnam").snippet("Do you love Vietnam, Dinh?");
                    // Add a marker in Sydney and move the camera
                    mMap.addMarker(options);
                    mMap.addMarker(optionsVN);

                    // If first init, move to something
                    if (mCurrentCameraPosition == null) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(SYDNEY));
                    }
                    // Else restore from last opened
                    else {
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCurrentCameraPosition));
                    }

                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                        @Override
                        public View getInfoWindow(Marker marker) {
                            return null;
                        }

                        @Override
                        public View getInfoContents(Marker marker) {

                            View v = getLayoutInflater(savedInstanceState).inflate(R.layout.info_window, null);

                            LatLng latLng = marker.getPosition();
                            ImageView icon = (ImageView) v.findViewById(R.id.icon);
                            TextView title = (TextView) v.findViewById(R.id.title);
                            TextView snippet = (TextView) v.findViewById(R.id.snippet);
                            TextView lat = (TextView) v.findViewById(R.id.lat);
                            TextView lng = (TextView) v.findViewById(R.id.lng);

                            title.setText(marker.getTitle());
                            snippet.setText(marker.getSnippet());

                            if (marker.getTitle().equals("Sydney")) {
                                icon.setImageResource(R.drawable.sydney);
                            } else if (marker.getTitle().equals("Vietnam")) {
                                icon.setImageResource(R.drawable.vietnam);
                            }

                            lat.setText("Latitude: " + latLng.latitude);
                            lng.setText("Longitude: " + latLng.longitude);

                            return v;
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        saveStateToLocalDatabase(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreStateFromLocalDatabase(getActivity());
    }

    public void restoreStateFromLocalDatabase(Context context) {

        TinyDB tinydb = new TinyDB(context);

        MyCameraPosition lastCameraPosition =
                (MyCameraPosition) tinydb.getObject(KEY_LOCAL_MAP_STATE, MyCameraPosition.class);

        if (lastCameraPosition != null) {

            LatLng lastTarget
                    = new LatLng(lastCameraPosition.getLatitude(), lastCameraPosition.getLongitude());

            mCurrentCameraPosition = new CameraPosition.Builder()
                    .target(lastTarget)
                    .zoom(lastCameraPosition.getZoom())
                    .bearing(lastCameraPosition.getBearing())
                    .tilt(lastCameraPosition.getTilt())
                    .build();
        }

    }

    public void saveStateToLocalDatabase(Context context) {
        if (mMap != null) {
            TinyDB tinydb = new TinyDB(context);

            CameraPosition cameraPosition = mMap.getCameraPosition();
            LatLng currentTarget = cameraPosition.target;
            MyCameraPosition currentCameraPosition = new MyCameraPosition(
                    currentTarget.longitude,
                    currentTarget.latitude,
                    cameraPosition.zoom,
                    cameraPosition.bearing,
                    cameraPosition.tilt
            );

            tinydb.putObject(KEY_LOCAL_MAP_STATE, currentCameraPosition);

        }
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

}
