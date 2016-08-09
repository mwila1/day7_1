package com.example.jarda.day7_1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SupportErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

//public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback,
        LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

    private boolean setHasOptionsMenu = false;

    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};

    private int curMapTypeIndex = 0;
    private GoogleMap mGoogleMap;


    //For loadind to the file
    public static final String APP_STATE_PREFS = "APP_STATE";
    public static final String EDITTEXT_VALUE_PREF = "EDITTEXT_VALUE";
    private static final String STORAGE_FILE = "events";
    //private TextView mLatitudeText;
    //private TextView mLongitudeText;


    // private LocationRequest mLocationRequest;
    //private boolean mRequestingLocationUpdates = false;

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find the Fragment
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        // Fetch the map asynchronously
        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }
        mLatitudeText = (TextView) findViewById(R.id.latitudeText);
  }      mLongitudeText = (TextView) findViewById(R.id.longitudeText);
*/


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        // Add a marker
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(25, 50))
                .title("Marker"));
        // Instantiates a new CircleOptions object and defines the center and radius
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(48.4, -122.1)) // This is about Palo Alto
                .radius(10000); // In meters

        // Get back the mutable Circle
        Circle circle = googleMap.addCircle(circleOptions);

        initListeners();
        //initCamera(mCurrentLocation);
    }


    //}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private boolean setHasOptionsMenu() {

        // if (set = Boolean.parseBoolean(null))
        return false;
    }


    private void initListeners() {
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapLongClickListener(this);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setOnMapClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jarda.day7_1/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);
    }


    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jarda.day7_1/http/host/path")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    // Create our request object for locations
    // This is basically an object that specifies our location update needs.
    //protected void createLocationRequest() {

    //  mLocationRequest = new LocationRequest();

    //mLocationRequest.setInterval(1000);
    //mLocationRequest.setFastestInterval(500);
    //mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    //}

   /* @Override
    protected void onPause() {
        super.onPause();
        // Don't listen for updates when the Activity is not active
        stopLocationUpdates();
    }

    */

   /* private void stopLocationUpdates() {
        // Stop listening for location updates
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        // Set the value to false so we know if we have to start listening again
        mRequestingLocationUpdates = false;
    }
*/
/*
    private void startLocationUpdates() {
        // Handle some permission stuff, this is auto-generated.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // Start listening for updates
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        // Set the value to true, so we know that we are listening for updates
        mRequestingLocationUpdates = true;
    }
*/
   /* @Override
    protected void onResume() {
        super.onResume();
        // If we are connected to the Google API, but not listening for updates yet, we should begin listening
        // for updates.
        if(mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }
*/



   /* public void onLocationChanged(Location location) {

        mLastLocation = location;

        mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
        mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

        mLatitudeText = (TextView) findViewById(R.id.latitudeText);
        mLongitudeText = (TextView) findViewById(R.id.longitudeText);


    }
*/

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);

    }


    private void initCamera(Location location) {
        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(),
                        location.getLongitude()))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);

        mGoogleMap.setMapType(MAP_TYPES[curMapTypeIndex]);
        mGoogleMap.setTrafficEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled( true );
    }


    // When the user clicks with the finger for a short time (a tap)
    public void onMapClick(LatLng latLng) {

        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title( getAddressFromLatLng( latLng ) );

        options.icon( BitmapDescriptorFactory.defaultMarker() );
        mGoogleMap.addMarker( options );
    }



    // When the user clicks with the finger for a long time
    public void onMapLongClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position( latLng );
        options.title( getAddressFromLatLng( latLng ) );

        options.icon( BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource( getResources(),
                        R.mipmap.ic_launcher ) ) );

        mGoogleMap.addMarker( options );

     // Hi guys, this is where I started adding the loading method
        loadingOnMap();
    }

    // Load location to the textfile
    private void loadingOnMap() {



        // Open the file for writing
        FileOutputStream fos;



        try {
            fos = openFileOutput(STORAGE_FILE, Context.MODE_PRIVATE);
// Write the some bytes to the file

            EditText editText = (EditText) findViewById(R.id.editText);
            //Button editButton = (Button) findViewById(R.id.button);
            fos.write(editText.getText().toString().getBytes());
// Close the file
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Open the file for reading

        FileInputStream fis;
        try {
            fis = openFileInput(STORAGE_FILE);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finish();
    }


    private String getAddressFromLatLng( LatLng latLng ) {
        Geocoder geocoder = new Geocoder( this );


        String address = "";
        try {
            address = geocoder
                    .getFromLocation( latLng.latitude, latLng.longitude, 1 )
                    .get( 0 ).getAddressLine( 0 );
        } catch (IOException e ) {
        }

        return address;
    }





    private void drawCircle( LatLng location ) {
        CircleOptions options = new CircleOptions();
        options.center( location );
        //Radius in meters
        options.radius( 10 );
        options.fillColor( getResources()
                .getColor( R.color.colorAccent ) );
        options.strokeColor( getResources()
                .getColor( R.color.colorPrimary ) );
        options.strokeWidth( 10 );
        mGoogleMap.addCircle(options);
    }


    @Override
   public void onInfoWindowClick(Marker marker) {
        marker.showInfoWindow();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }


        @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}