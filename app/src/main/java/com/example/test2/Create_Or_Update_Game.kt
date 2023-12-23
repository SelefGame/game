package com.example.test2

import MsgBox
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import io.socket.client.Socket
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.util.*
import kotlin.system.exitProcess


class Create_Or_Update_Game: AppCompatActivity(){
    val NewGameAdd=333

    var arrayplaceName = kotlin.arrayOfNulls<String>(0)
    var arrayGamesDescription = kotlin.arrayOfNulls<String>(0)
    var arrayGamesId = kotlin.arrayOfNulls<String>(0)
    var arrayGameSubject = kotlin.arrayOfNulls<String>(0)
    var arrayGameKind = kotlin.arrayOfNulls<String>(0)
    var arrayGamelat = kotlin.arrayOfNulls<String>(0)
    var arrayGamelong = kotlin.arrayOfNulls<String>(0)
    val mContext: Activity =this
    var mUserId=""
    var numberGames=0
    var ListVewLongKlickIndex=-1
    var mSocket: Socket? = null
    var arrayClue =  kotlin.arrayOfNulls<String>(0)
    var arrayInstractionToMishen =  kotlin.arrayOfNulls<String>(0)
    var arrayDontShowMishenPoint =  kotlin.arrayOfNulls<String>(0)

