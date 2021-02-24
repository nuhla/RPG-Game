package com.example.map;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mancj.materialsearchbar.MaterialSearchBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictions;
    private Location lastCurrentLocation;
    private LocationCallback locationCallback;
    private MaterialSearchBar materialSearchBar;
    private  View mapView;
    private Button btnDind;
    private  final  float DEFUALT_ZOOM=18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //------------------------------------ set Up Our Layout --------------------------------------------------------//
        setContentView(R.layout.activity_maps);
        materialSearchBar = findViewById(R.id.searchBar);
        btnDind = findViewById(R.id.btn_find);

        //--------------- Get the Map fragment and other Layout Elements in a variables to use later --------------------//
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();


        //----------------------- Prepare The GPS Service to get the Location and Places ---------------------------------//
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
        Places.initialize(MapsActivity.this, "AIzaSyBqYSJbo3IcmKLgeQBMsetpi_rsJ4JxxMM");
        placesClient= Places.createClient(this);
        final AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();


        //- --------------------------------- Searching the Api Event Listener---------------------------------------------//
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {


            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString(), true, null,true);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                if(buttonCode == MaterialSearchBar.BUTTON_NAVIGATION){

                }
                //--------------------------------------- opening or closing a navigation drawer-------------------------------//
                else if(buttonCode== MaterialSearchBar.BUTTON_BACK){
                    materialSearchBar.disableSearch();
                }

            }
        });

        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //------------------ and Event When the Text in the Search Bar changes ------------------------------------------//
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //---------------------------- We Creates a Request to the Places and AutoPrediction API ---------------------//
                FindAutocompletePredictionsRequest predictionsRequest = FindAutocompletePredictionsRequest.builder()
                        .setCountry("pk")
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .setSessionToken(token)
                        .setQuery(s.toString()).build();
                placesClient.findAutocompletePredictions(predictionsRequest).addOnCompleteListener(new OnCompleteListener<FindAutocompletePredictionsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<FindAutocompletePredictionsResponse> task) {

                        //------------------------- Checking the task result On Compeletion ------------------------------------//
                        if(task.isSuccessful()){
                            //----------------------------- We take the response from the task and also check it  --------------//
                            FindAutocompletePredictionsResponse  predictionsResponse= task.getResult();
                            //--------------------------- Checking if the response is Null or not -------------------------------//
                            if(predictionsResponse !=null){

                                predictions = predictionsResponse.getAutocompletePredictions();
                                List<String>  suseggtionList = new ArrayList<>();
                                //--------------------- Converting the predetion List into a Simple String List ---------------//
                                for(int i = 0 ; i< predictions.size() ;i++ ){
                                    AutocompletePrediction prediction = predictions.get(i);
                                    suseggtionList.add(prediction.getFullText(null).toString());

                                }

                                //------------------ Give the Search Bar the list of Suggestions ------------------------------//
                                materialSearchBar.updateLastSuggestions(suseggtionList);
                                //------------- if the list of Suggestions are not showing then we had to show it again ---------//
                                if(!materialSearchBar.isSuggestionsVisible()){
                                    materialSearchBar.showSuggestionsList();

                                }
                            }

                        }
                        //-------------------------------------- if the task Failed ----------------------------------------//
                        else{
                            Log.i("myTag", "Prediction fetching task unsuccessful"+task.getException().getMessage().toString());
                        }
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



    //------------------------------ When Ever the Map is Loaded and Ready this event will fire -------------------------//
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //------------------------------------ add Location Button to Map -----------------------------------------------//
        map = googleMap;
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        if(mapView!=null  &&  mapView.findViewById(Integer.parseInt("1")) !=null ){
            View LocationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) LocationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0,0,40,180);

        }

        //--------------------------- check if the GPS is Enabled and Request User to Enable it -------------------------//
        LocationRequest locationRequest =LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(MapsActivity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());


        //------------------------------ if the the gps is Enabled Successfully ----------------------------------------//
        task.addOnSuccessListener(MapsActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();

            }
        });

        //------------------------------ if the the gps did't Enabled Successfully ----------------------------------//
        task.addOnFailureListener(MapsActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {
                        resolvableApiException.startResolutionForResult(MapsActivity.this, 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();

                    }

                }
            }
        });
    }

    //------------------------------ On Complete the task and return the Result ----------------------------------//
    @Override
   protected void onActivityResult(int  RequestCode , int resultCode, Intent data){
        super.onActivityResult(RequestCode, resultCode, data);
        if(RequestCode ==51){
            if(resultCode== RESULT_OK){
                getDeviceLocation();
            }

        }
    }


    // ---------------------------------------- a function to get the Location ---------------------------------------//
    private  void getDeviceLocation(){

        fusedLocationProviderClient .getLastLocation()
        .addOnCompleteListener(new OnCompleteListener<Location>() {
              @Override
             public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful()){
                  lastCurrentLocation = task.getResult();
                      if(lastCurrentLocation!=null){
                          map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastCurrentLocation.getLatitude(), lastCurrentLocation.getLongitude()),DEFUALT_ZOOM));
                     }else {
                       final LocationRequest locationRequest = LocationRequest.create();
                       locationRequest.setInterval(10000);
                       locationRequest.setFastestInterval(5000);
                       locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                       locationCallback = new LocationCallback(){
                          @Override
                          public void onLocationResult(LocationResult locationResult){
                              super.onLocationResult(locationResult);
                              if(locationRequest ==null){
                             return;
                              }
                       lastCurrentLocation = locationResult.getLastLocation();
                       map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastCurrentLocation.getLatitude(), lastCurrentLocation.getLongitude()),DEFUALT_ZOOM));
                       fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                   }
               };

                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
            }
        }else {
            Toast.makeText(MapsActivity.this, "Unable to get Last Location", Toast.LENGTH_SHORT).show();
        }

    }
})  ;

    }
}
