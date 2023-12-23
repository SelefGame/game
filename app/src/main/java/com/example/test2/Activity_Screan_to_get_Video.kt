package com.example.test2

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.CursorLoader


class Activity_Screan_to_get_Video : AppCompatActivity() {
    private var videoView: VideoView? = null
    private  var videoView2:VideoView? = null
    private var btn: Button? = null
    private val TAG = "VideoPickerActivity"

    private val SELECT_VIDEOS = 1
    private val SELECT_VIDEOS_KITKAT = 1

    private var selectedVideos: List<String?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screan_to_get_video)
        //btn = findViewById<View>(R.id.btn) as Button
        videoView = findViewById<View>(R.id.vv) as VideoView
        videoView2 = findViewById<View>(R.id.vv2) as VideoView



    }

    fun SelectPic(view: View) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
    }

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
}



