package com.example.test2

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import io.socket.client.Socket
import org.json.JSONArray
import java.util.*

// claster class MapsActivityToShowExistGames : AppCompatActivity(), OnMapReadyCallback {
class show_exist_games_and_mishens_to_play_on_map_main_screen : AppCompatActivity() , OnMapReadyCallback {
    public  val BUTTON_KIND_SHOW_MISHEN =1
    public  val BUTTON_KIND_START_GAME =2

    private lateinit var mMap1: GoogleMap
    //private lateinit var binding1: ActivityMapsBinding
    private lateinit var currentLocation: Location
    private lateinit var NewLocation: LatLng
    private lateinit var fusedLocationProviderClient1: FusedLocationProviderClient
    private val permissionCode = 101
    var correntMarker: Marker?=null
    var PaintMark:Boolean=false
    val HaseMapMarkerToGame = HashMap<LatLng, Int>()
    var arrayGames = JSONArray()
    var oldLat:Double=0.0
    var oldLong:Double=0.0
    var latRecivr:String=""
    var longRecivr:String=""
    var NeedToGoBackToMainGameScreen=true
    var boolDoZoom=true
    var buttenActionKind=BUTTON_KIND_SHOW_MISHEN
    private lateinit var mClusterManager:ClusterManager<clasterGames>

    var gameIndex=0
    var mSocket: Socket? = null
    var mUserId=""
    var mGameIdSelectet=""
    var MapZoom=13f


    fun setGameSelectToStartGame(){
        val textviw = findViewById<ImageView>(R.id.textViewTextButten)as TextView
        textviw.setText(getString(R.string.maps_show_exist_games_Butten_Select_Mishen))
        buttenActionKind=BUTTON_KIND_START_GAME

    }

