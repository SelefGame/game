package com.example.test2

import MsgBox
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import org.json.JSONArray
import java.io.File
import java.util.*

class create_mishen_kind_1_1_radiobutten() : AppCompatActivity() {

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
    var mTotalNumberOfMishen=""
    var mAnserQwestenIs= ArrayList<String>()
    var mVidioPath=""
    var mpicherSplitNumber=""
    var mVideoSplitNumber=""
    var mclue = ""
    var mInstractionToMishen = ""
    var mDontShowMishenPoint = ""

    var mlistMishenInGameAndDescription=""



    var mGamelat=""
    var mGamelong=""
    var mFlegSetMishenLocationInMap=false

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
    private val GetGPS=12
    lateinit var viewImage: ImageView
    lateinit var mAdView : AdView
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.create_mishen_kind_1_1_radiobutten)
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


         mNewMishen= intent.getBooleanExtra("NewMishen",true)
         mlistMishenInGameAndDescription= intent.getStringExtra("listMishenInGameAndDescription").toString()


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
             mMishenQwesten= intent.getStringExtra("arrayGamesQwesten").toString()
             mMishenKind = intent.getStringExtra("arrayGamesKind").toString()
             mMishenanser1 = intent.getStringExtra("arrayGamesanser1").toString()
             mMishenanser2 = intent.getStringExtra("arrayGamesanser2").toString()
             mMishenanser3 = intent.getStringExtra("arrayGamesanser3").toString()
             mMishenanser4 = intent.getStringExtra("arrayGamesanser4").toString()
             var StringAnserQwestenIs= intent.getStringExtra("AnserQwestenIs").toString()
             StringAnserQwestenIs=StringAnserQwestenIs.removePrefix("[").trim()
             StringAnserQwestenIs=StringAnserQwestenIs.removeSuffix("]").trim()
             val arryAnserQwestenIs=StringAnserQwestenIs.split(",")
             for( i in 0..arryAnserQwestenIs.size-1)
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
             }
             mMishenlat = intent.getStringExtra("arrayGameslat").toString()
             mMishenlong= intent.getStringExtra("arrayGameslong").toString()
             mMishenremarks= intent.getStringExtra("arrayGamesremarks").toString()
             mMishenId= intent.getStringExtra("MishenId").toString()
             mGameId = intent.getStringExtra("GameId") as String
             mPlaceMishen=intent.getStringExtra("PlaceMishen").toString()
             mTotalNumberOfMishen=intent.getStringExtra("TotalNumberOfMishen").toString()
             mclue =intent.getStringExtra("clue").toString()
             mInstractionToMishen = intent.getStringExtra("InstractionToMishen").toString()
             mDontShowMishenPoint = intent.getStringExtra("DontShowMishenPoint").toString()

             findViewById<EditText?>(R.id.editText).setText(mMishenQwesten)

             findViewById<EditText?>(R.id.editTextTextPersonName1).setText(mMishenanser1)
             findViewById<EditText?>(R.id.editTextTextPersonName2).setText(mMishenanser2)
             findViewById<EditText?>(R.id.editTextTextPersonName3).setText(mMishenanser3)
             findViewById<EditText?>(R.id.editTextTextPersonName4).setText(mMishenanser4)
             var imagePath = this.filesDir.toString() + "/Gamefiles/" + mGameId + "/" + mMishenId + ".jpg"
             if (imagePath != null) {
                 this.imageBitmap = BitmapFactory.decodeFile(imagePath)
                 viewImage?.setImageBitmap(this.imageBitmap)


             }

         }
         else {

             mGameId = intent.getStringExtra("GameId") as String
             mPlaceMishen=intent.getStringExtra("mTotalNumberOfMishen").toString()
             mMishenId= UUID.randomUUID().toString()
             //   המיקום הראשוני הוא מיקום המשחק או מיקום המשימה האחרונה שהוכנס - במפה ניתן לקבל את המיקום שלך
             //if(mPlaceMishen.equals("0")){
             mMishenlat= intent.getStringExtra("lat") as String
             mMishenlong= intent.getStringExtra("long") as String
             // הזזה של מספר מטרים כדי שצייר האיקון יהיה ליוד נקודת ההתחלה ולא עליה
             mMishenlat=(mMishenlat.toDouble()+0.00000000000000).toString()
             mMishenlong=(mMishenlong.toDouble()+0.00020000000000).toString()
         }

     }



//////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun buttonChangPointOnMap(view: View)
    {
        val intent = Intent(this, maps_select_update_point_for_mishen_free_maps::class.java)
        intent.putExtra("TotalNumberOfMishen",mPlaceMishen.toString())
        intent.putExtra("lat",mMishenlat )
        intent.putExtra("long",mMishenlong)
        intent.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription)
        intent.putExtra("NewMishen",mNewMishen)
        intent.putExtra("clue",mclue)
        intent.putExtra("InstractionToMishen",mInstractionToMishen)
        intent.putExtra("DontShowMishenPoint",mDontShowMishenPoint)


        startActivityForResult(intent, GetGPS)

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////
    fun imageViewAddPic(view: View){
        openGalleryForImages()
    }
////////////////////////////////////////////////////////////////////////////////////////////////////



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
        val anser1: EditText = findViewById(R.id.editTextTextPersonName1)as EditText
        val anser2: EditText = findViewById(R.id.editTextTextPersonName2)as EditText
        val anser3: EditText = findViewById(R.id.editTextTextPersonName3)as EditText
        val anser4: EditText = findViewById(R.id.editTextTextPersonName4)as EditText

        val Qwesten: EditText = findViewById(R.id.editText)as EditText

        if (Qwesten.text.isEmpty() || anser1.text.isEmpty()|| anser2.text.isEmpty() || anser3.text.isEmpty()
            || anser4.text.isEmpty() || mAnserQwestenIs.size==0)
        {
            MsgBox.showMessageDialog(this,getString(R.string.create_mishen_kind_1_1_radiobutten_Need_TO_Add_Text))
            return
        }
        //
        if(mFlegSetMishenLocationInMap==false)
        {
            buttonChangPointOnMap(view)
           /* val intent = Intent(this, Maps_select_update_point_for_Mishen::class.java)
            intent.putExtra("TotalNumberOfMishen",mPlaceMishen.toString())
            intent.putExtra("lat",mMishenlat )
            intent.putExtra("long",mMishenlong)
            intent.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription)
            intent.putExtra("NewMishen",mNewMishen)

            startActivityForResult(intent, GetGPS)*/
            return
        }


        SaveData()

        // מחזיר עידכון כי מספר המשימות עלה ב 1
        val newPlace=(mPlaceMishen.toInt()+1)
        mPlaceMishen= newPlace.toString()
        val intent = Intent()
        intent.putExtra("mPlaceMishen", mPlaceMishen);
        setResult(RESULT_OK, intent);
        //todo חייב לעדכן את רשימת המשימות כי המשימה החדה לא נמצאת עכשיו בהצגה
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
            GetGPS ->
                if (resultCode == Activity.RESULT_OK) {
                    data?.apply {
                        mMishenlat = getStringExtra("lat")!!
                        mMishenlong = getStringExtra("long")!!
                        mFlegSetMishenLocationInMap  = getBooleanExtra("FlegSetMishenLocationInMap",false)
                        mclue = getStringExtra("clue")!!
                        mInstractionToMishen = getStringExtra("instraction")!!
                        mDontShowMishenPoint = getStringExtra("DontShowMishenPoint")!!
                    }

                }

        }
    }
//////////////////////////////////////////////////////////

}
