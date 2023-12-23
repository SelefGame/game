package com.example.test2

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.io.File
import java.util.*

private const val READ_STORAGE_PERMISSION_REQUEST_CODE = 41
class create_mishen_kind_1_2_yes_no() : AppCompatActivity() {
    var mKind="1.2"
    var mGameId=""
    var mMishenQwesten=""
    var GamesKind = ""
    var mMishenanser1 = "YES"
    var mMishenanser2 = "NO"
    var mMishenanser3 = ""
    var mMishenanser4 = ""
    var mAnserQwestenIs=ArrayList<String>()
    var mMishenlat = ""
    var mMishenlong= ""
    var mMishenremarks= ""
    var mNewMishen=true
    var mPlaceMishen=""
    var MishenId=""
    var mmissen=missen()
    var imageBitmap: Bitmap? =null
    var imageBitmapName:String=""
    var mTotalNumberOfMishen=""
    val mVidioPath=""
    var mclue = ""
    var mInstractionToMishen = ""
    var mDontShowMishenPoint = ""

    var mpicherSplitNumber=""
    var mVideoSplitNumber=""
    var mGamelat=""
    var mGamelong=""

    var mlistMishenInGameAndDescription=""
    var mFlegSetMishenLocationInMap=false
    private var uri: Uri? = null
    //Our widgets
    private lateinit var btnCapture: Button
    private lateinit var btnChoose : Button
    //Our constants
    private val CAPTURE_PHOTO = 1
    private val CHOOSE_PHOTO = 2
    private val GetGPS=12
    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val STORAGE_PERMISSION_CODE = 101
    }

     lateinit var viewImage: ImageView
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001

    var vFilename: String = ""
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_mishen_kind_1_2_yes_no)


        ///////////////////////////מתחיל את הפרסומת של גוגל/////////////////////////////////////////
        fun StartGoogleAddOns(){
            var mAdView = AdView(this)
            //adView.setAdSize(AdSize(300, 50))
            // for test -
            mAdView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
            // prodaction
            //adView.adUnitId = "ca-app-pub-2330011334188307/7965566378"
            //ca-app-pub-2330011334188307/7965566378
            MobileAds.initialize(this) {}

            mAdView = findViewById(R.id.adView2)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
        }

        StartGoogleAddOns()

