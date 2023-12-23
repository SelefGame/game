package com.example.test2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.databinding.DialogMessageBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class create_new_mishen_to_game_main_screan : AppCompatActivity(){
    var imageBitmap: Bitmap? =null
    val NewGameAdd=333

    val arrayListMissen: ArrayList<missen> = ArrayList()
    fun <String> append(arr: Array<String>, element: String): Array<String?> {
        val array = arr.copyOf(arr.size + 1)
        array[arr.size] = element
        return array
    }

    fun <missen> appendArry(arr: Array<com.example.test2.missen?>, element: com.example.test2.missen): Array<com.example.test2.missen?> {
        val array = arr.copyOf( 1)
        array[arr.size] = element
        return array
    }
    // todo צריך לקרא לפענוח תמונה מהקלאס כתביה ושמירה של קבצים
  /*  fun Base64ToPicher(encodedImageString: String): Bitmap? {
        try {
            //val base64Image = encodedImageString.split(",")[1]
            val decodedString = Base64.decode(encodedImageString, Base64.DEFAULT)
            val bitmap =
                BitmapFactory.decodeByteArray(decodedString,0,decodedString.size)
           return bitmap
        } catch (e: Exception) {
            return null
        }
    }
*/
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation")
    fun GetDraftMishenFromPhone(){
        val tempmissen=missen()


        val directory = File(this.filesDir, "Gamefiles/"+mGameId)
        if(directory==null){
            Toast.makeText(this, "ERROR ERROR ERROR FOLDER GAME NOT EXIST IS NULL", Toast.LENGTH_LONG).show()
        }
       // val size=directory.length()
       // if (size.equals(0) ) {
        var index = 0
            if (directory.listFiles()!=null) {
                val files: Array<File> = directory.listFiles()


                //val tempmissen=missen()
                // todo - need to chang it to sort list and not like i did
                for (i in files.indices) {
                    //Log.d("Files", "FileName:" + files[i].getName())
                    if (files[i].isFile && files[i].toString().contains(".jpg") == false && files[i].toString().contains(".mp4")==false) {
                        index = +1
                        arrayListMissen.add(tempmissen)
                    }

                }


                for (i in files.indices) {
                    //Log.d("Files", "FileName:" + files[i].getName())
                    if (files[i].isFile && files[i].toString().contains(".jpg") == false && files[i].isFile && files[i].toString().contains(".mp4") == false) {
                        val tempmissen = missen()
                        val f = FileReadWriteService()
                        val jsonStr =
                            f.readFileOnInternalStorage(
                                files[i].getName(),
                                "Gamefiles/" + mGameId,
                                this
                            )
                        val jsonObj = JSONObject(jsonStr)

                        try {
                            var mCoordinates=arrayOf(1.000000,1.00000)
                            var mremarks= ""
                            var mNewMishen=true
                            var mMishenId=""
                            var mPlaceOrderOfMissen="0"
                            var mpicherName:String=""
                            var mpicJpeg:Bitmap? =null
                            val mfileWrite=FileReadWriteService()
                            lateinit var mContex: Context
                            val tempAnserQwestenIs =jsonObj.get ("AnserQwestenIs").toString()
                            val tempArryAnserQwestenIs=tempAnserQwestenIs.split(",")
                            val listAnserQwestenIs = arrayListOf<String>()

                            for (index in 0 .. tempArryAnserQwestenIs.size-1) {
                               listAnserQwestenIs.add(tempArryAnserQwestenIs[index])
                            }

                            tempmissen.addInfo(
                                jsonObj.getString("kind"),
                                mGameId,
                                jsonObj.getString("Qwesten"),
                                jsonObj.getString("anser1"),
                                jsonObj.getString("anser2"),
                                jsonObj.getString("anser3"),
                                jsonObj.getString("anser4"),
                                listAnserQwestenIs,
                                jsonObj.getString("lat"),
                                jsonObj.getString("long"),
                                jsonObj.getString("remarks"),
                                jsonObj.getString("MishebId"),
                                false,
                                jsonObj.getString("picherName"),
                                f.Base64ToPicher(jsonObj.getString("picStr"))   ,
                                jsonObj.getString("VidioPath"),
                                jsonObj.getString("PlaceOrderOfMissen"),
                                this,
                                jsonObj.getString("clue"),
                                jsonObj.getString("InstractionToMishen"),
                                jsonObj.getString("DontShowMishenPoint")
                            )
                            // add the mishen to arry to the place of the mishen in the game
                            var mishemPlace = jsonObj.getString("PlaceOrderOfMissen")

                             arrayListMissen[mishemPlace.toInt()] = tempmissen
                            index = +1
                            TotalNumberOfMishen = TotalNumberOfMishen + 1
                            var a=TotalNumberOfMishen

                        } catch (ex: Exception) {
                            //your error handling code here
                            //here, consider adding Log.e("SmsReceiver", ex.localizedMessage)
                            //this log statement simply prints errors to your android studio terminal and will help with debugging, alternatively leave it out
                            val a = ex.localizedMessage

                        }
                    }

                }

                index=0
                arrayGamesQwesten=kotlin.arrayOfNulls<String>(TotalNumberOfMishen)
                arrayGamesKind=kotlin.arrayOfNulls<String>(TotalNumberOfMishen)
                for(item  in arrayListMissen){
                    //arrayGamesQwesten=append(arrayGamesQwesten,item.mQwesten)
                    //arrayGamesKind=append(arrayGamesKind,item.mKind)
                    arrayGamesQwesten.set(index,item.mQwesten)
                    arrayGamesKind.set(index,item.mKind)
                    index=index+1
                }
            }
    }


    var arrayGamesQwesten = kotlin.arrayOfNulls<String>(0)
    var arrayGamesKind = kotlin.arrayOfNulls<String>(0)
    var arrayGamesanser1 = kotlin.arrayOfNulls<String>(0)
    var arrayGamesanser2 = kotlin.arrayOfNulls<String>(0)
    var arrayGamesanser3 = kotlin.arrayOfNulls<String>(0)
    var arrayGamesanser4 = kotlin.arrayOfNulls<String>(0)
    var AnserQwestenIs = kotlin.arrayOfNulls<String>(0)
    var arrayGameslat = kotlin.arrayOfNulls<String>(0)
    var arrayGameslong = kotlin.arrayOfNulls<String>(0)
    var arrayGamesremarks = kotlin.arrayOfNulls<String>(0)
    var arrayGamesMishenId = kotlin.arrayOfNulls<String>(0)





    val mContext:Activity=this
    var mGameId=""
    var mGameSubject=""
    var mGamelat=""
    var mGamelong=""
    val mlistMishenInGameAndDescription=JSONArray()
    var TotalNumberOfMishen=0
    lateinit var mAdView : AdView
    var ListVewLongKlickIndex=-1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val  languageMnager= LanguageMnager(this)
        languageMnager.UpdateResource("he")
        setContentView(R.layout.create_new_mishen_to_game_main_screan)
        //val  languageMnager= LanguageMnager(this)
        //languageMnager.UpdateResource("he")
        mGameId= intent.getStringExtra("GameId") as String
        mGameSubject= intent.getStringExtra("GameSubject") as String
        mGamelat= intent.getStringExtra("lat") as String
        mGamelong= intent.getStringExtra("long") as String




        ////////מתחיל את הפרסומת של גוגל/////////////////////////////////////////
        fun StartGoogleAddOns(){
            var adView = AdView(this)
            //adView.setAdSize(AdSize(300, 50))
            // for test -
            adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
            // prodaction
            //adView.adUnitId = "ca-app-pub-2330011334188307/7965566378"
            //ca-app-pub-2330011334188307/7965566378
            MobileAds.initialize(this) {}

            mAdView = findViewById(R.id.adView1)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
        }

        StartGoogleAddOns()










        val editTextGameSubject: TextView = findViewById(R.id.editTextGameDescription) as TextView
        editTextGameSubject.text=mGameSubject
        GetDraftMishenFromPhone()

        //יצירת מערך של כל הנקודות של המשימות וכותרת של כל נקודה
        //הנקודה הראשונה שהיא תחילת המשחק
        var point=mGamelat +","+mGamelong
        mlistMishenInGameAndDescription.put(JSONArray(arrayOf(getString(R.string.create_new_mishen_to_game_main_screan_Arry_Point_Info_Game),point.toString())))
        //הנקודות של המשימות
        for (item in arrayListMissen) {
            point=item.mlat+","+item.mlong
            val mishenNumber=(item.mPlaceOrderOfMissen.toInt()+1).toString()
            mlistMishenInGameAndDescription.put(JSONArray(arrayOf(getString(R.string.Maps_select_update_point_for_Mishen_Marker_Title)+mishenNumber,point.toString())))

        }

        val listView = findViewById<ListView>(R.id.listView)
        var mGamesKind:Array<String> =arrayGamesKind as Array<String>
        val mGamesQwesten:Array<String> = arrayGamesQwesten as Array<String>
        var imageId = arrayOf<Int>()
        var Index=0
        for (item in arrayGamesKind) {
            if (item.equals("1.1")  ) {
                imageId+= R.drawable.q_4_anser_camera
                mGamesKind[Index] =getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_kind4_LableKindDescription)
            }
            if (item.equals("1.2")) {
                imageId += R.drawable.q_yes_no_camera
                mGamesKind[Index] = getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_YesNo_picher_LableKindDescription)
            }
            if (item.equals( "1.3")) {
                imageId+= R.drawable.q_4_anser
                mGamesKind[Index] =
                    getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_SCrean_New_Mishen_Q_4_Anser_LableKindDescription)
            }
            if (item.equals("1.4")) {
                imageId+= R.drawable.q_yesno
                mGamesKind[Index] =getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_YesNo_picher_LableKindDescription)
            }
            if (item.equals("2.1")) {
                imageId+= R.drawable.q_4_anser_video
                mGamesKind[Index] =getString(R.string.CreateNewMishen_Spesific_q_SCrean_New_Mishen_Q_4_Anser_Video)
            }
            if (item.equals("2.2")) {
                imageId+= R.drawable.q_yes_no_video
                mGamesKind[Index] =getString(R.string.CreateNewMishen_Spesific_q_SCrean_New_Mishen_Q_YesNo_Video)
            }
            Index = Index + 1

        }
        val myListAdapter = MyListAdapter(mContext,mGamesKind ,mGamesQwesten ,imageId)
        listView.adapter = myListAdapter




        /////////////////////////////////////////////////////////////////////////////////////////////////////

        listView.setOnItemLongClickListener(AdapterView.OnItemLongClickListener { arg0, arg1, pos, arg3 ->
            this.ListVewLongKlickIndex = pos
            listView.getChildAt(this.ListVewLongKlickIndex).setBackgroundColor(Color.BLUE);
            true
        }
        )
