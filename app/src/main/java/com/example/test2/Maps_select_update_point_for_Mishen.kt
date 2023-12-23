package com.example.test2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.test2.databinding.MapsSelectUpdatePointForMishenBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import java.util.*


class Maps_select_update_point_for_Mishen : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: MapsSelectUpdatePointForMishenBinding
    private lateinit var currentLocation: Location
    var mlat=""
    var mlong=""
    var mlatLng = LatLng(0.0,0.0)
    var mDescripshen=""
    var mPlaceMishen=""
    var mlistMishenInGameAndDescription=JSONArray()
    var mNewMishen=true
    var mInstractionToMishen=""
    var mDontShowMishenPoint = "false"
    var mclue="false"

    private lateinit var NewLocation: LatLng
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    var correntMarker:Marker?=null
    var mFlegSetMishenLocationInMap =true
    //var arrayGames = JSONArray()


    private var permissionDenied = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //var jsonStringGame= intent.getStringExtra("jsonStringGame")
        mFlegSetMishenLocationInMap=true
        binding = MapsSelectUpdatePointForMishenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@Maps_select_update_point_for_Mishen)
        fetchLocation()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        mPlaceMishen=intent.getStringExtra("TotalNumberOfMishen").toString()

        mlat = intent.getStringExtra("lat").toString()
        mlong = intent.getStringExtra("long").toString()
        mNewMishen=intent.getBooleanExtra("NewMishen",true)
        mlatLng = LatLng(mlat.toDouble(),mlong.toDouble())

        if(!intent.getStringExtra("listMishenInGameAndDescription").toString().equals(""))
            mlistMishenInGameAndDescription= JSONArray(intent.getStringExtra("listMishenInGameAndDescription").toString())
        if(!mNewMishen.equals("true") )
            {
                mclue=intent.getStringExtra("clue")!!
                if (!mclue.equals("")){

                    findViewById<EditText?>(R.id.editTextclue).setText(mclue.toString())
                }
                mInstractionToMishen=intent.getStringExtra("InstractionToMishen")!!
                if (!mInstractionToMishen.equals("")) {
                    findViewById<EditText?>(R.id.editTextInstraction).setText(mInstractionToMishen.toString())

                }
                mDontShowMishenPoint=  intent.getStringExtra("DontShowMishenPoint")!!
                if (mDontShowMishenPoint.equals("false")) {
                    findViewById<CheckBox?>(R.id.checkBox).isChecked=true
                }
            }
        //if (jsonStringGame!=null) {
        //    arrayGames = JSONArray(jsonStringGame)


       // }


    }
    fun buttonGetGPSLongLat(view: View) {
        //val output = Intent()

        //output.putExtra("latlong", currentLocation.latitude.toString())
        //output.putExtra("long", currentLocation.longitude.toString())
        //setResult(RESULT_OK, output)
        //finish()
    returnResult()


    }

///////////////////////////////////////////////////////////////////////////////////////////
fun CheckBox(view: View) {
    // Is the button now checked?
    val checked = view.isClickable
    if (checked) {
        mDontShowMishenPoint = "true"
        // Pirates are the best
    }
    else
    {
        mDontShowMishenPoint ="false"
    }
}
///////////////////////////////////////////////////////////////////////////////////////////


    // call this to return result and close activity
    fun returnResult() {

        val data = Intent().apply {
            val instraction: EditText = findViewById(R.id.editTextInstraction)as EditText
            val clue: EditText = findViewById(R.id.editTextclue)as EditText
            putExtra("lat",mlatLng.latitude.toString())
            putExtra("long", mlatLng.longitude.toString())
            putExtra("FlegSetMishenLocationInMap",mFlegSetMishenLocationInMap)
            putExtra("DontShowMishenPoint",mDontShowMishenPoint)
            putExtra("instraction",instraction.text.toString())
            putExtra("clue",clue.text.toString())

        }
        setResult(RESULT_OK, data)
        finish()
    }


    @Override
    public override fun onBackPressed() {
        // When the user hits the back button set the resultCode
        // as Activity.RESULT_CANCELED to indicate a failure
        val output = Intent()
        output.putExtra("latlong", currentLocation.latitude.toString())
        //output.putExtra("long", currentLocation.longitude.toString())
        setResult(RESULT_CANCELED,output);
        super.onBackPressed();
        finish()
    }


