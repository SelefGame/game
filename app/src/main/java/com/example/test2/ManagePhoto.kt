package com.example.test2

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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

class ManagePhoto(context:Context,ImageView: ImageView) {
    val REQUEST_CODE = 200
    val mContext=context


    fun GetPhotoFrpmGalerySetInImageView() {
        openGalleryForImages()
    }

    fun openGalleryForImages() {

        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            mContext.startActivity(
                Intent.createChooser(intent, "Choose Pictures")
            )
        } else { // For latest versions API LEVEL 19+
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            mContext.startActivity(intent);

        }

    }


/**
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mContext.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {

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
                var imageView = findViewById<ImageView>(R.id.ivImage)
                imageView.setImageURI(imageUri)
                val imageBitmap = imageView.drawable.toBitmap()
                val f = FileReadWriteService()
                f.writeFileOnInternalStorageMishenPic(
                    "1111" + ".jpg", "Gamefiles//" + "mGameId", "1111",
                    "mGameId", imageBitmap, this
                )


                //to get the image from the ImageView (say iv)
                var FilePath = File(imageUri.getPath());//create path from uri
                //var imagePath = getRealPathFromURI(imageUri)
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
    }**/



}


