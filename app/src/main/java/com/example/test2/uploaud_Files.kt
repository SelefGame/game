package com.example.test2

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class uploaud_Files : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploaud_files)


        val myWebView = findViewById<WebView>(R.id.WebView)

        val webSettings = myWebView.settings

        webSettings.javaScriptEnabled = true
        myWebView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        myWebView.settings.loadWithOverviewMode = true
        myWebView.settings.useWideViewPort = true

        //myWebView.loadData(dataUrl, "text/html", "utf-8")

        myWebView.loadUrl("file:///android_asset/uploadVideo.html")



    }

        fun splitFile(f: File) {
            var partCounter = 1 //I like to name parts from 001, 002, 003, ...
            //you can change it to 0 if you want 000, 001, ...
            val sizeOfFiles = 1024 * 1024 // 1MB
            val buffer = ByteArray(sizeOfFiles)
            val fileName: String = f.getName()
            FileInputStream(f).use { fis ->
                BufferedInputStream(fis).use { bis ->
                    var bytesAmount = 0
                    while (bis.read(buffer).also { bytesAmount = it } > 0) {
                        //write each chunk of data into separate file with different number in name
                        val filePartName =
                            String.format("%s.%03d", fileName, partCounter++)
                        val newFile = File(f.getParent(), filePartName)
                        FileOutputStream(newFile).use { out -> out.write(buffer, 0, bytesAmount) }
                    }
                }
            }
        }


       // fun main(args: Array<String>) {
       //     splitFile(File("D:\\destination\\myFile.mp4"))
        //}
   // }
}