private fun  fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }

            val task = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener {
                if (it != null) {
                    currentLocation = it
                    Toast.makeText(
                        applicationContext, currentLocation.latitude.toString() + "" +
                                currentLocation.longitude, Toast.LENGTH_SHORT
                    ).show()
                    val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map) as
                            SupportMapFragment?)!!
                    supportMapFragment.getMapAsync(this@Maps_select_update_point_for_Mishen)
                }
            }

        }



    private fun DrawMarker(latLong:LatLng,titel:String)
    {


            //val titel=getString(R.string.Maps_select_update_point_for_Mishen_Marker_Title) + mPlaceMishen
            val MarkerOptions = MarkerOptions().position(latLong).title(titel)
                .snippet(getAddress(latLong.latitude, latLong.longitude)).draggable(true)
            try {

                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLong))
                    correntMarker = mMap.addMarker(MarkerOptions)
                    correntMarker?.showInfoWindow()
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 19f))
            }
            catch (e: Exception) {
                var a=1
            }
        }

    private fun getAddress(lat:Double,long:Double):String{
        val GeoCoder=Geocoder(this, Locale.getDefault())
        val adress=GeoCoder.getFromLocation(lat,long,1)
        if (adress != null) {
            if (adress.size==0)
            {
                return "canot find adress"
            }
        }
        return adress!![0].getAddressLine(0).toString()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        /////////////////////////////////////////////
        try{
            mMap=googleMap

            //DrawMarker(mlatLng)
            //מצייר את כל הנקודות במשימה
            for (i in 0 until mlistMishenInGameAndDescription.length()) {
                val item = JSONArray(mlistMishenInGameAndDescription.getString(i))
                val longlatArry = item.getString(1).split(",")
                var tlatLng = LatLng(0.0,0.0)
                tlatLng=LatLng(longlatArry[0].toDouble(),longlatArry[1].toDouble())
                mDescripshen = item.getString(0)
                DrawMarker(tlatLng,mDescripshen)
            }

            if(mNewMishen==true){
                //mlatLng=LatLng(longlatArry[0].toDouble(),longlatArry[1].toDouble())
                if(mlistMishenInGameAndDescription.length()==0){
                    mDescripshen=getString(R.string.Maps_select_update_point_for_Mishen_Marker_New_Game_To_ADD)
                }
                else
                    mDescripshen=getString(R.string.Maps_select_update_point_for_Mishen_Marker_New_Mishen_To_ADD)
                DrawMarker(mlatLng,mDescripshen)
            }

        }
        catch (e: Exception) {
            //Logger.e(classTag, e)
            var a=1
        }

        mMap.setOnMarkerDragListener(object:GoogleMap.OnMarkerDragListener{
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                if(correntMarker!=null)
                    correntMarker?.remove()
                val newLatLong=LatLng(p0.position.latitude,p0.position.longitude)
                //NewLocation=newLatLong
                mlatLng=newLatLong
                DrawMarker(mlatLng,mDescripshen)
            }

            override fun onMarkerDragStart(p0: Marker) {

            }
        })


        /////////////////////////////////////////

       /** if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode
            )
            return
        }

        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap?.addMarker(markerOptions)

        // show wrer are yuo ril time
        googleMap.setMyLocationEnabled(true);
        if (googleMap != null) {
            googleMap.setOnMyLocationChangeListener { arg0 ->
                /* mMap.addMarker(
                     MarkerOptions().position(
                         LatLng(
                             arg0.latitude,
                             arg0.longitude
                         )
                     ).title("It's Me!")

                 )
                LatLng(
                    arg0.latitude,
                    arg0.longitude
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(
                    arg0.latitude,
                    arg0.longitude
                )))

                mMap.setMinZoomPreference(15.0F)*/

            }

        }
        */
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */




    /**override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))



  if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        mMap.setMyLocationEnabled(true);
        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("I am here!")
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
        googleMap?.addMarker(markerOptions)
/**
        if (mMap != null) {
            mMap.setOnMyLocationChangeListener { arg0 ->
               /* mMap.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            arg0.latitude,
                            arg0.longitude
                        )
                    ).title("It's Me!")

                )*/
                LatLng(
                    arg0.latitude,
                    arg0.longitude
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(
                    arg0.latitude,
                    arg0.longitude
                )))

                mMap.setMinZoomPreference(17.0F)

            }

        }*/

    }*/



}