package com.example.test2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.test2.databinding.CreateNewMishenToGameSelectKindMainScreanBinding

class create_new_mishen_to_game_select_kind_main_screan  : AppCompatActivity() {
    private lateinit var binding: CreateNewMishenToGameSelectKindMainScreanBinding
    private lateinit var mainBinding: CreateNewMishenToGameSelectKindMainScreanBinding
    var mGameId=""
    var mNewMishen=true
    var mPlaceMishen=""
    var mGamelat=""
    var mGamelong=""
    var mlistMishenInGameAndDescription=""
    var addNewMishen = 999

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main);
        mGameId= intent.getStringExtra("GameId") as String
        mNewMishen=intent.getBooleanExtra("NewMishen",true)
        mPlaceMishen= intent.getStringExtra("TotalNumberOfMishen") as String
        mGamelat= intent.getStringExtra("lat") as String
        mGamelong= intent.getStringExtra("long") as String
        mainBinding = CreateNewMishenToGameSelectKindMainScreanBinding.inflate(layoutInflater)
        mlistMishenInGameAndDescription=intent.getStringExtra("listMishenInGameAndDescription") as String
        setContentView(mainBinding.root)
        ///mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        val kind1: CardView = findViewById(R.id.kind1)
        val kind2: CardView = findViewById(R.id.kind2)
        val kind3: CardView = findViewById(R.id.kind3)


        kind1.setOnClickListener {
            //mSocket.emit("GetMain")
            //mSocket.emit("GetGaems","aaa")
            //val intent = Intent(this, Creat_QwestenSelectAnserRedioBox::class.java)
            val intent = Intent(this, create_new_mishen_to_game_create_spsific_kind_camera::class.java)
             intent.putExtra("Kind","1")
            intent.putExtra("GameId",mGameId)
            intent.putExtra("NewMishen",mNewMishen)
            intent.putExtra("PlaceMishen",mPlaceMishen)
            intent.putExtra("lat",mGamelat)
            intent.putExtra("long",mGamelong)
            intent.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription.toString())
            //intent.putExtra("mTotalNumberOfMishen",mPlaceMishen)

            //intent.putExtra("Description",arrayGamesDescription)
            //startActivity(intent)
            startActivityForResult(intent, addNewMishen)
        }

        kind2.setOnClickListener {
            //mSocket.emit("GetMain")
            //mSocket.emit("GetGaems","aaa")
            val intent = Intent(this, create_mishen_kind_1_2_yes_no ::class.java)
           // val intent = Intent(this, Create_mishen_do_activity_tack_same_pic ::class.java)
            intent.putExtra("Kind","2")
            intent.putExtra("GameId",mGameId)
            intent.putExtra("NewMishen",mNewMishen)
            intent.putExtra("PlaceMishen",mPlaceMishen)
            intent.putExtra("lat",mGamelat)
            intent.putExtra("long",mGamelong)
            intent.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription.toString())
            //intent.putExtra("Description",arrayGamesDescription)
                // startActivity(intent)
            startActivityForResult(intent, addNewMishen)
        }

        kind3.setOnClickListener {
            //mSocket.emit("GetMain")
            //mSocket.emit("GetGaems","aaa")
            val intent = Intent(this, create_new_mishen_to_game_create_spsific_kind_camera ::class.java)
            // val intent = Intent(this, Create_mishen_do_activity_tack_same_pic ::class.java)
            intent.putExtra("Kind","2")
            intent.putExtra("GameId",mGameId)
            intent.putExtra("NewMishen",mNewMishen)
            intent.putExtra("PlaceMishen",mPlaceMishen)
            intent.putExtra("lat",mGamelat)
            intent.putExtra("long",mGamelong)
            intent.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription.toString())
            //intent.putExtra("Description",arrayGamesDescription)

            startActivityForResult(intent, addNewMishen)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            addNewMishen -> {

                finish();
                //startActivity(getIntent());
            }

        }

    }





}