    private var permissionDenied = false
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_exist_games_and_mishens_to_play_on_map_main_screen)
        //var jsonStringGame= intent.getStringExtra("jsonStringGame")
        latRecivr = intent.getStringExtra("Lat").toString()
        longRecivr = intent.getStringExtra("Long").toString()
        val textviw = findViewById<ImageView>(R.id.textViewTextButten)as TextView
        textviw.setText(getString(R.string.maps_show_exist_games_Butten_Mishen_Show_Mishen))
        mUserId = intent.getStringExtra("mUserId").toString()
        mSocket = SocketHandler.getSocket()
                 // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map1) as SupportMapFragment
              mapFragment.getMapAsync(this)

        mapFragment.getMapAsync(OnMapReadyCallback { googleMap ->
            //todo ניראה לי שלפה היה צריך לשים את לצייר את הנקודת ולא ב מפה מוכנה כמו שכתבתי - לבדק !!!!!!
            //setUpClusterManager(mMap1)
            //PaintAllGemesMarkOnMAp(mMap1)
        })




        mSocket!!.on("GetGaemsFromServer") { args ->
            // val root = JSONObject(json_string)
            // val array: JSONArray = root.getJSONArray("array")
            if (args[0] != null) {
                arrayGames =args[0] as JSONArray
                runOnUiThread{
                    mMap1.clear()
                PaintAllGemesMarkOnMAp(mMap1)

                }

            }
        }

    }


    private fun setUpClusterManager(googleMap: GoogleMap) {
        //val clusterManager: ClusterManager<clasterGames?> = ClusterManager<Any?>(this, googleMap) // 3
        mClusterManager = ClusterManager(this, mMap1)


        googleMap.setOnCameraIdleListener(mClusterManager)
        //val items: List<clasterGames?> = getItems()
        var clasterGames:ArrayList<clasterGames> = ArrayList()

        var latLng = LatLng(22.57264,88.3638)
        var latLng2 = LatLng(22.57364,88.3638)
        var latLng3 = LatLng(22.57964,88.3638)
        var latLng4 = LatLng(22.55264,88.3638)
        var latLng5 = LatLng(22.56364,88.3638)
        var latLng6 = LatLng(22.57694,88.3638)
        var u1 = clasterGames ( "aaaa",latLng)
        var u2 = clasterGames ( "dddd" , latLng2 )
        var u3 = clasterGames ( "ffff" , latLng3 )
        var u4 = clasterGames ( "aaaa",latLng4)
        var u5 = clasterGames ( "dddd" , latLng5 )
        var u6 = clasterGames ( "ffff" , latLng6 )
        clasterGames.add(u1)
        clasterGames.add(u2)
        clasterGames.add(u3)
        clasterGames.add(u4)
        clasterGames.add(u5)
        clasterGames.add(u6)
        //clasterManager.addItems(clasterGames)

        mClusterManager.addItems(clasterGames) // 4
        mClusterManager.cluster() // 5



        var mClusterManager1:ClusterManager<clasterGames>
        mClusterManager1 = ClusterManager(this, mMap1)
         latLng = LatLng(23.58288,88.3638)
         latLng2 = LatLng(23.59399,88.3638)
         latLng3 = LatLng(23.60977,88.3638)
         latLng4 = LatLng(23.61266,88.3638)
         latLng5 = LatLng(23.62355,88.3638)
         latLng6 = LatLng(23.63644,88.3638)
         u1 = clasterGames ( "aaaa",latLng)
         u2 = clasterGames ( "dddd" , latLng2 )
         u3 = clasterGames ( "ffff" , latLng3 )
         u4 = clasterGames ( "aaaa",latLng4)
         u5 = clasterGames ( "dddd" , latLng5 )
         u6 = clasterGames ( "ffff" , latLng6 )
        clasterGames.add(u1)
        clasterGames.add(u2)
        clasterGames.add(u3)
        clasterGames.add(u4)
        clasterGames.add(u5)
        clasterGames.add(u6)
        mClusterManager.addItems(clasterGames) // 4
        mClusterManager.cluster() // 5



    }



    private fun setUpClasterManager1(mMap1:GoogleMap) {
        //val clasterManager=ClusterManager<clasterGames>(this,mMap1)
        mMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(22.57264, 88.3638), 10f))
        mClusterManager = ClusterManager(this, mMap1)

        //mMap1?.setOnCameraIdleListener(mClusterManager)


        //PaintAllGemesMarkOnMAp(mMap1)
        var clasterGames:ArrayList<clasterGames> = ArrayList()

        val latLng = LatLng(23.57264,88.3638)
        val latLng2 = LatLng(23.57364,88.3638)
        val latLng3 = LatLng(23.57964,88.3638)
        val u1 = clasterGames ( "aaaa",latLng)
        val u2 = clasterGames ( "dddd" , latLng2 )
        val u3 = clasterGames ( "ffff" , latLng3 )
        mClusterManager.addItem(u1)
        mClusterManager.addItem(u2)
        mClusterManager.addItem(u3)
        //clasterManager.addItems(clasterGames)
        //clasterManager.cluster()
    }



    private fun PaintAllGemesMarkOnMAp(mMap1: GoogleMap) {
        try {
            //הזום שקובע את הזום במפה כמציגים משחק
            mMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latRecivr.toDouble(), longRecivr.toDouble()), MapZoom))
            mClusterManager = ClusterManager(this, mMap1)
            mMap1?.setOnCameraIdleListener(mClusterManager)

            //var clasterGames:ArrayList<clasterGames> = ArrayList()
            if (arrayGames != null && PaintMark==false) {
                //val clasterManager=ClusterManager<clasterGames>(this,mMap1)
                //mMap1?.setOnCameraIdleListener(mClusterManager)
                //var clasterGames:ArrayList<clasterGames> = ArrayList();
                var flegAddItem=false
                for (i in 0 until arrayGames.length()) {

                    //  Name
                    // val Name = jsonArray.getJSONObject(i).getString("placeNameRes")
                    val Name = arrayGames.getJSONObject(i).getJSONObject("GameInfo").getString("PlaceGame")
                    val Description = arrayGames.getJSONObject(i).getJSONObject("GameInfo").getString("GameDescription")
                    val tLat= arrayGames.getJSONObject(i).getJSONObject("GameInfo").getString("placeLat")
                    val tLong= arrayGames.getJSONObject(i).getJSONObject("GameInfo").getString("placeLong")
                    val latLng = LatLng(tLat.toDouble(), tLong.toDouble())
                    //claster DrawMarker(latLng,Description,i)
                    val u1=clasterGames("",latLng)
                    //clasterGames.add(Item1)
                    mClusterManager.addItem(u1)
                    // has table to save the index of the game to find by position to show game info when press the marker
                    HaseMapMarkerToGame[latLng] = i
                    //mClusterManager.cluster()
                    flegAddItem=true
                }

                if (flegAddItem==true) {
                //    mClusterManager.addItems(clasterGames)
                    mClusterManager.cluster()

                }
            }
        } catch (e: Exception) {
            var a=e
        }


    }




    fun buttonConvertToListView(view: View) {
        //val output = Intent()

        //output.putExtra("latlong", currentLocation.latitude.toString())
        //output.putExtra("long", currentLocation.longitude.toString())
        //setResult(RESULT_OK, output)
        //finish()
        //returnResult()
        val intent = Intent(this, games_exist::class.java)
        val arrayGamesString = arrayGames.toString()
        intent.putExtra("jsonStringGame",arrayGamesString)


    }


    // call this to return result and close activity
    fun returnResult() {

        val data = Intent().apply {
            putExtra("lat",NewLocation.latitude.toString())
            putExtra("long", NewLocation.longitude.toString())
        }
        setResult(RESULT_OK, data)
        finish()
    }


    @Override
    public override fun onBackPressed() {
        // When the user hits the back button set the resultCode
        // as Activity.RESULT_CANCELED to indicate a failure
        buttenActionKind=BUTTON_KIND_SHOW_MISHEN
        val textVeiw = findViewById<Button>(R.id.textViewTextButten)as TextView
        textVeiw.setText(getString(R.string.maps_show_exist_games_Butten_Select_Mishen))
        if (NeedToGoBackToMainGameScreen==false){
            latRecivr= oldLat.toString()
            longRecivr=oldLong.toString()
            mMap1.clear()
            PaintAllGemesMarkOnMAp(mMap1)
            NeedToGoBackToMainGameScreen=true}
        else{
            setResult(RESULT_CANCELED);
            super.onBackPressed();
            finish()
        }
        //val output = Intent()

        //output.putExtra("latlong", LatLng(oldLat,oldLong).toString())
        //output.putExtra("long", currentLocation.longitude.toString())
        //
        //super.onBackPressed();
        //finish()
    }

