import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.example.test2.R


class MsgBox : Application() {

    companion object {
        @SuppressLint("MissingInflatedId")
        fun showMessageDialog(context: Context, message: String) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_message, null)
            val messageTextView = dialogView.findViewById<TextView>(R.id.messageTextView)
            messageTextView.text = message

            val dialogBuilder = AlertDialog.Builder(context)
                .setView(dialogView)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }

            val dialog = dialogBuilder.create()
            dialog.show()
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize other components or perform additional setup here
    }
}

/*package com.example.test2

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MsgBox {
    fun show(applicationContext:Context,msseg:String,Title:String){
        val builder = AlertDialog.Builder(applicationContext)
        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()

        }
        builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
//or
        builder.setPositiveButton(android.R.string.yes, positiveButtonClick)
        with(builder)
        {
            setTitle(Title)
            setCancelable(false)
            setMessage(msseg)
            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
            //setNegativeButton(android.R.string.no, negativeButtonClick)
            //setNeutralButton("Maybe", neutralButtonClick)
            setPositiveButtonIcon(applicationContext.getDrawable(android.R.drawable.ic_menu_call))
            setIcon(applicationContext.getDrawable(android.R.drawable.ic_dialog_alert))
        }

        val alertDialog = builder.create()
        alertDialog.show()

        val button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        with(button) {
            setBackgroundColor(Color.BLACK)
            setPadding(0, 0, 20, 0)
            setTextColor(Color.WHITE)
        }

    }
}*/