//////////////////////////////////////////////////////////////////////////////////////////////////


////////////////////////////////בחירת משימה לערכיה  - לחיצה על הרשימת משימות//////////////////////////////////////////////
        listView.setOnItemClickListener(){adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            var intent1 = Intent(Intent.ACTION_VIEW)
            val xx=arrayListMissen[position].mKind
            if (arrayListMissen[position].mKind.contains("1.")) {
                if(arrayListMissen[position].mKind.equals("1.1")){
                    intent1 = Intent(this, create_mishen_kind_1_1_radiobutten::class.java)
                }
                if(arrayListMissen[position].mKind.equals("1.2")){
                    intent1 = Intent(this, create_mishen_kind_1_2_yes_no::class.java)
                }
                if(arrayListMissen[position].mKind.equals("1.3")){
                    intent1 = Intent(this, create_mishen_kind_1_1_radiobutten::class.java)
                }
                if(arrayListMissen[position].mKind.equals("1.4")){
                    intent1 = Intent(this, create_mishen_kind_1_1_radiobutten::class.java)
                }

            }
            if (arrayListMissen[position].mKind.contains("2.")){
                if(arrayListMissen[position].mKind.equals("2.1")){
                    intent1 = Intent(this, create_mishen_kind_2_1_radiobutten::class.java)
                }
                if(arrayListMissen[position].mKind.equals("2.2")){
                    intent1 = Intent(this, create_mishen_kind_2_2_yes_no::class.java)
                }
                if(arrayListMissen[position].mKind.equals("2.3")){
                    intent1 = Intent(this, create_mishen_kind_1_1_radiobutten::class.java)
                }
                if(arrayListMissen[position].mKind.equals("2.4")){
                    intent1 = Intent(this, create_mishen_kind_1_1_radiobutten::class.java)
                }

            }




            intent1.putExtra("NewMishen",false)
            intent1.putExtra("GameId",mGameId)

            //if (arrayListMissen[position].mKind.equals("1")){
            intent1.putExtra("arrayGamesQwesten" , arrayListMissen[position].mQwesten)
            intent1.putExtra("arrayGamesKind" ,arrayListMissen[position].mKind)
            intent1.putExtra( "arrayGamesanser1" ,arrayListMissen[position].manser1)
            intent1.putExtra( "arrayGamesanser2" ,arrayListMissen[position].manser2)
            intent1.putExtra( "arrayGamesanser3" ,arrayListMissen[position].manser3)
            intent1.putExtra("arrayGamesanser4" ,arrayListMissen[position].manser4)
            val ssss=arrayListMissen[position].mAnserQwestenIs.toString()
            intent1.putExtra("AnserQwestenIs",arrayListMissen[position].mAnserQwestenIs.toString())
            intent1.putExtra("arrayGameslat" ,arrayListMissen[position].mlat)
            intent1.putExtra("arrayGameslong" ,arrayListMissen[position].mlong)
            intent1.putExtra("arrayGamesremarks" ,arrayListMissen[position].mremarks)
            intent1.putExtra("MishenId",arrayListMissen[position].mMishenId)
            intent1.putExtra("imageBitmapName",arrayListMissen[position].mpicherName)
            intent1.putExtra("PlaceMishen",arrayListMissen[position].mPlaceOrderOfMissen)
            intent1.putExtra("TotalNumberOfMishen",TotalNumberOfMishen.toString())
            intent1.putExtra("VidioPath",arrayListMissen[position].mVidioPath)
            intent1.putExtra("clue",arrayListMissen[position].mclue)
            intent1.putExtra("InstractionToMishen",arrayListMissen[position].mInstractionToMishen)
            intent1.putExtra("DontShowMishenPoint",arrayListMissen[position].mDontShowMishenPoint)
            val aa=arrayListMissen

            intent1.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription.toString())
            startActivity(intent1)
            }
