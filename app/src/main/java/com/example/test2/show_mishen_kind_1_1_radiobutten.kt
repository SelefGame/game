package com.example.test2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import org.json.JSONObject
import java.io.File
import java.util.*

class show_mishen_kind_1_1_radiobutten : AppCompatActivity() {

    var mGameId=""
    var mMishenQwesten=""
    var mMishenKind = "1.1"
    var mMishenanser1 = ""
    var mMishenanser2 = ""
    var mMishenanser3 = ""
    var mMishenanser4 = ""
    var mMishenlat = ""
    var mMishenlong= ""
    var mMishenremarks= ""
    var mNewMishen=true
    var mMishenId=""
    var mPlaceMishen=""
    var mAnserQwestenIs= ArrayList<String>()
    var mVidioPath=""
    var mclue = ""
    var mInstractionToMishen = ""
    var mDontShowMishenPoint = ""

    var MishenInfo: JSONObject? =null
    var mpicherSplitNumber=""
    var mVideoSplitNumber=""
    var ConfromPoint=false

    //var arrayListMissen: ArrayList<missen> = ArrayList()
    var mmissen=missen()

    var imageBitmap: Bitmap? =null
    var imageBitmapName:String=""

    private var uri: Uri? = null
    //Our widgets
    private lateinit var btnCapture: Button
    private lateinit var btnChoose : Button
    //Our constants
    private val CAPTURE_PHOTO = 1
    private val CHOOSE_PHOTO = 2
    private val ConfemPoint=12
    lateinit var viewImage: ImageView
    lateinit var mAdView : AdView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_mishen_kind_1_1_radiobutten)

        val tMishenStr= intent.getStringExtra("MishenString").toString()
        MishenInfo= JSONObject(tMishenStr)

        viewImage = findViewById(R.id.imageView);
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

            mAdView = findViewById(R.id.adView1)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
        }

        StartGoogleAddOns()