    lateinit var progressDialog: ProgressDialog
    lateinit var mAdView : AdView
    // משתנים להצגת סטאטוס בהודעות אנדואיד בטלפון להעלת קבצים
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationManager: NotificationManager
    lateinit var builder: Notification.Builder
    private val channelId = "Create_Or_Update_Game"
    private val description = "running game Notification"
    //private val description = "Test Notification"

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.test2.R.layout.create_or_update_game)




        mUserId= intent.getStringExtra("UserId") as String
        mSocket = SocketHandler.getSocket()
        SendAllFilesToServerFromDirectory(filesDir.toString()+"/GamesInUploudProsses/")
        //ShowProgressBarUploadFiles()
        var longitude:Double=0.0
        var latitude:Double=0.0

        fun <String> append(arr: Array<String>, element: String): Array<String?> {
            val array = arr.copyOf(arr.size + 1)
            array[arr.size] = element
            return array
        }

        ////////מתחיל את הפרסומת של גוגל/////////////////////////////////////////
        fun StartGoogleAddOns(){
            var adView = AdView(this)
            //adView.setAdSize(AdSize(300, 50))
            // for test -
            adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
            // prodaction
            //adView.adUnitId = "ca-app-pub-2330011334188307/7965566378"
            //ca-app-pub-2330011334188307/7965566378
            MobileAds.initialize(this) {}

            mAdView = findViewById(R.id.adView1)
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
        }

        StartGoogleAddOns()


        fun GetDraftGemsFromPhone(){

            val directory = File(this.filesDir, "Gamefiles")
            val files: Array<File> = directory.listFiles()
            for (i in files.indices) {
                //Log.d("Files", "FileName:" + files[i].getName())
                if(files[i].isFile && files[i].toString().contains(".jpg")==false ) {
                    val f = FileReadWriteService()
                    val jsonStr = f.readFileOnInternalStorage(files[i].getName(), "Gamefiles", this)
                    val jsonObj = JSONObject(jsonStr)
                    try {
                        numberGames+=1
                        val placeName = jsonObj.getString("PlaceGame")
                        val descripthanGame = jsonObj.getString("GameDescription")
                        val GameId = jsonObj.getString("GameId")
                        val GameSubject = jsonObj.getString("Gamesubject")
                        val GameKind = jsonObj.getString("GameKind")
                        val Gamelat = jsonObj.getString("placeLat")
                        val Gamelong = jsonObj.getString("placeLong")
                        val Clue= jsonObj.getString("clue")
                        val InstractionToMishen= jsonObj.getString("InstractionToMishen")
                        val DontShowMishenPoint= jsonObj.getString("DontShowMishenPoint")

                        var namesList = arrayplaceName.toMutableList()
                        namesList.add(placeName)
                        arrayplaceName = namesList.toTypedArray()
                        append(arrayplaceName, placeName)

                        namesList = arrayGamesDescription.toMutableList()
                        namesList.add(descripthanGame)
                        arrayGamesDescription = namesList.toTypedArray()
                        append(arrayGamesDescription, descripthanGame)

                        namesList = arrayGamesId.toMutableList()
                        namesList.add(GameId)
                        arrayGamesId = namesList.toTypedArray()
                        append(arrayGamesId, GameId)

                        namesList = arrayGameSubject.toMutableList()
                        namesList.add(GameSubject)
                        arrayGameSubject = namesList.toTypedArray()
                        append(arrayGameSubject, GameSubject)

                        namesList = arrayGameKind.toMutableList()
                        namesList.add(GameKind)
                        arrayGameKind = namesList.toTypedArray()
                        append(arrayGameKind, GameKind)

                        namesList = arrayGamelat.toMutableList()
                        namesList.add(Gamelat)
                        arrayGamelat = namesList.toTypedArray()
                        append(arrayGamelat, Gamelat)

                        namesList = arrayGamelong.toMutableList()
                        namesList.add(Gamelong)
                        arrayGamelong = namesList.toTypedArray()
                        append(arrayGamelong, Gamelong)

                        namesList = arrayClue.toMutableList()
                        namesList.add(Clue)
                        arrayClue = namesList.toTypedArray()
                        append(arrayClue, Clue)

                        namesList = arrayInstractionToMishen.toMutableList()
                        namesList.add(InstractionToMishen)
                        arrayInstractionToMishen = namesList.toTypedArray()
                        append(arrayInstractionToMishen, InstractionToMishen)


                        namesList = arrayDontShowMishenPoint.toMutableList()
                        namesList.add(DontShowMishenPoint)
                        arrayDontShowMishenPoint = namesList.toTypedArray()
                        append(arrayDontShowMishenPoint, DontShowMishenPoint)



                    } catch (ex: Exception) {
                        //your error handling code here
                        //here, consider adding Log.e("SmsReceiver", ex.localizedMessage)
                        //this log statement simply prints errors to your android studio terminal and will help with debugging, alternatively leave it out
                        MsgBox.showMessageDialog(this,"Error Error - ned to check : "+ ex.toString())


                    }
                }

            }
         }

        GetDraftGemsFromPhone()


        var imageId = arrayOf<Int>()
        var Index=0
        val listView = findViewById<ListView>(R.id.listView)
        if (arrayGamesDescription.size>0){
            var mName:Array<String> =arrayGameSubject as Array<String>
            val mDescription:Array<String> = arrayGamesDescription as Array<String>
            for (item in arrayGameKind) {
                if (item.equals("kids")  ) {
                    imageId+= R.drawable.kids
                    //mGamesKind[Index] =getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_kind4_LableKindDescription)
                }
                if (item.equals("adolet")) {
                    imageId += R.drawable.femely
                    //mGamesKind[Index] = getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_YesNo_picher_LableKindDescription)
                }
                if (item.equals("old peple")) {
                    imageId += R.drawable.femely
                    //mGamesKind[Index] = getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_YesNo_picher_LableKindDescription)
                }
                if (item.equals( "smoll kids")) {
                    imageId+= R.drawable.baby
                    //mGamesKind[Index] =
                     //   getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_SCrean_New_Mishen_Q_4_Anser_LableKindDescription)
                }


                Index = Index + 1

            }
            val myListAdapter = MyListAdapter(mContext,mName ,mDescription ,imageId)
            listView.adapter = myListAdapter
            val xx=listView
            val dd=xx






        }




