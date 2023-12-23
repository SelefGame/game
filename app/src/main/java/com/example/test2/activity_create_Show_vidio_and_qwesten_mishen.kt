package com.example.test2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.webkit.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


class activity_create_Show_vidio_and_qwesten_mishen : AppCompatActivity() {
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
    var mPlaceMishen=""
    var mAnserQwestenIs= ArrayList<String>()


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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_show_vidio_and_qwesten_mishen)


        //val myYouTubeVideoUrl = "https://www.youtube.com/embed/XUTXU6fy94E"
       /* val myYouTubeVideoUrl = "https://runinggame.pagekite.me/api/video/video.mp4"
        val dataUrl = "<html>" +
                "<body>" +
                "<h2>Video From YouTube</h2>" +
                "<br>" +
                "<iframe width=\"560\" height=\"315\" src=\"" + myYouTubeVideoUrl + "\" frameborder=\"0\" allowfullscreen/>" +
                "</body>" +
                "</html>"
        */


        val myWebView = findViewById<WebView>(R.id.WebView1)

        val webSettings = myWebView.settings

        webSettings.javaScriptEnabled = true
        myWebView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        myWebView.settings.loadWithOverviewMode = true
        myWebView.settings.useWideViewPort = true

        //myWebView.loadData(dataUrl, "text/html", "utf-8")

        myWebView.loadUrl("file:///android_asset/videoStreem.html")

        /*val WebView1= findViewById<WebView>(R.id.WebView1)
        val webSettings: WebSettings = WebView1.getSettings()
        webSettings.javaScriptEnabled = true
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        WebView1.getSettings().setJavaScriptEnabled(true);
        WebView1.getSettings().setDomStorageEnabled(true);
        WebView1.getSettings().setMediaPlaybackRequiresUserGesture(false);
        //WebView1.loadUrl("file:///android_asset/videoStreem.html")


        // URL of the video to be played
        //val url =  "https://firebasestorage.googleapis.com/v0/b/videodownloader-2847e.appspot.com/o/demo.mp4?alt=media&token=09adc942-0e7f-43b9-a7c2-54251e8d3776"
        val url =  "file:///android_asset/videoStreem.html"

        // Get the VideoView and MediaController objects
        val videoView = findViewById<VideoView>(R.id.videoView)
        val mediaController = MediaController(this)

        // Set the video URI for the VideoView
        videoView.setVideoURI(Uri.parse(url))

        // Set the MediaController for the VideoView
        videoView.setMediaController(mediaController)

        // Set the anchor view for the MediaController
        mediaController.setAnchorView(videoView)

        // Start playing the video
        videoView.start()

        // Get the download button
        val downloadButton = findViewById<Button>(R.id.download)

        // Set the onClickListener for the download button
        downloadButton.setOnClickListener {
            // Get the DownloadManager
            val download = applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            // Get the URI of the video
            val videoUri = Uri.parse(url)

            // Create a DownloadManager request for the video
            val getVideo = DownloadManager.Request(videoUri)

            // Set the visibility of the download notification
            getVideo.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

            // Enqueue the request in the DownloadManager
            download.enqueue(getVideo)

            // Show a Toast message indicating that the download has started
            Toast.makeText(this, "Download Started", Toast.LENGTH_LONG).show()
        }*/
    }



    fun AddPicFromCamera(view: View){
        capturePhoto()
    }

    fun AddPicFromgalery(view: View){
        openGalleryForImages()
    }

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

    fun buttonChangPointOnMap(view: View)
    {
        val intent = Intent(this, Maps_select_update_point_for_Mishen::class.java)
        startActivityForResult(intent, GetGPS)

    }

    private fun openGalleryForImages() {

        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "video/*"
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
            intent.type = "video/*"
            startActivityForResult(intent, CHOOSE_PHOTO);
        }

    }



    private fun openGallery(){


        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "video/*"
        startActivityForResult(intent, CHOOSE_PHOTO)
    }
    /**    private fun renderImage(imagePath: String?){
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
        f.writeFileOnInternalStorageMishenPic(mMishenId+".jpg","Gamefiles//"+mGameId,mMishenId,
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
            var imageView=findViewById<ImageView>(R.id.imageView)
            imageView.setImageURI(imageUri)
            imageBitmap=imageView.drawable.toBitmap()


        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>
                                            , grantedResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
        when(requestCode){
            1 ->
                if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
                    PackageManager.PERMISSION_GRANTED){
                    openGallery()
                }else {
                    show("Unfortunately You are Denied Permission to Perform this Operataion.")
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CAPTURE_PHOTO ->

                if (resultCode == Activity.RESULT_OK) {

                   /* this.imageBitmap = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(uri!!))
                    viewImage!!.setImageBitmap(this.imageBitmap)*/
                    //savePic(this.imageBitmap!!)
                }
            CHOOSE_PHOTO ->
                if (resultCode == RESULT_OK) {
                    /*if (Build.VERSION.SDK_INT >= 19) {
                        SetImageFromGaleryToImageView(data)
                        //handleImageOnKitkat(data)
                    }*/
                    val selectedImageUri = data!!.data


                    // OI FILE Manager
                    //val filemanagerstring = selectedImageUri!!.path

                    // MEDIA GALLERY
                    val xxx= RealPathUtil.getRealPath (getApplicationContext(), selectedImageUri)
                    // MEDIA GALLERY
                    val selectedImagePath = getRealPathFromUri(this,selectedImageUri)
                    if (selectedImagePath != null) {
                        val intent = Intent(
                            this,
                            activity_create_Show_vidio_and_qwesten_mishen::class.java
                        )
                        intent.putExtra("path", selectedImagePath)
                        startActivity(intent)
                    }
                }
            GetGPS ->
                if (resultCode == Activity.RESULT_OK) {
                    data?.apply {
                        mMishenlat = getStringExtra("lat")!!
                        mMishenlong = getStringExtra("long")!!
                        // do something
                    }

                }

        }


    }

    // UPDATED!
    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor? = contentResolver.query(uri!!, projection, null, null, null)
        return if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            val column_index: Int = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } else null
    }


    fun getRealPathFromUri(context: Context, contentUri: Uri?): String? {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri!!, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }



    //////////////////////////////////////////////////////////




}



