package com.example.test2;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.app.ActivityCompat.requestPermissions;
//import static androidx.core.app.AppOpsManagerCompat.Api29Impl.getSystemService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

public class gps1 {
    private FusedLocationProviderClient mFusedLocationClient;


    static Context mContext;
    static Activity mActivity;
    int mNumber;

    public gps1(Context mContext) {
        this.mContext = mContext;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.mContext);

    }

    public gps1() {
        this.mContext = mContext;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.mContext);
    }

    private Boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return LocationManagerCompat.isLocationEnabled(locationManager);
    }

    // @SuppressLint("MissingPermission")
    public List getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return null;
                }


                        Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
                        double lat = 1;
                        double lng=2;
                        try {
                            List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String tvLatitude = "Latitude\n${list[0].latitude}";
                    }


        }

        else{
            int a=5;
            //requestPermissions();
        }

        return null;
    }




}


/*
    @SuppressLint("MissingPermission")
    public List getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnSuccessListener((Executor) this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {

                                   // Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
                                    Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
                                    double lat = 1;
                                    double lng=2;
                                    try {
                                        List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                        String tvLatitude = "Latitude\n${list[0].latitude}";
                                        //var tvLongitude = "Longitude\n${list[0].longitude}"
                                        //var tvCountryName = "Country Name\n${list[0].countryName}"
                                       // var tvLocality = "Locality\n${list[0].locality}"
                                       // var tvAddress = "Address\n${list[0].getAddressLine(0)}"

                            }
                                else {
                                    //Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                                    //Intent intent = (Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                    //startActivity(intent)

                        };







            }

        }
        }
        return List;
    }
           // else {
           //     requestPermissions()



    }


*/