/**
    private fun fetchLocation() {
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

        val task = fusedLocationProviderClient1.lastLocation
        task.addOnSuccessListener {
            if (it != null) {
                currentLocation = it
                Toast.makeText(
                    applicationContext, currentLocation.latitude.toString() + "" +
                            currentLocation.longitude, Toast.LENGTH_SHORT
                ).show()
                mSocket?.emit("GetGaemsFromServer",currentLocation.latitude,currentLocation.longitude)
                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map1) as
                        SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this)

            }
        }

    }
**/


    private fun DrawMarker(latLong: LatLng, GameName: String,indexInList:Int)
    {

        val MarkerOptions = MarkerOptions().position(latLong).title(GameName)
            .snippet(getAddress(latLong.latitude, latLong.longitude))//.draggable(true)
        try {

            //var xx= MarkerOptions
            mMap1.animateCamera(CameraUpdateFactory.newLatLng(latLong))
            correntMarker = mMap1.addMarker(MarkerOptions)

            // has table to save the index of the game to find by position to show game info when press the marker
            HaseMapMarkerToGame[latLong] = indexInList
            correntMarker?.showInfoWindow()
            mMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, MapZoom))


        }
        catch (e: Exception) {
            var a=1
        }
    }

    private fun getAddress(lat:Double,long:Double):String{
        val GeoCoder= Geocoder(this, Locale.getDefault())
        val adress=GeoCoder.getFromLocation(lat,long,1)
        if (adress != null) {
            if (adress.size==0)
            {
                return "canot find adress"
            }
        }
        return adress!![0].getAddressLine(0).toString()
    }