////////////////////// get path ////////////////////
fun getPath(context: Context, uri: Uri): String? {
    val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

    // DocumentProvider
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            val type = split[0]
            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }

            // TODO handle non-primary volumes
        } else if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
            )
            return getDataColumn(context, contentUri, null, null)
        } else if (isMediaDocument(uri)) {
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
    } else if ("content".equals(uri.scheme, ignoreCase = true)) {
        return getDataColumn(context, uri, null, null)
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }
    return null
}

/**
 * Get the value of the data column for this Uri. This is useful for
 * MediaStore Uris, and other file-based ContentProviders.
 *
 * @param context       The context.
 * @param uri           The Uri to query.
 * @param selection     (Optional) Filter used in the query.
 * @param selectionArgs (Optional) Selection arguments used in the query.
 * @return The value of the _data column, which is typically a file path.
 */
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
        cursor = uri?.let {
            context.getContentResolver().query(
                it, projection, selection, selectionArgs,
                null
            )
        }
        if (cursor != null && cursor.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(column_index)
        }
    } finally {
        cursor?.close()
    }
    return null
}


/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is DownloadsProvider.
 */
fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is MediaProvider.
 */
fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

fun getMultiPartBody(key: String?, mMediaUrl: String?): MultipartBody.Part? {
    return if (mMediaUrl != null) {
        val file = File(mMediaUrl)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        MultipartBody.Part.createFormData(key, file.name, requestFile)
    } else {
        MultipartBody.Part.createFormData(key, "")
    }
}
////////////////////////////////////////////////////



