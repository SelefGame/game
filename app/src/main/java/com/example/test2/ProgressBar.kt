package com.example.test2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView


class ProgressBar : AppCompatActivity() {
    var isStarted = false
    var progressStatus = 0
    var handler: Handler? = null
    var secondaryHandler: Handler? = Handler()
    var primaryProgressStatus = 0
    var secondaryProgressStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_bar)
        val progressBarHorizontal =findViewById<android.widget.ProgressBar>(R.id.progressBarHorizontal)
        val textViewHorizontalProgress=findViewById<TextView>(R.id.textViewHorizontalProgress)
        handler = Handler(Handler.Callback {
            if (isStarted) {
                progressStatus++
            }
            progressBarHorizontal.progress = progressStatus
            textViewHorizontalProgress.text = "${progressStatus}/${progressBarHorizontal.max}"
            handler?.sendEmptyMessageDelayed(0, 100)


            true
        })

        handler?.sendEmptyMessage(0)

        primaryProgressStatus = 0
        secondaryProgressStatus = 0

        Thread(Runnable {
            while (primaryProgressStatus < 100) {
                primaryProgressStatus += 1

                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }


            }
        }).start()
    }


}