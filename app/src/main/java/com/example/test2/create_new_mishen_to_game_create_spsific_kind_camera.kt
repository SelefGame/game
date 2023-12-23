package com.example.test2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.cardview.widget.CardView

class create_new_mishen_to_game_create_spsific_kind_camera : AppCompatActivity() {
    var mGameId=""
    var mNewMishen=true
    var mPlaceMishen=""
    var mKind=""
    var mQwestenView1: Intent? =null
    var mQwestenView2: Intent? =null
    var mQwestenView3: Intent? =null
    var mQwestenView4: Intent? =null
    var mVidioPath=""
    var mGamelat=""
    var mGamelong=""
    var mlistMishenInGameAndDescription=""
    var addNewMishen = 999
    lateinit var myIntent:ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_new_mishen_to_game_create_spsific_kind_camera)
        mGameId= intent.getStringExtra("GameId") as String
        mNewMishen=intent.getBooleanExtra("NewMishen",true)
        mPlaceMishen= intent.getStringExtra("PlaceMishen") as String
        mKind=intent.getStringExtra("Kind") as String
        mGamelat= intent.getStringExtra("lat") as String
        mGamelong= intent.getStringExtra("long") as String
        mlistMishenInGameAndDescription=intent.getStringExtra("listMishenInGameAndDescription") as String
        if (mNewMishen==false)  mVidioPath=intent.getStringExtra("VidioPath") as String
        //mainBinding = CreateNewMishenToGameSelectKindMainScreanBinding.inflate(layoutInflater)
        //setContentView(mainBinding.root)
        ///mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        val kind1: CardView = findViewById(R.id.kind1)
        val kind2: CardView = findViewById(R.id.kind2)
        val kind3: CardView = findViewById(R.id.kind3)
        ///////// מעדכן את הכיתוב בכפתורים בהתאם לסוג המשימה וגם מעדכן אייכונים
        if (mKind.equals("1"))
        {
            val editTextButten1: TextView = findViewById(R.id.butten1) as TextView
            val editTextButten2: TextView = findViewById(R.id.butten2) as TextView
            val editTextButten3: TextView = findViewById(R.id.butten3) as TextView
            val editTextButten4: TextView = findViewById(R.id.butten4) as TextView

            var imageView1=findViewById<ImageView>(R.id.ImageView1)
            var imageView2=findViewById<ImageView>(R.id.ImageView2)
            var imageView3=findViewById<ImageView>(R.id.ImageView3)
            var imageView4=findViewById<ImageView>(R.id.ImageView4)

            imageView1.setImageResource(R.drawable.q_4_anser_camera)
            imageView2.setImageResource(R.drawable.q_yes_no_camera)
            imageView3.setImageResource(R.drawable.q_4_anser)
            imageView4.setImageResource(R.drawable.q_yesno)

            editTextButten1.text=getString(R.string.CreateNewMishen_Spesific_q_SCrean_New_Mishen_Q_4_Anser_Picher)
            editTextButten2.text=getString(R.string.CreateNewMishen_Spesific_q_SCrean_New_Mishen_Q_YesNo_picher)
            editTextButten3.text=getString(R.string.CreateNewMishen_Spesific_q_SCrean_New_Mishen_Q_4_Anser)
            editTextButten4.text=getString(R.string.CreateNewMishen_Spesific_q_SCrean_New_Mishen_Q_YesNo)

            mQwestenView1=Intent(this, create_mishen_kind_1_1_radiobutten::class.java)
            mQwestenView2=Intent(this, create_mishen_kind_1_2_yes_no::class.java)
            mQwestenView3=Intent(this, create_mishen_kind_1_2_yes_no::class.java)
            mQwestenView4=Intent(this, create_mishen_kind_1_2_yes_no::class.java)


        }
        if (mKind.equals("2")){
            val editTextButten1: TextView = findViewById(R.id.butten1) as TextView
            val editTextButten2: TextView = findViewById(R.id.butten2) as TextView
            val editTextButten3: TextView = findViewById(R.id.butten3) as TextView
            val editTextButten4: TextView = findViewById(R.id.butten4) as TextView

            var imageView1=findViewById<ImageView>(R.id.ImageView1)
            var imageView2=findViewById<ImageView>(R.id.ImageView2)
            var imageView3=findViewById<ImageView>(R.id.ImageView3)
            var imageView4=findViewById<ImageView>(R.id.ImageView4)

            imageView1.setImageResource(R.drawable.q_4_anser_video)
            imageView2.setImageResource(R.drawable.q_yes_no_video)
            imageView3.setImageResource(R.drawable.q_4_anser)
            imageView4.setImageResource(R.drawable.q_yesno)

            editTextButten1.text=getString(R.string.CreateNewMishen_Spesific_q_SCrean_New_Mishen_Q_4_Anser_Video)
            editTextButten2.text=getString(R.string.CreateNewMishen_Spesific_q_SCrean_New_Mishen_Q_YesNo_Video)
            editTextButten3.text=getString(R.string.CreateNewMishen_Spesific_q_SCrean_New_Mishen_Video3)
            editTextButten4.text=getString(R.string.CreateNewMishen_Spesific_q_SCrean_New_Mishen_Q_Video4)



            mQwestenView1= Intent(this,create_mishen_kind_2_1_radiobutten::class.java)
            mQwestenView2= Intent(this,create_mishen_kind_2_2_yes_no::class.java)
            mQwestenView3= Intent(this,create_mishen_kind_1_2_yes_no::class.java)
            mQwestenView4= Intent(this,create_mishen_kind_1_2_yes_no::class.java)


        }
        if (mKind.equals("3")){

        }


        kind1.setOnClickListener {
            mQwestenView1?.putExtra("Kind","2.1")
            mQwestenView1?.putExtra("GameId",mGameId)
            mQwestenView1?.putExtra("NewMishen",mNewMishen)
            mQwestenView1?.putExtra("mTotalNumberOfMishen",mPlaceMishen)
            mQwestenView1?.putExtra("PlaceMishen",mPlaceMishen)
            mQwestenView1?.putExtra("lat",mGamelat )
            mQwestenView1?.putExtra("long",mGamelong)
            mQwestenView1?.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription.toString())
            //intent.putExtra("Description",arrayGamesDescription)
            //startActivity(intent)
            startActivityForResult(mQwestenView1!!, 999)
        }

        kind2.setOnClickListener {
            //val intent = Intent(this,  mQwestenView2::class.java)
            // val intent = Intent(this, Create_mishen_do_activity_tack_same_pic ::class.java)
            mQwestenView2?.putExtra("Kind","2.2")
            mQwestenView2?.putExtra("GameId",mGameId)
            mQwestenView2?.putExtra("NewMishen",mNewMishen)
            mQwestenView2?.putExtra("mTotalNumberOfMishen",mPlaceMishen)
            mQwestenView2?.putExtra("PlaceMishen",mPlaceMishen)
            mQwestenView2?.putExtra("VidioPath",mVidioPath)
            mQwestenView2?.putExtra("lat",mGamelat )
            mQwestenView2?.putExtra("long",mGamelong)
            mQwestenView2?.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription.toString())
            //intent.putExtra("Description",arrayGamesDescription)
            // startActivity(intent)
            startActivityForResult(mQwestenView2!!, addNewMishen)
        }

        kind3.setOnClickListener {
            //val intent = Intent(this,  mQwestenView1::class.java)
            // val intent = Intent(this, Create_mishen_do_activity_tack_same_pic ::class.java)
            mQwestenView3?.putExtra("Kind","2.3")
            mQwestenView3?.putExtra("GameId",mGameId)
            mQwestenView3?.putExtra("NewMishen",mNewMishen)
            mQwestenView3?.putExtra("mTotalNumberOfMishen",mPlaceMishen)
            mQwestenView3?.putExtra("PlaceMishen",mPlaceMishen)
            mQwestenView3?.putExtra("VidioPath",mVidioPath)
            mQwestenView3?.putExtra("lat",mGamelat )
            mQwestenView3?.putExtra("long",mGamelong)
            mQwestenView3?.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription.toString())
            //intent.putExtra("Description",arrayGamesDescription)

            startActivityForResult(mQwestenView3!!, addNewMishen)
        }

    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            addNewMishen -> {
                if (resultCode== RESULT_OK) {
                    val res=data?.getStringExtra("mPlaceMishen").toString()
                    mPlaceMishen = res
                    // צריך לחזור למסך משימות ראשי כדי לעדכן את המשימה החדשה
                    finish()
                }
            }

        }
    }
}