/////////////////////////////////////המשתמש בחר את המשחק ורוצה להוסיף או למחוק משימות עובר למסך משימות////////////////////////////////////
        listView.setOnItemClickListener(){adapterView, view, position, id ->

            val intent = Intent(this, create_new_mishen_to_game_main_screan::class.java)
            intent.putExtra("GameId",arrayGamesId[position])
            intent.putExtra("GameSubject",arrayGameSubject[position])
            intent.putExtra("lat",arrayGamelat [position])
            intent.putExtra("long",arrayGamelong[position])
            startActivity(intent)
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////

        listView.setOnItemLongClickListener(OnItemLongClickListener { arg0, arg1, pos, arg3 ->
            this.ListVewLongKlickIndex=pos
            val jj=listView
            val cc=listView.size
            val xx=listView.getChildAt(this.ListVewLongKlickIndex)
            val zz=listView.getChildAt(ListVewLongKlickIndex - listView.getFirstVisiblePosition());
            if(xx==null || pos==null ){
                val ddd=0
            }
            listView.getChildAt(ListVewLongKlickIndex - listView.getFirstVisiblePosition()).setBackgroundColor(Color.BLUE);
           // listView.getChildAt(this.ListVewLongKlickIndex).setBackgroundColor(Color.BLUE);
            true}
        )
//////////////////////////////////////////////////////////////////////////////////////////////////


        val res=mSocket!!.connected()
        if (res==false)
        {
            val a=1
        }

        mSocket!!.on("uploadFiles") { args ->
            if (args[0] != null) {
                val JsonObjRes:JSONObject
                JsonObjRes = args[0] as JSONObject
                if( !res.equals("")){
                    val path=this.filesDir.toString()+"/GamesInUploudProsses/"+JsonObjRes.getString("GameId")+"/"+JsonObjRes.getString("fileName")
                    val file = File(path)
                    val result = file.delete()
                    val a=result

            }
        }
        }

        mSocket!!.on("uploadGameToServer") { args ->
            val listView = findViewById<ListView>(R.id.listView)
            if (args[0] != null) {

                val res = args[0] as Boolean
                if( res == true){
                    runOnUiThread {
                        Toast.makeText(this, "the Json game upload secseesfuly", Toast.LENGTH_LONG)
                            .show()
                    }
                    /*
                    runOnUiThread {
                        //progressDialog.hide()
                        Toast.makeText(this, "the game upload secseesfuly", Toast.LENGTH_LONG).show()

                        if (arrayGamesDescription.size>0){
                            var mName:Array<String> =arrayGameSubject as Array<String>
                            val mDescription:Array<String> = arrayGamesDescription as Array<String>
                            for (item in arrayGameKind) {
                                if (item.equals("kids")  ) {
                                    imageId+= R.drawable.kids
                                    //mGamesKind[Index] =getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_kind4_LableKindDescription)
                                }
                                if (item.equals("adolet")) {
                                    imageId += R.drawable.femely
                                    //mGamesKind[Index] = getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_YesNo_picher_LableKindDescription)
                                }
                                if (item.equals("old peple")) {
                                    imageId += R.drawable.femely
                                    //mGamesKind[Index] = getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_YesNo_picher_LableKindDescription)
                                }
                                if (item.equals( "smoll kids")) {
                                    imageId+= R.drawable.baby
                                    //mGamesKind[Index] =
                                    //   getString(R.string.CreateNewMishen_Secend_SCrean_New_Mishen_Q_SCrean_New_Mishen_Q_4_Anser_LableKindDescription)
                                }


                                Index = Index + 1

                            }
                            val myListAdapter = MyListAdapter(mContext,mName ,mDescription ,imageId)
                            listView.adapter = myListAdapter
                            val xx=listView
                            val dd=xx
                        }

                        if (listView==null){
                            var d=0
                        }
                        else {
                            val ddddd=this.ListVewLongKlickIndex
                            val dd=listView.getChildAt(this.ListVewLongKlickIndex)
                            if(dd==null){
                                val ss=8
                            }
                            listView.getChildAt(ListVewLongKlickIndex - listView.getFirstVisiblePosition()).setBackgroundColor(Color.WHITE);
                            //listView.getChildAt(this.ListVewLongKlickIndex).setBackgroundColor(Color.WHITE);
                        }
                            //todo need to get the enser and print secsess upload
                        //todo if secsess need to delete the game fils from phon
                        // When the loop is finished, updates the notification
                        if(builder==null){
                            var a=9
                        }
                        builder.setContentText("UPLOADING completed")
                            // Removes the progress bar
                            .setProgress(0,0,false);
                        notificationManager.notify(12345,builder.build());
                    }*/

                }
                else
                {
                    // todo need the msg error to user
                    Toast.makeText(this, "Error Error the game didnt upload", Toast.LENGTH_LONG).show()
                    val jsonService= json_service
                    val jsonGames=jsonService.CreateGamesJsonFromAllFiles(this,arrayGamesId[ListVewLongKlickIndex]!!)
                    mSocket!!.emit("uploadGameToServer",arrayGamesId[ListVewLongKlickIndex],jsonGames,mUserId)
                }

            }
        }

        mSocket!!.on("disconnect") { args ->
            val res = args[0]
            mSocket!!.connected()

        }

        mSocket!!.on("reconnect") { args ->
            val res = args[0]
        }
        //////////////////////////////////////////////////////////////////////////////////

    }