/////////////////////////////////////////////////////////////////////////////////////////////////



        mNewMishen= intent.getBooleanExtra("NewMishen",false)

        val locationTrack = LocationTrack(this)
        if (locationTrack.canGetLocation()) {
            mMishenlong = locationTrack.getLongitude().toString()
            mMishenlat= locationTrack.getLatitude().toString()
            Toast.makeText(
                applicationContext, """
             Longitude:${mMishenlat}
             Latitude:${mMishenlong}
             """.trimIndent(), Toast.LENGTH_SHORT
            ).show()
        } else {
            locationTrack.showSettingsAlert()
        }



        if (mNewMishen == false){
            mMishenQwesten= MishenInfo!!.getString("Qwesten").toString()
            mMishenKind = MishenInfo!!.getString("kind").toString()
            mMishenanser1 = MishenInfo!!.getString("anser1").toString()
            mMishenanser2 = MishenInfo!!.getString("anser2").toString()
            mMishenanser3 = MishenInfo!!.getString("anser3").toString()
            mMishenanser4 = MishenInfo!!.getString("anser4").toString()
            var StringAnserQwestenIs= MishenInfo!!.getString("AnserQwestenIs").toString()
            StringAnserQwestenIs=StringAnserQwestenIs.removePrefix("[").trim()
            StringAnserQwestenIs=StringAnserQwestenIs.removeSuffix("]").trim()
            val arryAnserQwestenIs=StringAnserQwestenIs.split(",")
            /*for( i in 0..arryAnserQwestenIs.size-1)
            {
                mAnserQwestenIs.add(arryAnserQwestenIs[i].trim())

                if (mAnserQwestenIs[i].equals("1")){
                    findViewById<CheckBox?>(R.id.checkBox1).isChecked=true
                }
                if (mAnserQwestenIs[i].equals("2")){
                    findViewById<CheckBox?>(R.id.checkBox2).isChecked=true
                }
                if (mAnserQwestenIs[i].equals("3")){
                    findViewById<CheckBox?>(R.id.checkBox3).isChecked=true
                }
                if (mAnserQwestenIs[i].equals("4")){
                    findViewById<CheckBox?>(R.id.checkBox4).isChecked=true
                }
            }*/
            mMishenlat = MishenInfo!!.getString("lat").toString()
            mMishenlong= MishenInfo!!.getString("long").toString()
            mMishenremarks= MishenInfo!!.getString("remarks").toString()
            mMishenId= MishenInfo!!.getString("MishebId").toString()
            mGameId = MishenInfo!!.getString("GameId") as String
            mPlaceMishen=MishenInfo!!.getString("PlaceOrderOfMissen").toString()
            mclue =intent.getStringExtra("clue").toString()
            mInstractionToMishen = intent.getStringExtra("InstractionToMishen").toString()
            mDontShowMishenPoint = intent.getStringExtra("DontShowMishenPoint").toString()

            findViewById<EditText?>(R.id.editText).setText(mMishenQwesten)

            findViewById<EditText?>(R.id.editTextTextPersonName1).setText(mMishenanser1)
            findViewById<EditText?>(R.id.editTextTextPersonName2).setText(mMishenanser2)
            findViewById<EditText?>(R.id.editTextTextPersonName3).setText(mMishenanser3)
            findViewById<EditText?>(R.id.editTextTextPersonName4).setText(mMishenanser4)

            var imagePath = filesDir.toString()+"/GameWatingToStartPlay/" + mGameId + "/" + mMishenId + ".jpg"
            if (imagePath != null) {
                this.imageBitmap = BitmapFactory.decodeFile(imagePath)
                viewImage?.setImageBitmap(this.imageBitmap)


            }
        }
        else {

            mGameId = intent.getStringExtra("GameId") as String
            mPlaceMishen=intent.getStringExtra("mTotalNumberOfMishen").toString()
            mMishenId= UUID.randomUUID().toString()

        }
     }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun buttonUpload(view: View)
    {
        val anserJsonObject=JSONObject()

        anserJsonObject.put("mAnserQwestenIs",mAnserQwestenIs)

        //בדיקה שהתשובה נכונה
        manage_Mishen_When_Play_game.executeCheckMishen(anserJsonObject,this)

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun buttonChangPointOnMap(view: View)
    {
        val intent = Intent(this, Maps_select_update_point_for_Mishen::class.java)
        startActivityForResult(intent, ConfemPoint)

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////הוספת תמונה מהמצלמה למשימה//////////////////////////////////////////////
    fun AddPicFromCamera(view: View){
        capturePhoto()
    }
//////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////// לחיצה על הוספת תמונה מהגלריה//////////////////////////
    fun AddPicFromgalery(view: View){
        openGalleryForImages()
    }
//////////////////////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////כפתור שמור משימה////////////////////////////////////////////////
    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveMishenButtonClicked(view: View) {

        SaveData()

        // מחזיר עידכון כי מספר המשימות עלה ב 1
        val newPlace=(mPlaceMishen.toInt()+1)
        mPlaceMishen= newPlace.toString()
        val intent = Intent()
        intent.putExtra("mPlaceMishen", mPlaceMishen);
        setResult(RESULT_OK, intent);


        finish()
        return
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onBackPressed() {
        setResult(RESULT_CANCELED);
        finish()
        // AND here I tried with this calls without luck. -> requireActivity().finish() or super.onBackPressed()
    }

    /////////////////////////////////פונקציה שנירת מידע לקובץ גיסון בטלפון/////////////////////////////////////////////
    @RequiresApi(Build.VERSION_CODES.O)
    fun SaveData()
    {


        val anser1: EditText = findViewById(R.id.editTextTextPersonName1)as EditText
        val anser2: EditText = findViewById(R.id.editTextTextPersonName2)as EditText
        val anser3: EditText = findViewById(R.id.editTextTextPersonName3)as EditText
        val anser4: EditText = findViewById(R.id.editTextTextPersonName4)as EditText

        val Qwesten: EditText = findViewById(R.id.editText)as EditText

        /*if (Qwesten.text.isEmpty() || anser1.text.isEmpty()|| anser2.text.isEmpty() || anser3.text.isEmpty()  || anser4.text.isEmpty())
        {
           MsgBox().show(this,"You need .... ","title")
        }*/

        mmissen.addInfo(mMishenKind,mGameId,Qwesten.text.toString() ,anser1.text.toString(),
            anser2.text.toString(),anser3.text.toString(),anser4.text.toString(),mAnserQwestenIs,mMishenlat,mMishenlong,"",
            mMishenId,true,this.imageBitmapName,this.imageBitmap,mVidioPath,
            mPlaceMishen,this,mclue,mInstractionToMishen,mDontShowMishenPoint)
        mmissen.AddMishen()

        if (this.imageBitmap!=null ){
            savePic(this.imageBitmap!!)}

        //finish();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    fun CheckBox1(view: View) {
        // Is the button now checked?
        val checked = view.isClickable
        if (checked) {
            mAnserQwestenIs.add("1")
            // Pirates are the best
        }
    }
    fun CheckBox2(view: View) {
        // Is the button now checked?
        val checked = view.isClickable
        if (checked) {
            mAnserQwestenIs.add("2")
            // Pirates are the best
        }
    }
    fun CheckBox3(view: View) {

        // Is the button now checked?
        val checked = view.isClickable
        if (checked) {
            mAnserQwestenIs.add("3")
            // Pirates are the best
        }
    }
    fun CheckBox4(view: View) {

        // Is the button now checked?
        val checked = view.isClickable
        if (checked) {
            mAnserQwestenIs.add("4")
            // Pirates are the best
        }
    }

    /////////////////////////////////// פונקציה הצגת הודעה בבועה/////////////////////////////////////////////////////////
    private fun show(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////פתיחת מצלמה לקבלת תמונה//////////////////////////////////////////////////////////////
    private fun capturePhoto(){
        val capturedImage = File(externalCacheDir, "My_Captured_Photo.jpg")
        if(capturedImage.exists()) {
            capturedImage.delete()
        }
        capturedImage.createNewFile()
        uri = if(Build.VERSION.SDK_INT >= 24){
            FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider",
                capturedImage)
        } else {
            Uri.fromFile(capturedImage)
        }

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, CAPTURE_PHOTO)
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////פתיחת גלירה לקבלת תמונה/////////////////////////////////////////////
    private fun openGalleryForImages() {

        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Pictures")
                , CHOOSE_PHOTO
            )
        }
        else { // For latest versions API LEVEL 19+
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, CHOOSE_PHOTO);
        }

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////פנקציה לשמירת תמונה לטלפון///////////////////////////////////////////////////////
// todo צריך לבדוק מה אני עושה עם התמונה או שאני משתמש בזיסון וייצר את התמונה משם אז אין צורך לשמור אותה
    fun savePic(imageBitmap: Bitmap) {
        val f=FileReadWriteService()
        f.writeFileOnInternalStorageMishenPic(mMishenId+".jpg","Gamefiles//"+mGameId,mMishenId,
            mGameId,imageBitmap ,this)
    }



    /////////////////////////פונקציה שפועלת כחזרה אחרי בחירת תמונה מהגלריה ומציה אותה באפליקציה//////////////////////////////////////
    fun SetImageFromGaleryToImageView( data: Intent?){
        // if multiple images are selected
        if (data?.getClipData() != null) {
            var count = data.clipData?.itemCount

            for (i in 0..count!! - 1) {
                var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri
                //     iv_image.setImageURI(imageUri) Here you can assign your Image URI to the ImageViews
            }

        } else if (data?.getData() != null) {
            // if single image is selected

            var imageUri: Uri = data.data!!
            var imageView=findViewById<ImageView>(R.id.imageView)
            imageView.setImageURI(imageUri)
            imageBitmap=imageView.drawable.toBitmap()


        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CAPTURE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    this.imageBitmap = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(uri!!))
                    viewImage!!.setImageBitmap(this.imageBitmap)

                }
            CHOOSE_PHOTO ->
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        SetImageFromGaleryToImageView(data)
                        //handleImageOnKitkat(data)
                    }
                }
            ConfemPoint ->
                if (resultCode == Activity.RESULT_OK) {
                    data?.apply {
                        mMishenlat = getStringExtra("lat")!!
                        mMishenlong = getStringExtra("long")!!
                        // do something
                    }
                    //סימון הדגל שהמשתמש היה במסך מקום משימה ובחא את המקןם
                    ConfromPoint=true

                }

        }
    }
//////////////////////////////////////////////////////////

}