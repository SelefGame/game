package com.example.test2

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.Marker
//import com.google.maps.android.clustering.ClusterManager
import io.socket.client.Socket
import org.json.JSONArray

import org.osmdroid.views.overlay.Marker

import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import java.util.HashMap

class MapsActivity1 : AppCompatActivity() {
    public  val BUTTON_KIND_SHOW_MISHEN =1
    public  val BUTTON_KIND_START_GAME =2


    //private lateinit var binding1: ActivityMapsBinding
    private lateinit var currentLocation: Location
    private lateinit var NewLocation: LatLng

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
    //private lateinit var mClusterManager: ClusterManager<clasterGames>
    var mCommonClickListener: Marker.OnMarkerClickListener? = null
    var gameIndex=0
    var mSocket: Socket? = null
    var mUserId=""
    var mGameIdSelectet=""
    var MapZoom=19


    fun setGameSelectToStartGame(){
        val textviw = findViewById<ImageView>(R.id.textViewTextButten)as TextView
        textviw.setText(getString(R.string.maps_show_exist_games_Butten_Select_Mishen))
        buttenActionKind=BUTTON_KIND_START_GAME

    }

    private var permissionDenied = false
    private lateinit var mapView: MapView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_games_on_map_free_maps)






        // Initialize osmdroid configuration (typically in your app's Application class)
        Configuration.getInstance().load(applicationContext, getPreferences(MODE_PRIVATE))


        mapView = findViewById(R.id.mapView22)

        /*
  You can replace TileSourceFactory.MAPNIK with other available tile sources like
  TileSourceFactory.MAPNIK
  TileSourceFactory.USGS_TOPO,
  TileSourceFactory.MAPQUESTOSM, or
   TileSourceFactory.CYCLEMAP

   */


        // Set the map's tile source (e.g., Mapnik for standard OSM tiles)
        //mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        // Enable multi-touch gestures for zooming
        mapView.setMultiTouchControls(true)


        //var jsonStringGame= intent.getStringExtra("jsonStringGame")
        latRecivr = intent.getStringExtra("Lat").toString()
        longRecivr = intent.getStringExtra("Long").toString()
        val textviw = findViewById<ImageView>(R.id.textViewTextButten)as TextView
        textviw.setText(getString(R.string.maps_show_exist_games_Butten_Mishen_Show_Mishen))
        mUserId = intent.getStringExtra("mUserId").toString()
        mSocket = SocketHandler.getSocket()

        // Set the initial map position and zoom level (optional)
        mapView.controller.setZoom(MapZoom)
        mapView.controller.setCenter(org.osmdroid.util.GeoPoint(latRecivr.toDouble(), longRecivr.toDouble())) // Example coordinates for Paris, France
        mSocket!!.emit("GetGaemsFromServer",latRecivr.toDouble(),longRecivr.toDouble(),mUserId)

        // Create a common OnMarkerClickListener for all markers
        val commonClickListener = Marker.OnMarkerClickListener { marker, mapView ->
            // Perform a common action when any marker is clicked
            Toast.makeText(this, "Marker clicked: ${marker.title}", Toast.LENGTH_SHORT).show()
            if (buttenActionKind!=BUTTON_KIND_START_GAME) {
                val markerPosition = marker.position
                val latLng = LatLng(markerPosition.latitude.toDouble() , markerPosition.longitude.toDouble())
                gameIndex = HaseMapMarkerToGame[latLng]!!
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





            true // Return true to consume the click event
        }
        mCommonClickListener=commonClickListener




        mSocket!!.on("GetGaemsFromServer") { args ->
            // val root = JSONObject(json_string)
            // val array: JSONArray = root.getJSONArray("array")
            if (args[0] != null) {
                arrayGames =args[0] as JSONArray
                runOnUiThread{
                    //mapView.clear
                    PaintAllGemesMarkOnMAp()

                }

            }
        }

    }

    private fun PaintAllGemesMarkOnMAp() {
        try {
            //הזום שקובע את הזום במפה כמציגים משחק
            // Set the initial map position and zoom level (optional)
            mapView.controller.setZoom(MapZoom)

            /*mMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latRecivr.toDouble(), longRecivr.toDouble()), MapZoom))
            mClusterManager = ClusterManager(this, mMap1)
            mMap1?.setOnCameraIdleListener(mClusterManager)
            */


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
                    //val u1=clasterGames("",latLng)
                    //clasterGames.add(Item1)
                    //mClusterManager.addItem(u1)
                    // has table to save the index of the game to find by position to show game info when press the marker

                    HaseMapMarkerToGame[latLng] = i
                    val marker1 = Marker(mapView)
                    val geoPoint1 = GeoPoint(tLat.toDouble(), tLong.toDouble()) // Coordinates for marker 1
                    marker1.position = geoPoint1
                    marker1.title = Description
                    marker1.setOnMarkerClickListener(mCommonClickListener)
                    //marker1.icon = ContextCompat.getDrawable(this, R.drawable.femely)
                    //marker1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    mapView.overlays.add(marker1)

                    flegAddItem=true
                }
                mapView.invalidate() // Refresh the map to display the marker

                if (flegAddItem==true) {
                    //    mClusterManager.addItems(clasterGames)
                    //mClusterManager.cluster()

                }
            }
        } catch (e: Exception) {
            var a=e
        }


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
                mapView.overlays.clear()
                mapView.invalidate()

                for (i in 0 until MishensArry.length()) {


                    val Qwesten = MishensArry.getJSONObject(i).getString("Qwesten")
                    oldLat = MishensArry.getJSONObject(i).getString("lat").toDouble()
                    oldLong = MishensArry.getJSONObject(i).getString("long").toDouble()
                    val tlatLng = LatLng(oldLat, oldLong)

                    DrawMarker(tlatLng.latitude,tlatLng.longitude, "Qwesten", i)

                }
                mapView.invalidate() // Refresh the map to display the marker
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


    // print the mishen of the selectet game
    private fun DrawMarker(tLat: Double,tLong:Double, GameName: String,indexInList:Int)
    {

        //val MarkerOptions = MarkerOptions().position(latLong).title(GameName)
        //    .snippet(getAddress(latLong.latitude, latLong.longitude))//.draggable(true)
        try {

            //var xx= MarkerOptions
            //mMap1.animateCamera(CameraUpdateFactory.newLatLng(latLong))
            //correntMarker = mMap1.addMarker(MarkerOptions)
            val marker1 = Marker(mapView)
            val geoPoint1 = GeoPoint( tLat.toDouble(), tLong.toDouble()) // Coordinates for marker 1
            marker1.position = geoPoint1
            marker1.title = GameName
            mapView.overlays.add(marker1)
            // has table to save the index of the game to find by position to show game info when press the marker
            val latLng = LatLng(tLat.toDouble(), tLong.toDouble())
            HaseMapMarkerToGame[latLng] = indexInList
            //correntMarker?.showInfoWindow()
            //mMap1.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, MapZoom))


        }
        catch (e: Exception) {
            var a=1
        }
    }


}