/*
    fun buttonDeletGame(view: View){
        if(ListVewLongKlickIndex!=-1) {
            val DeletDir = FileReadWriteService()
            DeletDir.DeletDir(arrayGamesId[ListVewLongKlickIndex].toString(),this)
            val fileNmae=arrayGamesId[ListVewLongKlickIndex].toString()+".txt"
            DeletDir.DeletFile(fileNmae,this)
            finish();
            startActivity(getIntent());
        }
    }*/

///////////////////// מחיקת משחק עלידי המשתמש /////////////////////// ////////////////////////
    fun Butten_Delete_Game(view: View){
    if(ListVewLongKlickIndex!=-1) {
        val DeletDir = FileReadWriteService()
        DeletDir.DeletDir(arrayGamesId[ListVewLongKlickIndex].toString(),this)
        val fileNmae=arrayGamesId[ListVewLongKlickIndex].toString()+".txt"
        DeletDir.DeletFile(fileNmae,this)
        finish();
        startActivity(getIntent());
    }
    else{
        //todo יש להוסיף הןדאה במסך למשתמש
        Toast.makeText(this, "Click on item to select game", Toast.LENGTH_LONG).show()
    }
    }
///////////////////////////////////////////////////////////////////////////////////////////////




/*
    fun buttonUploadGameToServer(view: View)
    {

        //val intent = Intent(this, ProgressBar::class.java)
        //startActivity(intent)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMax(100);
        progressDialog.setMessage("Loading ...")
        progressDialog.setCancelable(false) // blocks UI interaction
        progressDialog.show()

        object: CountDownTimer(30000, 10000){
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                //add your code here
                progressDialog.dismiss()
            }
        }.start()



        if(ListVewLongKlickIndex!=-1) {
        val jsonService= json_service
       // mSocket.emit("OpenDB")
        val jsonGames=jsonService.CreateGamesJsonFromAllFiles(this,arrayGamesId[ListVewLongKlickIndex]!!)

        mSocket!!.emit("uploadGameToServer",arrayGamesId[ListVewLongKlickIndex],jsonGames,mUserId)
    }
        else{
            Toast.makeText(this, "Click on item to select game", Toast.LENGTH_LONG).show()

        }
    }
*/


/*    fun buttonGsmeOnMap(view: View)
    {
        val intent = Intent(this, MapsActivity::class.java)
        //startActivity(intent)

        startActivityForResult(intent,12)
    }
*/

///////////////////// הצגת מסך יצירת משחק חדש /////////////////////// ////////////////////////
    fun Butten_CreateNewGame(view: View){
        val intent = Intent(this, new_game_insert_main_propertis::class.java)
        //startActivity(intent)
        startActivityForResult(intent, NewGameAdd)
    }
///////////////////////////////////////////////////////////////////////////////////////////////

