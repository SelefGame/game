package com.example.test2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.*

class new_game_insert_main_propertis: AppCompatActivity() {
    var UserId=""
    var mGameId=""
    var mGamelat=""
    var mGamelong=""
    var mGameSubject=""
    var mclue = ""
    var mInstractionToMishen = ""
    var mDontShowMishenPoint = ""

    var mlistMishenInGameAndDescription=""
    lateinit var mAdressInfo:Array<String>
    var mContent=this
    private val GetGPS=12
    var mFlegSetMishenLocationInMap=false
    var kindNames = arrayOf("kids", "adolet", "smoll kids", "old peple", "student")
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_game_insert_main_propertis)
        //UserId= intent.getStringExtra("UserId") as String

        var adView = AdView(this)
        //adView.setAdSize(AdSize(300, 50))
        // for test -
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        // prodaction
        //adView.adUnitId = "ca-app-pub-2330011334188307/7965566378"
        //ca-app-pub-2330011334188307/7965566378
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
// TODO: Add adView to your view hierarchy.


        val locationTrack = LocationTrack(this)
        if (locationTrack.canGetLocation()) {
            mGamelong= locationTrack.getLongitude().toString()
            mGamelat= locationTrack.getLatitude().toString()

            try {
                val mAdressInfo =
                    locationTrack.getAddress(mGamelat.toDouble(),mGamelong.toDouble(), this)
                val PlaceGame: TextView = findViewById(R.id.editTextPlaceGame) as TextView
                PlaceGame.text = mAdressInfo.get(0).getAddressLine(0);
            }catch (e: Exception) {
                //Logger.e(classTag, e)
                Toast.makeText(
                    applicationContext, "Error Error get locatin - no internet", Toast.LENGTH_SHORT
                ).show()
                var a=1
            }

        } else {
            locationTrack.showSettingsAlert()
        }

        val spinner = findViewById<Spinner>(R.id.spinnerGameKind)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, kindNames)
            spinner.adapter = adapter
            }


        }


    fun buttonAddNewGame(view: View) {
        if(mFlegSetMishenLocationInMap==false)
        {
            val intent = Intent(this, Maps_select_update_point_for_Mishen::class.java)
            intent.putExtra("TotalNumberOfMishen","0")
            intent.putExtra("lat",mGamelat )
            intent.putExtra("long",mGamelong)

            intent.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription)
            intent.putExtra("NewMishen",true)


            intent.putExtra("clue","clue")
            intent.putExtra("InstractionToMishen","InstractionToMishen")
            intent.putExtra("DontShowMishenPoint","DontShowMishenPoint")
            startActivityForResult(intent, GetGPS)
            return
        }
        addNewGameDoc()
        val intent = Intent(this, create_new_mishen_to_game_main_screan::class.java)
        intent.putExtra("UserId",UserId)
        intent.putExtra("GameId",mGameId)
        intent.putExtra("GameSubject",mGameSubject)
        intent.putExtra("lat",mGamelat)
        intent.putExtra("long",mGamelong)
        intent.putExtra("mFlegSetMishenLocationInMap",mFlegSetMishenLocationInMap)


        startActivity(intent)
        finish()

    }

    fun buttonChangPointOnMap(view: View)
    {
        val intent = Intent(this, maps_select_update_point_for_mishen_free_maps::class.java)
        intent.putExtra("TotalNumberOfMishen","0")
        intent.putExtra("lat",mGamelat )
        intent.putExtra("long",mGamelong)

        intent.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription)
        intent.putExtra("NewMishen",true)
        intent.putExtra("clue",mclue)
        intent.putExtra("InstractionToMishen",mInstractionToMishen)
        intent.putExtra("DontShowMishenPoint",mDontShowMishenPoint)
        startActivityForResult(intent, GetGPS)

    }



    fun addNewGameDoc()
    {
        mGameId= UUID.randomUUID().toString()
        val PersonName: TextView = findViewById(R.id.editTextTextPersonName)as TextView
        val GameDescription: TextView = findViewById(R.id.editTextGameDescription)as TextView
        val GameKind: Spinner = findViewById(R.id.spinnerGameKind)as Spinner
        val PlaceGame: TextView = findViewById(R.id.editTextPlaceGame)as TextView
        val Gamesubject: TextView = findViewById(R.id.editTextGamesubject)as TextView
        mGameSubject=Gamesubject.text.toString()
        var Remarks: TextView = findViewById(R.id.editTextGameRemarks)as TextView
        val GameDateCreate=Date().toString()
        val updateDate="2222"
        val placeLat=mGamelat
        val placeLong=mGamelong
        val RemarksSTR=Remarks.text.toString()
        val f=FileReadWriteService()
        val indexValue: Int = GameKind.getSelectedItemPosition()
        f.writeFileOnInternalStorageGame( PersonName.text.toString(), GameDescription.text.toString(),
            kindNames[indexValue], PlaceGame.text.toString(), Gamesubject.text.toString(),
            GameDateCreate.toString(),mGameId,RemarksSTR,updateDate,placeLat,placeLong,this,mclue,mInstractionToMishen,mDontShowMishenPoint)
        f.CreateDir("Gamefiles//"+mGameId,this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){

            GetGPS ->
                if (resultCode == Activity.RESULT_OK) {
                    data?.apply {
                        mGamelat = getStringExtra("lat")!!
                        mGamelong = getStringExtra("long")!!
                        mFlegSetMishenLocationInMap= getBooleanExtra("FlegSetMishenLocationInMap",false)
                        mclue = getStringExtra("clue")!!
                        mInstractionToMishen = getStringExtra("instraction")!!
                        mDontShowMishenPoint = getStringExtra("DontShowMishenPoint")!!

                        val locationTrack = LocationTrack(mContent)
                        val mAdressInfo=locationTrack.getAddress(mGamelat.toDouble(),mGamelong.toDouble(),mContent)
                        val PlaceGame: TextView = findViewById(R.id.editTextPlaceGame)as TextView
                        PlaceGame.text=mAdressInfo.get(0).getAddressLine(0);
                        // do something
                    }

                }

        }
    }
}

