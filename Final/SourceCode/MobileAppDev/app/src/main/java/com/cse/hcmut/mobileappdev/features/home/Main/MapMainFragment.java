package com.cse.hcmut.mobileappdev.features.home.Main;


import android.Manifest;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapMainFragment extends Fragment {

    private static final LatLng SYDNEY = new LatLng(-34, 151);
    private static final LatLng VIETNAM = new LatLng(21.0333, 105.85);

    public MapMainFragment() {
        // Required empty public constructor
    }

    private GoogleMap mMap;
    private SupportMapFragment mSupportMapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_map_main, container, false);
        if (rootView != null) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView_MapMainFragment);
            if (mSupportMapFragment == null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mSupportMapFragment = SupportMapFragment.newInstance();
                fragmentTransaction.replace(R.id.mapView_MapMainFragment, mSupportMapFragment).commit();
            } else {
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

                            BitmapDescriptor descriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                            MarkerOptions options = new MarkerOptions().position(SYDNEY).title("Sydney").snippet("Hi Dinh, Welcome to Sydney").icon(descriptor);
                            MarkerOptions optionsVN = new MarkerOptions().position(VIETNAM).title("Vietnam").snippet("Do you love Vietnam, Dinh?");
                            // Add a marker in Sydney and move the camera
                            mMap.addMarker(options);
                            mMap.addMarker(optionsVN);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(SYDNEY));

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
        }
        return rootView;
    }
}
