package com.example.test2

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.example.test2.SocketHandler.mSocket
import org.json.JSONObject
import kotlin.system.exitProcess

class show_exist_games_and_mishens_to_play_save_game_to_play : AppCompatActivity() {
    var mUserId=""
    var GameId=""
    var arrayGame=""
    var mlongitude=""
    var mlatitude=""
    var ShowProgressBar=true
    // משתנים להצגת סטאטוס בהודעות אנדואיד בטלפון להעלת קבצים
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private val channelId = "Create_Or_Update_Game"
    private val description = "running game Notification"


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_exist_games_and_mishens_to_play_save_game_to_play)
        mUserId = intent.getStringExtra("mUserId").toString()
        GameId= intent.getStringExtra("GameId").toString()
        arrayGame= intent.getStringExtra("arrayGame").toString()
        mlongitude= intent.getStringExtra("Long").toString()
        mlatitude= intent.getStringExtra("Lat").toString()
        val create_SelfGame: CardView = findViewById(R.id.create_SelfGame)

        //  לחיצה על שמירה של משחק אישי
        create_SelfGame.setOnClickListener{
            // השרת פותח רשומה ברשימת משחקים של המשתמש ומחזיר את תוכן המשחק למשתמש
            val a= GameId
            val b=mUserId
            mSocket.emit("StartSalfGame",mUserId,GameId)
            // קבלת התוצאה מהשרת על שמירת המשחק. השרת מחזיר את המשחק עם כל הפרטים לשמירה למיקרה שלא יהיה אינטארנט
            mSocket.on("StartSalfGame") { args ->
                if (args[0] != null) {
                    val res = args[0]
                    if (res==true){
                        // שמירה של המשחק והנתונים בלי תמונות למקרה שלא יהיה אינטארנט
                        // הנתונים של המשחק הדיע מהמסך הקודם בו בחר את המשחק ולכן רק צריך לשמור אותם
                        SaveGame(arrayGame)
                        val intent = Intent(this, StartPlayGame::class.java)
                        intent.putExtra("UserId",mUserId)
                        intent.putExtra("GameId",GameId)
                        intent.putExtra("Long",mlongitude)
                        intent.putExtra("Lat",mlatitude)
                        //שומר את הDB של הניבול משחק לעבודה ללא אינטארנט
                        var GameDBJsonObjsect=args[1]
                        val f=FileReadWriteService()
                        f.writeFileOnInternalStorageGameWatingToPlay(GameDBJsonObjsect.toString(),GameId,GameId+"_GamesUserWantsToPlay.txt",this)
                        // צריך להחליט עם לחזור למפה או להקפיץ אותו לתפריט התחיל  לשחק
                        //startActivity(intent)

                    }
                    else
                    {
                        // במידה והיתה בעיה בקבלת המשחק מהשרת לאחר הבחירה מודיע למשתמש שנכשל  הסיבה הסבירה שבחר כבר את המשחק
                        runOnUiThread {
                            val builder = AlertDialog.Builder(this)
                            val positiveButtonClick = { dialog: DialogInterface, which: Int ->
                                Toast.makeText(applicationContext,
                                    android.R.string.no, Toast.LENGTH_SHORT).show()

                            }
                            builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))

                            builder.setPositiveButton(android.R.string.yes, positiveButtonClick)
                            with(builder)
                            {
                                setTitle("המשחק לא נירשם")
                                setCancelable(false)
                                setMessage("לא ניתן להוסיף את המשחק לרשימת המשחקים - בדוק שהוא כבר קיים ברשימה")
                                setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
                                //setNegativeButton(android.R.string.no, negativeButtonClick)
                                //setNeutralButton("Maybe", neutralButtonClick)
                                setPositiveButtonIcon(resources.getDrawable(android.R.drawable.ic_menu_call, theme))
                                setIcon(resources.getDrawable(android.R.drawable.ic_dialog_alert, theme))
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
                    }
                }
            }

        }


        mSocket.on("MishenFilesReciveFromServerForGame") { args ->
            if (args[0] != null) {

                val res = args[0] as Boolean
                if( res == true){
                    ShowProgressBar=false
                    runOnUiThread {
                        //progressDialog.hide()
                        Toast.makeText(this, "the game dwonloadd secseesfuly", Toast.LENGTH_LONG).show()
                       //todo need to get the enser and print secsess upload


                        builder.setContentText("DWONLOADING completed")
                            // Removes the progress bar
                            .setProgress(0,0,false);
                        notificationManager.notify(12345,builder.build());



                    }

                }
                else
                {
                    // todo need the msg error to user
                    Toast.makeText(this, "Error Error the game didnt upload", Toast.LENGTH_LONG).show()
                }

            }

        }


        ///////////// קבלת תמונות מהשרת במידה ויש במשחק תמונות
        mSocket.on("SendPicherFromServerToUser") { args ->
            if (args[0] != null) {
                // val res = args[0]
                val f=FileReadWriteService()
                val res=args[0] as JSONObject
                val buf = res.get("PicherAsString") as ByteArray
                val FileName=res.getString("file")
                if(FileName.contains(".jpg")) {
                    val Bitmap = f.Base64ToPicher(String(buf))
                    // val picStr=Base64.getDecoder().decode(buf)
                    //val Bitmap=byteArrayToBitmap(picStr)
                    if (Bitmap != null) {
                        f.writePicherOnInternalStorage(FileName, GameId, Bitmap!!, this)
                        val ListFilesInDeir = f.GetListFilesInFolder(
                            this.filesDir.toString() + "/GameWatingToStartPlay/" + GameId + "/"  )
                        mSocket.emit(
                            "MishenFilesReciveFromServerForGame",
                            mUserId,
                            GameId,
                            ListFilesInDeir
                        )
                    }
                }
                else{
                        if(FileName.contains(".mp4")) {
                            f.writeFileOnInternalStorageGameWatingToPlay( String(buf), GameId, FileName, this)
                            val ListFilesInDeir = f.GetListFilesInFolder(
                                this.filesDir.toString() + "/GameWatingToStartPlay/" + GameId + "/",)
                            mSocket.emit("MishenFilesReciveFromServerForGame",mUserId,GameId,ListFilesInDeir)
                        }
                    }
                runOnUiThread {
                    //MainTextView.text = counter.toString()
                }
            }
        }



    }





    fun ShowProgressBarUploadFiles(){
        val intent = Intent(this, LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationChannel = NotificationChannel(channelId, description, NotificationManager .IMPORTANCE_LOW)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this,channelId).setContentTitle("DWONLOAD GAME").setContentText("Running game").setSmallIcon(R.drawable.eye).setLargeIcon(
                BitmapFactory.decodeResource(this.resources, R.drawable
                    .ic_launcher_background)).setContentIntent(pendingIntent)
        }
        else{
            builder = Notification.Builder(this).setContentTitle("DWONLOAD GAME").setContentText("Running game").setSmallIcon(R.drawable.eye).setLargeIcon(
                BitmapFactory.decodeResource(this.resources, R.drawable
                    .ic_launcher_background)).setContentIntent(pendingIntent)
        }
        notificationManager.notify(12345, builder.build())

        Thread(Runnable {
            if (ShowProgressBar ==false){
                builder.setContentText("DWONLOADING completed")
                    // Removes the progress bar
                    .setProgress(0,0,false);
                notificationManager.notify(12345,builder.build());

                exitProcess(0)
            }
            var primaryProgressStatus=0
            while (primaryProgressStatus < 100) {
                if (primaryProgressStatus>98){
                    primaryProgressStatus=50}
                primaryProgressStatus += 1
                builder.setProgress(100, primaryProgressStatus, false);
                // Displays the progress bar for the first time.
                notificationManager.notify(12345, builder.build());
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }


            }
        }).start()
    }

    fun SaveGame(arrayGame:String){
        ShowProgressBar=true
        ShowProgressBarUploadFiles()
        val f=FileReadWriteService()
        val jsonObj = JSONObject(arrayGame)
        f.writeFileOnInternalStorageGame(GameId+".txt","GameWatingToStartPlay",jsonObj,this)

        //מכין את הרשימה על הקבצים שיש אצל המשתמש כדי שהשרת ישלח מה שחסר
        val ListFilesInDeir = f.GetListFilesInFolder(
            this.filesDir.toString() + "/GameWatingToStartPlay/" + GameId + "/")
        mSocket!!.emit("GetGamesFromDBbyGameId",GameId,ListFilesInDeir)
    }



}