/////////////////////////////////////////////////////////////////////////////////////////////////
        mlistMishenInGameAndDescription= intent.getStringExtra("listMishenInGameAndDescription").toString()





        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
            STORAGE_PERMISSION_CODE)
        checkPermission(Manifest.permission.CAMERA,
            CAMERA_PERMISSION_CODE)

        val locationTrack = LocationTrack(this)
        if (locationTrack.canGetLocation()) {
            mMishenlat = locationTrack.getLatitude().toString()
            mMishenlong = locationTrack.getLongitude().toString()
            Toast.makeText(
                applicationContext, """
             Longitude:${mMishenlat}
             Latitude:${mMishenlong}
             """.trimIndent(), Toast.LENGTH_SHORT
            ).show()
        } else {
            locationTrack.showSettingsAlert()
        }


        viewImage = findViewById(R.id.imageView2);
        mNewMishen = intent.getBooleanExtra("NewMishen", true)
        if (mNewMishen == false) {
            // todo יש להוסיף שהוא מסמן את התשובה הנכונה שנבחרה
            mMishenQwesten = intent.getStringExtra("arrayGamesQwesten").toString()
            mKind = intent.getStringExtra("arrayGamesKind").toString()
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

                if (mAnserQwestenIs[i].equals("Yes")){
                    findViewById<ImageButton?>(R.id.imageButtonYes).setBackgroundColor(Color.RED)
                    findViewById<ImageButton?>(R.id.imageButtonNo).setBackgroundColor(Color.GRAY)
                }
                if (mAnserQwestenIs[i].equals("No")){
                    findViewById<ImageButton?>(R.id.imageButtonYes).setBackgroundColor(Color.GRAY)
                    findViewById<ImageButton?>(R.id.imageButtonNo).setBackgroundColor(Color.RED)
                }

            }

            mMishenlat = intent.getStringExtra("arrayGameslat").toString()
            mMishenlong = intent.getStringExtra("arrayGameslong").toString()
            mMishenremarks = intent.getStringExtra("arrayGamesremarks").toString()
            MishenId = intent.getStringExtra("MishenId").toString()
            mGameId = intent.getStringExtra("GameId") as String
            imageBitmapName = intent.getStringExtra("imageBitmapName").toString()
            mPlaceMishen=intent.getStringExtra("PlaceMishen").toString()
            mTotalNumberOfMishen=intent.getStringExtra("TotalNumberOfMishen").toString()
            //imageBitmap =  intent.getStringExtra("picStr").toString()
            findViewById<TextView?>(R.id.editTextTextQwesten).text = mMishenQwesten
            // todo יש להוסיף שמעדכן את הכפתורים במה שרשום בקובץ ולר כמו שעשעתי שרשום פכפתור
            //findViewById<TextView?>(R.id.anser1useradd).text = mMishenanser1
            //findViewById<TextView?>(R.id.anser2useradd).text = mMishenanser2
            //findViewById<TextView?>(R.id.anser3useradd).text = mMishenanser3
            //findViewById<TextView?>(R.id.anser4useradd).text = mMishenanser4
            //findViewById<TextView?>(R.id.lat).text=Gameslat
            //findViewById<TextView?>(R.id.long).text=Gameslong
            //findViewById<TextView?>(R.id.remarks).text=Gamesremarks
            //findViewById<TextView?>(R.id.buttonSaveMishen).text = "עדכן משימה"
            var imagePath = this.filesDir.toString() + "/Gamefiles/" + mGameId + "/" + MishenId + ".jpg"
            if (imagePath != null) {
                this.imageBitmap = BitmapFactory.decodeFile(imagePath)
                viewImage?.setImageBitmap(this.imageBitmap)
                imageBitmap =this.imageBitmap
                }
                 mclue =intent.getStringExtra("clue").toString()
                mInstractionToMishen = intent.getStringExtra("InstractionToMishen").toString()
                 mDontShowMishenPoint = intent.getStringExtra("DontShowMishenPoint").toString()
        } else {
            //mKind = intent.getStringExtra("Kind") as String
            mGameId = intent.getStringExtra("GameId") as String
            mPlaceMishen=intent.getStringExtra("mTotalNumberOfMishen").toString()
            MishenId= UUID.randomUUID().toString()
            //   המיקום הראשוני הוא מיקום המשחק או מיקום המשימה האחרונה שהוכנס - במפה ניתן לקבל את המיקום שלך
            //if(mPlaceMishen.equals("0")){
            mMishenlat= intent.getStringExtra("lat") as String
            mMishenlong= intent.getStringExtra("long") as String
            // הזזה של מספר מטרים כדי שצייר האיקון יהיה ליוד נקודת ההתחלה ולא עליה
            mMishenlat=(mMishenlat.toDouble()+0.00000000000000).toString()
            mMishenlong=(mMishenlong.toDouble()+0.00020000000000).toString()

        }




    }

    /////////////////////////////////////////////

    //////////////////////////////////////////////////




    /////////////////////////////////////////////////////////////////////////////////////////
    fun pichrViewAddPic(view: View) {
        openGalleryForImages()
    }
/////////////////////////////////////////////////////////////////////////////////////////




/////////////////////////////////////////////////////////////////////////////////////////
    fun buttonPicFromGalery(view: View) {
        //openGallery()
        openGalleryForImages()
    }
/////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////
    fun buttonYes(view: View){
        mAnserQwestenIs.clear()
        mAnserQwestenIs.add("Yes")
        findViewById<ImageButton?>(R.id.imageButtonYes).setBackgroundColor(Color.RED)
        findViewById<ImageButton?>(R.id.imageButtonNo).setBackgroundColor(Color.GRAY)
    }
/////////////////////////////////////////////////////////////////////////////////////////
    fun buttonNo(view: View){
        mAnserQwestenIs.clear()
        mAnserQwestenIs.add("No")
        findViewById<ImageButton?>(R.id.imageButtonYes).setBackgroundColor(Color.GRAY)
        findViewById<ImageButton?>(R.id.imageButtonNo).setBackgroundColor(Color.RED)
    }