///////////////////// העלת משחק לשרת /////////////////////// ////////////////////////
@RequiresApi(Build.VERSION_CODES.O)
fun Butten_UploadGame(view: View){
    if(ListVewLongKlickIndex==-1)
    {
        MsgBox.showMessageDialog(this,"Click on item to select game")
        Toast.makeText(this, "Click on item to select game", Toast.LENGTH_LONG).show()
        return
    }

    val f = FileReadWriteService()



    if(ListVewLongKlickIndex!=-1) {
        val jsonService= json_service
        // mSocket.emit("OpenDB")
        val jsonGames=jsonService.CreateGamesJsonFromAllFiles(this,arrayGamesId[ListVewLongKlickIndex]!!)
        f.writeFileOnInternalStorageGameToUploudProsses(jsonGames,this,arrayGamesId[ListVewLongKlickIndex].toString()+".txt",arrayGamesId[ListVewLongKlickIndex]!!)
        //val FilePath=this.filesDir.toString()+"/GamesInUploudProsses/"+ arrayGamesId[ListVewLongKlickIndex]+"/"+arrayGamesId[ListVewLongKlickIndex]+".txt"
        //splitFile(File(FilePath))
     //   val mediaTransformer = MediaTransformer(applicationContext)
//        mediaTransformer.release();
        mSocket!!.emit("uploadGameToServer",arrayGamesId[ListVewLongKlickIndex],jsonGames,mUserId)
        //todo צריך להוסיף בדיקה בשרת שכל הקבצים הגיע למשימה וגם עם הגיעו קבצים לבדוק שהוכנס גיסון למבנה נתונים
        SendAllFilesToServerFromDirectory(filesDir.toString()+"/GamesInUploudProsses/")
        ShowProgressBarUploadFiles()
    }
    else{
        Toast.makeText(this, "Click on item to select game", Toast.LENGTH_LONG).show()

    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun ShowProgressBarUploadFiles(){
    val directory = File(filesDir.toString()+"/GamesInUploudProsses/"+arrayGamesId[ListVewLongKlickIndex].toString())
    val intent = Intent(this, LauncherActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationChannel = NotificationChannel(channelId, description, NotificationManager .IMPORTANCE_LOW)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.enableVibration(false)
        notificationManager.createNotificationChannel(notificationChannel)

        builder = Notification.Builder(this,channelId).setContentTitle("UPLOADING GAME").setContentText("Running game").setSmallIcon(R.drawable.eye).setLargeIcon(
            BitmapFactory.decodeResource(this.resources, R.drawable
                .ic_launcher_background)).setContentIntent(pendingIntent)
    }
    else{
        builder = Notification.Builder(this).setContentTitle("UPLOADING GAME").setContentText("Running game").setSmallIcon(R.drawable.eye).setLargeIcon(
            BitmapFactory.decodeResource(this.resources, R.drawable
                .ic_launcher_background)).setContentIntent(pendingIntent)
    }
    notificationManager.notify(12345, builder.build())
    val listView = findViewById<ListView>(R.id.listView)

    Thread(Runnable {
        var primaryProgressStatus=0
        while (primaryProgressStatus < 100) {
            //עם אין קבצים בספריה סימן שהכל נשלח ולכן מסיום את שליחת הקבצים
            val Dirs: Array<File> = directory.listFiles()
            if(Dirs.size==0 ){
                builder.setContentText("UPLOADING completed")
                    // Removes the progress bar
                    .setProgress(0,0,false);
                notificationManager.notify(12345,builder.build());
                runOnUiThread {
                    listView.getChildAt(ListVewLongKlickIndex - listView.getFirstVisiblePosition())
                        .setBackgroundColor(Color.WHITE);
                    primaryProgressStatus=100
                }
            }
            if (primaryProgressStatus>98){
                primaryProgressStatus=50}
            primaryProgressStatus += 1
            builder.setProgress(100, primaryProgressStatus, false);
            // Displays the progress bar for the first time.
            notificationManager.notify(12345, builder.build());
            try {
                if (primaryProgressStatus==40 || primaryProgressStatus==60 || primaryProgressStatus==80)
                {
                    SendAllFilesToServerFromDirectory(filesDir.toString()+"/GamesInUploudProsses/")
                }
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }


        }
    }).start()
}

/*@RequiresApi(Build.VERSION_CODES.O)
fun Butten_UploadGame(view: View){
        if(ListVewLongKlickIndex==-1)
        {
            //todo יש להוסיף הןדאה במסך למשתמש
            Toast.makeText(this, "Click on item to select game", Toast.LENGTH_LONG).show()
            return
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMax(100);
        progressDialog.setMessage("Loading ...")
        progressDialog.setCancelable(false) // blocks UI interaction
        progressDialog.show()

        object: CountDownTimer(80000000, 10000){
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                //add your code here
                progressDialog.dismiss()
            }
        }.start()
        if(ListVewLongKlickIndex!=-1) {
            val jsonService= json_service
            // mSocket.emit("OpenDB")
            val jsonGames=jsonService.CreateGamesJsonFromAllFiles(this,arrayGamesId[ListVewLongKlickIndex]!!)

            mSocket!!.emit("uploadGameToServer",arrayGamesId[ListVewLongKlickIndex],jsonGames,mUserId)
        }
        else{
            Toast.makeText(this, "Click on item to select game", Toast.LENGTH_LONG).show()

        }
    }*/
///////////////////////////////////////////////////////////////////////////////////////////////

///////////////////// הצגת מסך המשחקים בתצורת מפה /////////////////////// ////////////////////////
    fun Butten_Show_Map(view: View){
        //val intent = Intent(this,XXX::class.java)
        //startActivityForResult(intent, NewGameAdd)
    }
///////////////////////////////////////////////////////////////////////////////////////////////





    /*fun buttonAddNewGame(view: View) {
        val intent = Intent(this, new_game_insert_main_propertis::class.java)
            //startActivity(intent)
        startActivityForResult(intent, NewGameAdd)
    }*/


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            NewGameAdd -> {
                finish();
                startActivity(getIntent());
            }

        }
    }
