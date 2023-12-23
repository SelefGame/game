package com.example.test2

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.util.*

class create_mishen_kind_2_2_yes_no : AppCompatActivity() {
    var mKind="2.2"
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
    var mVidioPath=""
    var mpicherSplitNumber=""
    var mVideoSplitNumber=""
    var mGamelat=""
    var mGamelong=""
    var mclue = ""
    var mInstractionToMishen = ""
    var mDontShowMishenPoint = ""

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

    lateinit var videoView: VideoView
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001

    var vFilename: String = ""
    lateinit var mAdView : AdView

    private val TAG = "VideoPickerActivity"

    private val SELECT_VIDEOS = 1
    private val CAPTURE_VIDEOS = 2
    private val SELECT_VIDEOS_KITKAT = 1
    private var selectedVideos: List<String?>? = null



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_mishen_kind_2_2_yes_no)

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





        checkPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            create_mishen_kind_2_2_yes_no.STORAGE_PERMISSION_CODE
        )
        checkPermission(
            Manifest.permission.CAMERA,
            create_mishen_kind_2_2_yes_no.CAMERA_PERMISSION_CODE
        )

        val locationTrack = LocationTrack(this)
        if (locationTrack.canGetLocation()) {
            mMishenlat = locationTrack.getLatitude().toString()
            mMishenlong = locationTrack.getLongitude().toString()

        } else {
            locationTrack.showSettingsAlert()
        }


        videoView = findViewById(R.id.VideoView);
        videoView.setOnClickListener {
            if(mVidioPath.length==0){
                if (Build.VERSION.SDK_INT < 19) {
                    val intent = Intent()
                    intent.type = "video/mp4"
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "Select videos"),
                        SELECT_VIDEOS
                    )
                } else {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.type = "video/mp4"
                    startActivityForResult(intent, SELECT_VIDEOS_KITKAT)
                }
            }
            val isPlaying = videoView.isPlaying
            if (isPlaying) {
                videoView.pause()
            } else {
                videoView.start()
            }
        }

        mNewMishen = intent.getBooleanExtra("NewMishen", true)
        if (mNewMishen == false) {
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
            mTotalNumberOfMishen=intent.getStringExtra("TotalNumberOfMishen").toString()
            mPlaceMishen=intent.getStringExtra("PlaceMishen").toString()
            mVidioPath=intent.getStringExtra("VidioPath").toString()
            if (!mVidioPath.equals(""))
            {
                if (mVidioPath!=null){
                    videoView!!.setVideoPath(filesDir.toString()+"/Gamefiles/"+mVidioPath)
                    videoView!!.requestFocus()
                    videoView!!.start()}
            }
            mclue =intent.getStringExtra("clue").toString()
            mInstractionToMishen = intent.getStringExtra("InstractionToMishen").toString()
            mDontShowMishenPoint = intent.getStringExtra("DontShowMishenPoint").toString()
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
                //viewImage?.setImageBitmap(this.imageBitmap)
                imageBitmap =this.imageBitmap
            }
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

    fun AddVideoFromCamera(view: View) {

        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, CAPTURE_VIDEOS)
    }


////////////////////////////////////////כפתור שמור משימה////////////////////////////////////////////////
    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveMishenButtonClicked(view: View) {
        val Qwesten: TextView = findViewById(R.id.editTextTextQwesten)as TextView

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


        val DestPath="/Gamefiles/"+mGameId+"/"+MishenId+".mp4"
        if (!mVidioPath.equals("") ){

            val f=FileReadWriteService()
            f.CopyFile(mVidioPath,DestPath,this)
        }
        mVidioPath=mGameId+"/"+MishenId+".mp4"
        mmissen.addInfo(mKind,mGameId,Qwesten.text.toString() ,mMishenanser1,
            mMishenanser2,"","",mAnserQwestenIs,mMishenlat,mMishenlong,
            "",MishenId,mNewMishen,this.imageBitmapName,this.imageBitmap,mVidioPath,
            mPlaceMishen,this,mclue,mInstractionToMishen,mDontShowMishenPoint)
        mmissen.AddMishen()

        // מחזיר עידכון כי מספר המשימות עלה ב 1
        val newPlace=(mPlaceMishen.toInt()+1)
        mPlaceMishen= newPlace.toString()
        val intent = Intent()
        intent.putExtra("mPlaceMishen", mPlaceMishen);
        setResult(RESULT_OK, intent);
        finish()
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////הוספת וידאו מהגלריה למסך//////////
    fun AddVideoFromgalery(view: View) {
        if (Build.VERSION.SDK_INT < 19) {
            val intent = Intent()
            intent.type = "video/mp4"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select videos"),
                SELECT_VIDEOS
            )
        } else {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.type = "video/mp4"
            startActivityForResult(intent, SELECT_VIDEOS_KITKAT)
        }
    }



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




