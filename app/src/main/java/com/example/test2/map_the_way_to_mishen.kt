package com.example.test2

import MsgBox
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
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
import org.json.JSONObject
import java.util.*


class map_the_way_to_mishen : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
   // private lateinit var binding1: MapsSelectUpdatePointForMishenBinding
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
    var mContext:Context?=null

    var MishenInfo: JSONObject? =null
    var mmissen=missen()


    // הזמן שלאחריו יצג רמז להגיע למשימה הבאה
    var TimeToShowClue=0
    var ShowClue=true
    var FlegOKToWrithMishenPoint="false"
    var TimeToShowMap = TimeToShowClue +0

    lateinit var mAdView : AdView
    private lateinit var NewLocation: LatLng
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    var correntMarker:Marker?=null
    var mFlegSetMishenLocationInMap =true
    //var arrayGames = JSONArray()
    private lateinit var handler: Handler
    private var secondsPassed: Long = 0
    private lateinit var textViewTimer: TextView

    private var permissionDenied = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.map_the_way_to_mishen)
        //var jsonStringGame= intent.getStringExtra("jsonStringGame")
        mFlegSetMishenLocationInMap=true
        mContext=this
       // binding1 = MapsSelectUpdatePointForMishenBinding.inflate(layoutInflater)
        setContentView(R.layout.map_the_way_to_mishen)
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@map_the_way_to_mishen)
        fetchLocation()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map33) as SupportMapFragment
        mapFragment.getMapAsync(this)
        ///////////////////////////מתחיל את הפרסומת של גוגל/////////////////////////////////////////
        fun StartGoogleAddOns(){
            var adView = AdView(this)
            //adView.setAdSize(AdSize(300, 50))
            // for test -
            adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
            // prodaction
            //adView.adUnitId = "ca-app-pub-2330011334188307/7965566378"
            //ca-app-pub-2330011334188307/7965566378
            MobileAds.initialize(this) {}

            mAdView = findViewById(R.id.adView3)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
        }

        StartGoogleAddOns()
/////////////////////////////////////////////////////////////////////////////////////////////////


        handler = Handler()
        textViewTimer = findViewById(R.id.textViewTimer)
        // Start the timer when the activity is created
        startTimer()




        val tMishenStr= intent.getStringExtra("MishenString").toString()
        MishenInfo= JSONObject(tMishenStr)

        mPlaceMishen=MishenInfo!!.getString("PlaceOrderOfMissen").toString()

        //mlat = MishenInfo!!.getString("lat").toString()
        //mlong = MishenInfo!!.getString("long").toString()
        //mNewMishen=intent.getBooleanExtra("NewMishen",true)




        manage_Mishen_When_Play_game.mPlaceMishen =manage_Mishen_When_Play_game.mCorentMishenNumber.toString()
        manage_Mishen_When_Play_game.mMishenlat =MishenInfo!!.getString("lat")
        manage_Mishen_When_Play_game.mMishenlong =MishenInfo!!.getString("long")
        manage_Mishen_When_Play_game.mlistMishenInGameAndDescription =""
        manage_Mishen_When_Play_game.mNewMishen ="false"
        manage_Mishen_When_Play_game.mclue =MishenInfo!!.getString("clue")
        manage_Mishen_When_Play_game.mInstractionToMishen =MishenInfo!!.getString("InstractionToMishen")
        manage_Mishen_When_Play_game.mDontShowMishenPoint =MishenInfo!!.getString("DontShowMishenPoint")
        mlatLng = LatLng(manage_Mishen_When_Play_game.mMishenlat.toDouble(),manage_Mishen_When_Play_game.mMishenlong.toDouble())


       // if(!intent.getStringExtra("listMishenInGameAndDescription").toString().equals(""))
       //     mlistMishenInGameAndDescription= JSONArray(intent.getStringExtra("listMishenInGameAndDescription").toString())

        //mDontShowMishenPoint=  intent.getStringExtra("DontShowMishenPoint")!!
        if(manage_Mishen_When_Play_game.mDontShowMishenPoint.equals("true")) {
            FlegOKToWrithMishenPoint="false"
        }
            //manage_Mishen_When_Play_game.mclue=intent.getStringExtra("clue")!!

            //mInstractionToMishen=intent.getStringExtra("InstractionToMishen")!!
        if (!manage_Mishen_When_Play_game.mInstractionToMishen.equals("")) {
            findViewById<EditText?>(R.id.editTextInstraction).setText(manage_Mishen_When_Play_game.mInstractionToMishen.toString())
            }





    }
    private fun startTimer() {
        handler.post(object : Runnable {
            override fun run() {
                // Update UI with the elapsed time
                secondsPassed++
                val minutes = secondsPassed / 60
                val seconds = secondsPassed % 60
                //val aa =findViewById<EditText?>(R.id.textViewTimer2)
                textViewTimer.text ="Time elapsed: ${String.format("%02d:%02d", minutes, seconds)}"
                //לאחר מעבר של דקות כפי שהוחלט מציג רמז להגעה למקום המשימה
                if(minutes>TimeToShowClue && ShowClue!=false)
                {
                    ShowClue=false
                    MsgBox.showMessageDialog(mContext!!,"הגיע הזמן לקבל רמז לנקודה הבאה - בהצלחה")
                    if (!mclue.equals("")){
                        findViewById<EditText?>(R.id.editTextclue).setText(manage_Mishen_When_Play_game.mclue)
                    }
                }

                //לאחר מעבר של דקות כפי שהוחלט ועם היה מוסתרת המפה מציג על המפה איך להגעה למקום המשימה
                if(minutes>TimeToShowMap && FlegOKToWrithMishenPoint.equals("false"))
                {
                    FlegOKToWrithMishenPoint="true"
                    MsgBox.showMessageDialog(mContext!!,"הגיע הזמן לקבל עוד עזרה להגעה לנקודה הבאה -מפה - בהצלחה")

                    DrawMarker(mlatLng,"נקודה למשימה הבאה - יש לנווט אליה")
                }



                // Schedule the next update after 1 second (1000 ms)
                handler.postDelayed(this, 1000)
            }
        })
    }

    // Optionally, you can stop the timer when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    fun buttonGetGPSLongLat(view: View) {
        //val output = Intent()

        //output.putExtra("latlong", currentLocation.latitude.toString())
        //output.putExtra("long", currentLocation.longitude.toString())
        //setResult(RESULT_OK, output)
        //finish()
        returnResult()


    }



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
                /*val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map) as
                        SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this@map_the_way_to_mishen)*/
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

            if (FlegOKToWrithMishenPoint.equals(true)) {
                DrawMarker(mlatLng,"נקודה למשימה הבאה - יש לנווט אליה")
            }
            //מצייר את כל הנקודות במשימה
            /*for (i in 0 until mlistMishenInGameAndDescription.length()) {
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
            }*/

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



    }



}