///////////////////// הצגת המשימות במשחק שנבחר על המפה//////////////////////////
       fun buttonShowMishen(view: View) {
           if (buttenActionKind==BUTTON_KIND_SHOW_MISHEN) {
               if (mGameIdSelectet.equals("")){
                   MsgBox.showMessageDialog(this,getString(R.string.maps_show_exist_games_MSB_Txt_Need_SelectGame))
                   // val MsgBox=MsgBox()
                   //MsgBox.show(this,getString(R.string.maps_show_exist_games_MSB_Txt_Need_SelectGame),getString(R.string.maps_show_exist_games_MSB_Title_Need_SelectGame))
               }
               else {
                   buttenActionKind = BUTTON_KIND_START_GAME
                   var MishensArry: JSONArray;
                   NeedToGoBackToMainGameScreen = false
                   MishensArry = arrayGames.getJSONObject(gameIndex).getJSONArray("Mishens")
                   mMap1.clear()
                   for (i in 0 until MishensArry.length()) {


                       val Qwesten = MishensArry.getJSONObject(i).getString("Qwesten")
                       oldLat = MishensArry.getJSONObject(i).getString("lat").toDouble()
                       oldLong = MishensArry.getJSONObject(i).getString("long").toDouble()
                       val tlatLng = LatLng(oldLat, oldLong)

                       DrawMarker(tlatLng, "Qwesten", i)

                   }
                   setGameSelectToStartGame()
               }
           }
               else{
                   if (buttenActionKind==BUTTON_KIND_START_GAME) {
                       if (mGameIdSelectet.equals("")){
                           MsgBox.showMessageDialog(manage_Mishen_When_Play_game.mContext!!,"ffff")
                            //val MsgBox=MsgBox()
                           //MsgBox.show(this,getString(R.string.maps_show_exist_games_MSB_Txt_Need_SelectGame),getString(R.string.maps_show_exist_games_MSB_Title_Need_SelectGame))
                       }
                       else {
                           val intent = Intent(
                               this,
                               show_exist_games_and_mishens_to_play_save_game_to_play::class.java
                           )
                           intent.putExtra("mUserId", mUserId)
                           intent.putExtra("GameId", mGameIdSelectet)
                           intent.putExtra(
                               "arrayGame",
                               arrayGames.getJSONObject(gameIndex).toString()
                           )
                           intent.putExtra("Long", longRecivr)
                           intent.putExtra("Lat", latRecivr)

                           startActivity(intent)
                       }
                   }
               }
       }




    override fun onMapReady(googleMap: GoogleMap) {
        /////////////////////////////////////////////
        try{
            mMap1=googleMap
           mSocket!!.emit("GetGaemsFromServer",latRecivr.toDouble(),longRecivr.toDouble(),mUserId)
            mMap1?.setOnCameraIdleListener({
                GoogleMap.OnCameraIdleListener {

                    val coords = mMap1.cameraPosition.target
                    val XX = oldLat
                    val dd = oldLong
                    if (oldLat != coords.latitude && oldLong != coords.longitude) {
                        oldLat = coords.latitude

                        oldLat = dd
                        oldLong = coords.longitude
                        //mSocket?.emit("GetGaemsFromServer", coords.latitude, coords.longitude)

                        PaintMark = false
                    }
                    //Called when camera movement has ended, there are no pending animations and the user has stopped interacting with the map.
                }
            })
            //val markerInfoWindowAdapter = MarkerInfoWindowAdapter(applicationContext)
            //googleMap.setInfoWindowAdapter(markerInfoWindowAdapter)


            //setUpClusterManager(mMap1);
            //PaintAllGemesMarkOnMAp(mMap1) ****


            //setUpClasterManager1(mMap1);
            //
            // Setting a custom info window adapter for the google map
            // Setting a custom info window adapter for the google map


            // Adding and showing marker when the map is touched
/**
            mMap1.setOnCameraMoveListener(){
                val coords = mMap1.cameraPosition.target

                mSocket?.emit("GetGaemsFromServer",coords.latitude,coords.longitude)

                PaintMark=false

            }**/








            // Adding and showing marker when the map is touched
            mMap1.setOnMapClickListener { arg0 ->
                mSocket?.emit("תץ",latRecivr.toDouble(),longRecivr.toDouble())
                mMap1.clear()
                PaintAllGemesMarkOnMAp(mMap1)
                //val markerOptions = MarkerOptions()
                //markerOptions.position(arg0)
                //mMap1.animateCamera(CameraUpdateFactory.newLatLng(arg0))
                //val marker: Marker = mMap1.addMarker(markerOptions)!!
                //marker.showInfoWindow()
            }

        }
        catch (e: Exception) {
            //Logger.e(classTag, e)
            var a=1
        }



        // adding on click listener to marker of google maps.
        // adding on click listener to marker of google maps.

        mMap1.setOnMarkerClickListener(OnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
            // which is clicked and displaying it in a toast message.
            if (buttenActionKind!=BUTTON_KIND_START_GAME) {
                val markerPosition = marker.position
                gameIndex = HaseMapMarkerToGame[markerPosition]!!
                val a=gameIndex
                val Gamesubject: TextView = findViewById(R.id.textViewSabject) as TextView
                val GameDescription: TextView =findViewById(R.id.textViewDescription) as TextView
                val GameKind: TextView = findViewById(R.id.textViewGameKind) as TextView
                val NumberMishen: TextView = findViewById(R.id.textViewNumberMishen) as TextView
                val subject = arrayGames.getJSONObject(gameIndex!!).getJSONObject("GameInfo").getString("Gamesubject")
                val Description = arrayGames.getJSONObject(gameIndex).getJSONObject("GameInfo").getString("GameDescription")
                val Kind = arrayGames.getJSONObject(gameIndex).getJSONObject("GameInfo").getString("GameKind")
                val mishenNumber =arrayGames.getJSONObject(gameIndex).getJSONArray("Mishens").length()
                Gamesubject.text = subject
                GameDescription.text = Description
                GameKind.text = Kind
                NumberMishen.text = mishenNumber.toString()
                mGameIdSelectet = arrayGames.getJSONObject(gameIndex).getJSONObject("GameInfo").getString("GameId")
                val b=mGameIdSelectet
                val ddd=arrayGames
                val gg=9
            }
            else
            {
                val a=buttenActionKind
            }
                false

        })


        mMap1.setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener {
                marker -> // on marker click we are getting the title of our marker
            // which is clicked and displaying it in a toast message.
            val markerName = marker.title

            false
        })




        mMap1.setOnMarkerDragListener(object: GoogleMap.OnMarkerDragListener{
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                if(correntMarker!=null)
                    correntMarker?.remove()
                val newLatLong= LatLng(p0.position.latitude,p0.position.longitude)
                NewLocation=newLatLong

                DrawMarker(newLatLong,"",0)
            }

            override fun onMarkerDragStart(p0: Marker) {

            }
        })





    }






}