/////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////
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
/////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////////////////
    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveMishenButtonClicked(view: View) {
        val Qwesten: TextView = findViewById(R.id.editTextTextQwesten)as TextView
        //val lat: TextView = findViewById(R.id.anser4useradd)as TextView
        //val long: TextView = findViewById(R.id.anser4useradd)as TextView
        //val remarks: TextView = findViewById(R.id.anser4useradd)as TextView

        if (Qwesten.text.isEmpty() || mAnserQwestenIs.size==0)
        {
            MsgBox.showMessageDialog(this,getString(R.string.create_mishen_kind_1_1_radiobutten_Need_TO_Add_Text))
            return
        }

        if(mFlegSetMishenLocationInMap==false)
        {
            buttonChangPointOnMap(view)
            return
        }

        mmissen.addInfo(mKind,mGameId,Qwesten.text.toString() ,mMishenanser1,
            mMishenanser2,"","",mAnserQwestenIs,mMishenlat,mMishenlong,
        "",MishenId,mNewMishen,this.imageBitmapName,this.imageBitmap,mVidioPath,
            mPlaceMishen,this,mclue,mInstractionToMishen,mDontShowMishenPoint)
        mmissen.AddMishen()
        if (this.imageBitmap!=null) {
            savePic(this.imageBitmap!!)
        }
        // מחזיר עידכון כי מספר המשימות עלה ב 1
        val newPlace=(mPlaceMishen.toInt()+1)
        mPlaceMishen= newPlace.toString()
        val intent = Intent()
        intent.putExtra("mPlaceMishen", mPlaceMishen);
        setResult(RESULT_OK, intent);
        finish()
        }
/////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////
    fun AddPicFromCamera(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            capturePhoto()
        } else {
            //longToast("Sorry you're version android is not support, Min Android 6.0 (Marsmallow)")
            var a=5
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////



    /////////////////////////////////////////////////////////
    private fun show(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
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

    private fun openGallery(){

        
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, CHOOSE_PHOTO)
    }

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





    /**
    private fun renderImage(imagePath: String?){
        if (imagePath != null) {
            this.imageBitmap = BitmapFactory.decodeFile(imagePath)
            viewImage?.setImageBitmap(this.imageBitmap)

        }
        else {
            show("ImagePath is null")
        }
    }

    @SuppressLint("Range")
    private fun getImagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = contentResolver.query(uri!!, null, selection, null, null )
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }
**/
    fun savePic(imageBitmap: Bitmap) {
        //viewImage.setDrawingCacheEnabled(true)
        //viewImage.buildDrawingCache()
        //val bitmap: Bitmap = Bitmap.createBitmap(viewImage.getDrawingCache())
        val f=FileReadWriteService()
        f.writeFileOnInternalStorageMishenPic(MishenId+".jpg","Gamefiles//"+mGameId,MishenId,
            mGameId,imageBitmap ,this)
    }

/**
    @TargetApi(19)
    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        //DocumentsContract defines the contract between a documents provider and the platform.
        if (DocumentsContract.isDocumentUri(this, uri)){
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority){
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selsetion)
            }
            else if ("com.android.providers.downloads.documents" == uri.authority){
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse(
                        "content://downloads/public_downloads"), Long.valueOf(docId))
                imagePath = getImagePath(contentUri, null)
            }
        }
        else if ("content".equals(uri!!.scheme, ignoreCase = true)){
            imagePath = getImagePath(uri, null)
        }
        else if ("file".equals(uri!!.scheme, ignoreCase = true)){
            imagePath = uri!!.path
        }
        renderImage(imagePath)

    }**/
fun SaveImageViewToFolder(imageView:ImageView){
    val imageBitmap = resizePhoto(imageView.drawable.toBitmap())

    val f=FileReadWriteService()
    f.writeFileOnInternalStorageMishenPic(MishenId+".jpg","Gamefiles//"+mGameId,MishenId,
        mGameId,imageBitmap ,this)

}

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
            var imageView=findViewById<ImageView>(R.id.imageView2)
            imageView.setImageURI(imageUri)
            imageBitmap=imageView.drawable.toBitmap()


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CAPTURE_PHOTO ->
                if (resultCode == RESULT_OK) {
                    this.imageBitmap = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(uri!!))
                    viewImage!!.setImageBitmap(this.imageBitmap)
                    //savePic(this.imageBitmap!!)
                }
            CHOOSE_PHOTO ->
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        SetImageFromGaleryToImageView(data)
                        //handleImageOnKitkat(data)
                    }
                }
            GetGPS ->
                if (resultCode == RESULT_OK) {
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
// Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    // This function is called when the user accepts or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when the user is prompt for permission.

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun resizePhoto(bitmap: Bitmap): Bitmap {


        val w = bitmap.width
        val h = bitmap.height
        val aspRat = w / h
        val W = 400
        val H = W * aspRat
        val b = Bitmap.createScaledBitmap(bitmap, W, H, false)

        return b


    }

}

