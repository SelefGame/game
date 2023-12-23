package com.example.test2

//import com.google.android.gms.maps.model.Marker

import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray
import org.osmdroid.api.IGeoPoint
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.util.*


class maps_select_update_point_for_mishen_free_maps : AppCompatActivity(){

    //private lateinit var mMap: GoogleMap
    //private lateinit var binding: MapsSelectUpdatePointForMishenBinding
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
    private lateinit var mapView: MapView
    var MapZoom=17
    private lateinit var gestureDetector: GestureDetector
    private var isMarkerSelected = false

    private lateinit var NewLocation: LatLng
    //private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    var correntMarker:Marker?=null
    var mFlegSetMishenLocationInMap =true
    //var arrayGames = JSONArray()
    private lateinit var mapController: IMapController
    private var selectedMarker: Marker? = null
    val mMarkers = mutableListOf<Marker>()


    private var permissionDenied = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maps_select_update_point_for_mishen_free_maps)
        //var jsonStringGame= intent.getStringExtra("jsonStringGame")
        mFlegSetMishenLocationInMap=true
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

        // Initialize osmdroid configuration (typically in your app's Application class)
        Configuration.getInstance().load(applicationContext, getPreferences(MODE_PRIVATE))
        mapView = findViewById(R.id.mapView22)
        mapController = mapView.controller
        // Set the map's tile source (e.g., Mapnik for standard OSM tiles)
        //mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        // Set the initial map position and zoom level (optional)
        val startPoint = GeoPoint(mlatLng.latitude,mlatLng.longitude) // Coordinates for Paris, France
        mapView.controller.setCenter(startPoint)
        mapView.controller.setZoom(MapZoom)
        // Enable multi-touch gestures for zooming
        mapView.setMultiTouchControls(true)
        mapView.isClickable = true
        mapView.invalidate() // Refresh the map to display the marker


        for (i in 0 until mlistMishenInGameAndDescription.length()) {
            val item = JSONArray(mlistMishenInGameAndDescription.getString(i))
            val longlatArry = item.getString(1).split(",")
            var tlatLng = LatLng(0.0,0.0)
            tlatLng=LatLng(longlatArry[0].toDouble(),longlatArry[1].toDouble())
            mDescripshen = item.getString(0)
            DrawMarker(tlatLng.latitude,tlatLng.longitude,mDescripshen)
        }

        if(mNewMishen==true){
            //mlatLng=LatLng(longlatArry[0].toDouble(),longlatArry[1].toDouble())
            if(mlistMishenInGameAndDescription.length()==0){
                mDescripshen=getString(R.string.Maps_select_update_point_for_Mishen_Marker_New_Game_To_ADD)
            }
            else
                mDescripshen=getString(R.string.Maps_select_update_point_for_Mishen_Marker_New_Mishen_To_ADD)
            DrawMarker(mlatLng.latitude,mlatLng.longitude,mDescripshen)
        }
        mapView.invalidate() // Refresh the map to display the marker

        // Customize the layout to your preferences (e.g., background color, padding, etc.)
        // For example, you can set a background color:
        val customInfoLayout = LayoutInflater.from(this).inflate(R.layout.custom_info_layout, null)
        customInfoLayout.setBackgroundColor(Color.WHITE)



        // Iterate through markers and create a custom layout for each marker
        /*for (marker in mMarkers) {
            // Check if the marker's position is null
            val markerPosition = marker.position
            if (markerPosition == null) {
                continue // Skip this marker and proceed to the next one
            }

            // Create a new instance of the custom layout for each marker
            val customInfoLayout = LayoutInflater.from(this).inflate(R.layout.custom_info_layout, null)

            // Customize the layout to your preferences (e.g., background color, padding, etc.)
            // For example, you can set a background color:
            customInfoLayout.setBackgroundColor(Color.WHITE)

            val titleTextView = customInfoLayout.findViewById<TextView>(R.id.titleTextView)
            val snippetTextView = customInfoLayout.findViewById<TextView>(R.id.snippetTextView)

            titleTextView.text = marker.title
            snippetTextView.text = marker.snippet

            // Calculate the screen coordinates for marker position
            val screenPoint = mapView.projection.toPixels(markerPosition, null)

            // Set the position of the custom layout near the marker
            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.leftMargin = screenPoint.x
            layoutParams.topMargin = screenPoint.y - 100 // Adjust the vertical position as needed

            customInfoLayout.layoutParams = layoutParams

            // Add the custom layout to the mapView
            mapView.addView(customInfoLayout)
        }*/






        val a=9





        /*
        // Initialize the GestureDetector to detect long-press events
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                val tappedMarker = findTappedMarker(e)
                if (tappedMarker != null) {
                    selectedMarker = tappedMarker
                }
            }
        })

        // Set a touch listener to handle GestureDetector events
        mapView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            if (event.action == MotionEvent.ACTION_UP && selectedMarker != null) {
                // Move the selected marker to the new location
                val newPoint = mapView.projection.fromPixels(
                    event.x.toInt(),
                    event.y.toInt()
                )
                moveSelectedMarkerTo(newPoint)
                selectedMarker = null
                true // Consume the touch event
            } else {
                false
            }
        }*/

        // Set a click listener to handle marker selection and moving
        val eventsReceiver = object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                if (p != null) {
                    if (selectedMarker != null && isMarkerSelected) {
                        // Move the selected marker to the new location
                        moveSelectedMarkerTo(p)
                        isMarkerSelected = false
                    } else {
                        // Select a marker on the first click
                        selectMarkerAt(p)
                        isMarkerSelected = true
                    }
                }
                return true
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {return false }
        }

        mapView.overlayManager.add(MapEventsOverlay(eventsReceiver))

    }

    private fun selectMarkerAt(geoPoint: GeoPoint) {
        val zoomLevel = mapView.zoomLevelDouble // Get the current zoom level

        for (overlay in mapView.overlays) {
            if (overlay is Marker) {
                val markerLocation = overlay.position
                val markerPoint = mapView.projection.toPixels(markerLocation, null)
                val clickPoint = mapView.projection.toPixels(geoPoint, null)

                val distance = Math.sqrt(
                    Math.pow((markerPoint.x - clickPoint.x).toDouble() / zoomLevel, 2.0) +
                            Math.pow((markerPoint.y - clickPoint.y).toDouble() / zoomLevel, 2.0)
                )

                // Adjust this value as needed for marker selection radius based on the zoom level
                val selectionRadius = 70 / zoomLevel

                if (distance <= selectionRadius) {
                    selectedMarker = overlay
                    return
                }
            }
        }


        val overlayManager = mapView.overlays


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
        //output.putExtra("latlong",mlatLng.toString())
        //output.putExtra("long", currentLocation.longitude.toString())
        setResult(RESULT_CANCELED,output);
        super.onBackPressed();
        finish()
    }




    private fun moveSelectedMarkerTo(newPoint: IGeoPoint) {
        selectedMarker?.position = GeoPoint(newPoint.latitude, newPoint.longitude)
        mlatLng = LatLng(newPoint.latitude,newPoint.longitude)

        //val ff=LatLng (newPoint.latitude,newPoint.longitude)
        //val location = Location("")
        //location.latitude =newPoint.latitude
        //location.longitude = newPoint.longitude

        //currentLocation=location
        mapView.invalidate()
    }

   /* private fun findTappedMarker(e: MotionEvent): Marker? {
        val tappedGeoPoint = mapView.projection.fromPixels(e.x.toInt(), e.y.toInt())
        for (overlay in mapView.overlays) {
            if (overlay is Marker) {
                val markerLocation = overlay.position
                val markerPoint = mapView.projection.toPixels(markerLocation, null)

                // Define a radius for the marker's "hit area" in pixels
                val markerHitRadius = 150 // Adjust this value as needed

                // Calculate the distance between the tapped point and the marker's center
                val distance = Math.sqrt(
                    Math.pow((markerPoint.x - e.x).toDouble(), 2.0) +
                            Math.pow((markerPoint.y - e.y).toDouble(), 2.0)
                )

                if (distance <= markerHitRadius) {
                    return overlay
                }
            }
        }
        return null
    }

*/

    // print the mishen of the selectet game
    private fun DrawMarker(tLat: Double,tLong:Double,GameName:String) {

        //val MarkerOptions = MarkerOptions().position(latLong).title(GameName)
        //    .snippet(getAddress(latLong.latitude, latLong.longitude))//.draggable(true)
        try {

            //var xx= MarkerOptions
            //mMap1.animateCamera(CameraUpdateFactory.newLatLng(latLong))
            //correntMarker = mMap1.addMarker(MarkerOptions)
            val marker1 = org.osmdroid.views.overlay.Marker(mapView)
            val geoPoint1 = GeoPoint(tLat.toDouble(), tLong.toDouble()) // Coordinates for marker 1
            marker1.position = geoPoint1
            marker1.title = GameName
            marker1.showInfoWindow()
            mapView.controller.setZoom(19)
            mapView.overlays.add(marker1)
            mapView.invalidate() // Refresh the map to display the marker
            mMarkers.add(marker1)

        } catch (e: Exception) {
            var a = 1
        }
    }

    /*
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
    }*/



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







}




