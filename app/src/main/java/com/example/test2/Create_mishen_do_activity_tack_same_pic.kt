package com.example.test2


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import java.io.File
import java.io.FileOutputStream


class Create_mishen_do_activity_tack_same_pic : AppCompatActivity() {
    val REQUEST_CODE = 200
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_mishen_do_activity_tack_same_pic)
        val list = listOf<String>(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        // Initialize a new instance of ManagePermissions class
        //managePermissions = ManagePermissions(this,list,PermissionsRequestCode)
        //managePermissions.checkPermissions()
        var imageView=findViewById<ImageView>(R.id.ivImage)
        var ManagePhoto=ManagePhoto(this,imageView)
        ManagePhoto.GetPhotoFrpmGalerySetInImageView()
        openGalleryForImages()
    }

    private fun openGalleryForImages() {

        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Pictures")
                , REQUEST_CODE
            )
        }
        else { // For latest versions API LEVEL 19+
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE);
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){

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
                val docId = DocumentsContract.getDocumentId(imageUri)
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id

                //ivImage.setImageURI(imageUri)
                var image=findViewById<ImageView>(R.id.ivImage)
                image.setImageURI(imageUri)
                val imageBitmap = image.drawable.toBitmap()
                val f=FileReadWriteService()
                f.writeFileOnInternalStorageMishenPic("1111"+".jpg","Gamefiles//"+"mGameId","1111",
                    "mGameId",imageBitmap ,this)


                //to get the image from the ImageView (say iv)
                var FilePath= File(imageUri.getPath());//create path from uri
                var imagePath = getRealPathFromURI(imageUri)
                //var imagePath1 = getImagePath(
                //    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                //    selsetion)
                //renderImage(FilePath.toString())
                //final String[] split = file.getPath().split(":");//split the path.
                //filePath = split[1]
                //to get the image from the ImageView (say iv)

                var outStream: FileOutputStream? = null
                val sdCard: File = Environment.getExternalStorageDirectory()
                val dir = File(sdCard.getAbsolutePath() + "/YourFolderName")
                dir.mkdirs()
                val fileName = String.format("%d.jpg", System.currentTimeMillis())
                val outFile = File(dir, fileName)
                outStream = FileOutputStream(outFile)
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
                outStream.flush()
                outStream.close()
            }
        }
    }


    /// todo לבדוק את השמירה ופענוח של הקובץ לתמונה יש את זה בהרבה מקמות וצריך אחידות בקוד
    private fun renderImage(imagePath: String?){
        var imageBitmap: Bitmap? =null
        if (imageBitmap != null) {
            imageBitmap = BitmapFactory.decodeFile(imagePath)
            //viewImage?.setImageBitmap(this.imageBitmap)
            savePic(imageBitmap,"12345678")
        }
        else {
           var a="ImagePath is null"
        }
    }


    fun savePic(imageUri: Bitmap, mMishenId: String) {
        //viewImage.setDrawingCacheEnabled(true)
        //viewImage.buildDrawingCache()
        //val bitmap: Bitmap = Bitmap.createBitmap(viewImage.getDrawingCache())
        val f=FileReadWriteService()
        var mGameId="3333"
        f.writeFileOnInternalStorageMishenPic(mMishenId+".jpg","Gamefiles//"+mGameId,mMishenId,
            mGameId,imageUri ,this)
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

    fun getRealPathFromURI(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        val column_index: Int = cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

}
