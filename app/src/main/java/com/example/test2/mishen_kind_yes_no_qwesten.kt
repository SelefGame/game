package com.example.test2

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File

class mishen_kind_yes_no_qwesten: AppCompatActivity() {
    lateinit var viewImage: ImageView
    var mGameId=""
    var mMishenQwesten=""
    var mMishenKind = ""
    var mMishenanser1 = ""
    var mMishenanser2 = ""
    var mMishenanser3 = ""
    var mMishenanser4 = ""
    var mMishenlat = ""
    var mMishenlong= ""
    var mMishenremarks= ""
    var mNewMishen=true
    var mMishenId=""
    var imageBitmap: Bitmap? =null
    var imageBitmapName:String=""

    private var uri: Uri? = null
    //Our widgets
    private lateinit var btnCapture: Button
    private lateinit var btnChoose : Button
    //Our constants
    private val CAPTURE_PHOTO = 1
    private val CHOOSE_PHOTO = 2




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_mishen_kind_1_2_yes_no)
        viewImage = findViewById(R.id.imageView2);
        UpdateScreen()
    }

    fun onSendAnserButtonClicked (view: View) {

    }


    fun UpdateScreen() {
        mMishenQwesten = intent.getStringExtra("arrayGamesQwesten").toString()
        mMishenKind = intent.getStringExtra("arrayGamesKind").toString()
        mMishenanser1 = intent.getStringExtra("arrayGamesanser1").toString()
        mMishenanser2 = intent.getStringExtra("arrayGamesanser2").toString()
        mMishenanser3 = intent.getStringExtra("arrayGamesanser3").toString()
        mMishenanser4 = intent.getStringExtra("arrayGamesanser4").toString()
        mMishenlat = intent.getStringExtra("arrayGameslat").toString()
        mMishenlong = intent.getStringExtra("arrayGameslong").toString()
        mMishenremarks = intent.getStringExtra("arrayGamesremarks").toString()
        mMishenId = intent.getStringExtra("MishenId").toString()
        imageBitmapName = intent.getStringExtra("imageBitmapName").toString()
        mGameId = intent.getStringExtra("GameId") as String

        //findViewById<TextView?>(R.id.textViewQwesten).text = mMishenQwesten
        //findViewById<TextView?>(R.id.anser1useradd).text = mMishenanser1
        //findViewById<TextView?>(R.id.anser2useradd).text = mMishenanser2
        //findViewById<TextView?>(R.id.anser3useradd).text = mMishenanser3
        //findViewById<TextView?>(R.id.anser4useradd).text = mMishenanser4
        //findViewById<TextView?>(R.id.lat).text=Gameslat
        //findViewById<TextView?>(R.id.long).text=Gameslong
        //findViewById<TextView?>(R.id.remarks).text=Gamesremarks
        //findViewById<TextView?>(R.id.buttonSaveMishen).text="עדכן משימה"

        var imagePath = this.filesDir.toString() + "/Gamefiles/" + mGameId + "/" + mMishenId + ".jpg"
        if (imagePath != null) {
            this.imageBitmap = BitmapFactory.decodeFile(imagePath)
            viewImage?.setImageBitmap(this.imageBitmap)
        }

    }

}