/**
    open fun sendImage(path: String) {
        val sendData = JSONObject()
        try {
            sendData.put("imageData", encodeImage(path))
            socket.emit("image", sendData)
        } catch (e: JSONException) {
        }
    }**/

    //פונקציה ששלוחת את כל הקבצים - כל קובץ שהץקבל נמחק ולכן עם קראים שוב לפונקציה לא שולחת קובץ פעמיים
    @RequiresApi(Build.VERSION_CODES.O)
    fun SendAllFilesToServerFromDirectory(FolderName:String){

        val directory = File(FolderName)

        if(directory==null || !directory.exists()){
            Toast.makeText(this, "No Files In Folder To Send", Toast.LENGTH_LONG).show()
            return
        }

        val Dirs: Array<File> = directory.listFiles()
        var index=0
        for (i in Dirs.indices) {
            val dir = Dirs[i].toString()
            val directoryGame = File(dir, "/")
            if (directoryGame == null) {
                Toast.makeText(this, "No Files To Send", Toast.LENGTH_LONG).show()
                return
            }
            val files: Array<File> = directoryGame.listFiles()
            val GameId = dir.split("/")
            var bufferBas64 = ""
            for (j in files.indices) {
                val filePath = files[j].toString()
                val f = FileReadWriteService()

                //val options = BitmapFactory.Options()
                //options.inPreferredConfig = Bitmap.Config.ARGB_8888
                //val bitmap = BitmapFactory.decodeFile(filePath, options)
                //selected_photo.setImageBitmap(bitmap)


                if (filePath.contains(".jpg") == true) {
                    val bitmap = f.readPpicherFromInternalStorageByPathReturnBitmap(filePath, this)
                    val bufferPic = f.PicherToBase64(bitmap)
                    mSocket!!.emit("uploadFiles", GameId[7], files[j].getName(), bufferPic, mUserId)

                }
                else {
                     if (filePath.contains(".mp4") == true) {
                            val VedioBuffer = f.readFileOnInternalStorageByPath(filePath, this)
                            mSocket!!.emit(
                                "uploadFiles",
                                GameId[7],
                                files[j].getName(),
                                VedioBuffer,
                                mUserId
                            )
                        } else {
                            var buffer = f.readFileOnInternalStorageByPath(filePath, this)
                            mSocket!!.emit(
                                "uploadFiles",
                                GameId[7],
                                files[j].getName(),
                                buffer,
                                mUserId
                            )

                        }


                }
            }
        }

}



}