//////////////////////////////////////////////////////////////////////////////////////////////

    /*   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
           super.onActivityResult(requestCode, resultCode, data)
           if (resultCode == RESULT_OK) {
               selectedVideos = getSelectedVideos(requestCode, data!!)
               // Log.d("path", selectedVideos.toString())
               videoView!!.setVideoPath(selectedVideos!![0])
               videoView!!.requestFocus()
               videoView!!.start()
               if (selectedVideos!!.size > 1) {
                   videoView2?.setVideoPath(selectedVideos!![1])
                   videoView2?.requestFocus()
                   videoView2?.start()
               }
           }
       }*/

    private fun getSelectedVideos(requestCode: Int, data: Intent): List<String?>? {
        val result: MutableList<String?> = ArrayList()
        val clipData = data.clipData
        if (clipData != null) {
            for (i in 0 until clipData.itemCount) {
                val videoItem = clipData.getItemAt(i)
                val videoURI: Uri = videoItem.uri
                val filePath = getPath(this, videoURI)
                result.add(filePath)
            }
        } else {
            val videoURI: Uri? = data.data
            // val getpathtest: RealPathUtil
            val xxx= RealPathUtil.getRealPath (getApplicationContext(), videoURI)
            val filePath = getPath(getApplicationContext(), videoURI)
            result.add(filePath)
        }
        return result
    }

    @SuppressLint("NewApi")
    fun getPath(context: Context, uri: Uri?): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)==true) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)==true) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri: Uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri) == true) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if (uri != null) {
            if ("content".equals(uri.getScheme(), ignoreCase = true)) {

                // Return the remote address
                if (uri != null) {
                    return if (isGooglePhotosUri(uri) == true) uri.getLastPathSegment() else getDataColumn(
                        context,
                        uri,
                        null,
                        null
                    )
                }
            } else if ("file".equals(uri.getScheme(), ignoreCase = true)) {
                if (uri != null) {
                    return uri.getPath()
                }
            }
        }
        return null
    }

    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.getContentResolver().query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            val num= cursor?.columnCount
            val ddd= cursor?.count
            val rr=cursor?.moveToFirst()
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            val ff=0

            if (cursor != null && cursor.moveToFirst()) {
                val index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            if (cursor != null) cursor.close()
        }
        return null
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri?): Boolean? {
        if (uri != null) {
            return "com.android.externalstorage.documents" == uri.getAuthority()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri?): Boolean? {
        if (uri != null) {
            return "com.android.providers.downloads.documents" == uri.getAuthority()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri?): Boolean? {
        if (uri != null) {
            return "com.android.providers.media.documents" == uri.getAuthority()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean? {
        if (uri != null) {
            return "com.google.android.apps.photos.content" == uri.getAuthority()
        }
        return null
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CAPTURE_VIDEOS ->
                if (resultCode == Activity.RESULT_OK) {

                    if (resultCode == Activity.RESULT_OK) {
                        selectedVideos = getSelectedVideos(requestCode, data!!)
                        // Log.d("path", selectedVideos.toString())
                        mVidioPath= selectedVideos!![0].toString()
                        videoView!!.setVideoPath(selectedVideos!![0])
                        videoView!!.requestFocus()
                        videoView!!.start()
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        Toast.makeText(
                            this, "Video recording cancelled.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this, "Failed to record video",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            SELECT_VIDEOS ->
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        selectedVideos = getSelectedVideos(requestCode, data!!)
                        mVidioPath= selectedVideos!![0].toString()
                        // Log.d("path", selectedVideos.toString())
                        videoView!!.setVideoPath(selectedVideos!![0])
                        videoView!!.requestFocus()
                        videoView!!.start()
                        /*if (selectedVideos!!.size > 1) {
                            videoView2?.setVideoPath(selectedVideos!![1])
                            videoView2?.requestFocus()
                            videoView2?.start()
                        }*/
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
        if (requestCode == create_mishen_kind_2_2_yes_no.CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == create_mishen_kind_2_2_yes_no.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }




}