/////////////////////////////////////////////////////////////////////////////////////////////////////

    }

    fun buttonAddNewMIshen(view: View) {

        val intent = Intent(this, create_new_mishen_to_game_select_kind_main_screan::class.java)
        intent.putExtra("GameId",mGameId)
        intent.putExtra("NewMishen",true)
        intent.putExtra("TotalNumberOfMishen",TotalNumberOfMishen.toString())
        intent.putExtra("lat",mGamelat )
        intent.putExtra("long",mGamelong)
        intent.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription.toString())
        //startActivity(intent)
        startActivityForResult(intent, NewGameAdd)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){

            NewGameAdd -> {

                finish();
                startActivity(getIntent());
            }

        }

    }


    ///////////////////// מחיקת משחק עלידי המשתמש /////////////////////// ////////////////////////
    fun Butten_Delete_Mishen(view: View){
        var fileNmae=""
        if(ListVewLongKlickIndex!=-1) {
            val DeletDir = FileReadWriteService()
            //DeletDir.DeletDir(arrayListMissen[ListVewLongKlickIndex].toString(),this)

            if (!arrayListMissen[ListVewLongKlickIndex].mpicherName.equals("")){
                fileNmae=arrayListMissen[ListVewLongKlickIndex].mGameId+"/"+ arrayListMissen[ListVewLongKlickIndex].mpicherName
                DeletDir.DeletFile(fileNmae,this)
            }

            if (!arrayListMissen[ListVewLongKlickIndex].mVidioPath.equals("")){
                fileNmae= arrayListMissen[ListVewLongKlickIndex].mVidioPath
                DeletDir.DeletFile(fileNmae,this)
            }


            fileNmae=arrayListMissen[ListVewLongKlickIndex].mMishenId .toString()+".txt"
            fileNmae=arrayListMissen[ListVewLongKlickIndex].mGameId+"/"+ fileNmae
            DeletDir.DeletFile(fileNmae,this)
            finish();
            startActivity(getIntent());
        }
        else{
            //todo יש להוסיף הןדאה במסך למשתמש
            Toast.makeText(this, "Click on item to select game", Toast.LENGTH_LONG).show()
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////







}

