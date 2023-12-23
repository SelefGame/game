package com.example.test2

import MsgBox
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import org.json.JSONArray


class Start_Play_SelectGame : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    var mUserId=""
    var mGameId=""
    var mlongitude=""
    var mlatitude=""
    var FlegDoTheFunction=true
    var Game_to_play: JSONArray? = null
    var mName= arrayOf<String>()
    var mDescription=arrayOf<String>()
    var imageId = arrayOf<Int>()
    var FlegSelectGameFromList=false

    ///////////////////////////////////////////////////////////////////////
    fun ThredCheckIfNeedToDwonloadFile(){
        Thread(Runnable {
            Thread.sleep(500)
            var NeedToDWONLOD=true

            while (NeedToDWONLOD) {
                if (Game_to_play!!.length() > 0) {
                    for (i in 0 until Game_to_play!!.length()) {
                        //בודק שהמשחק כבר ירד למשתמש למכשיר
                        val IsGameSavad= Game_to_play!!.getJSONObject(i).getString("UserReciveAllFilesOfGame")
                        mGameId = Game_to_play!!.getJSONObject(i).getString("GameId")
                        if (IsGameSavad.equals("false")) {
                            val f = FileReadWriteService()
                            //מכין את הרשימה על הקבצים שיש אצל המשתמש כדי שהשרת ישלח מה שחסר
                            val ListFilesInDeir = f.GetListFilesInFolder(
                                this.filesDir.toString() + "/GameWatingToStartPlay/" + mGameId + "/")
                            //val listFiles=f.checkMissingFileInUserFolder("GameWatingToStartPlay/"+mGameId,
                            //    "GameWatingToStartPlay/"+mGameId
                            //    ,"ListFilesInServer.txt",this)

                            //שולח לשרת את רשימת הקבצים שיש במשתמש עם הרשימה ריקה סימן שאין שם קובץ למשתמש
                            SocketHandler.mSocket!!.emit("GetGamesFromDBbyGameId",Game_to_play!!.getJSONObject(i).getString("GameId"),ListFilesInDeir)
                           // mDescription +="המשחק בתהליך הורדה - יש להמתין עד לסיום"
                        }
                        else {
                            //mName += Game_to_play!!.getJSONObject(i).getString("GameId")
                            //mDescription += "המשחק מוכן להתחיל לשחק"
                            //imageId += R.drawable.kids
                            NeedToDWONLOD = false
                        }
                        runOnUiThread {
                            //val myListAdapter = MyListAdapter(this,mName ,mDescription ,imageId)
                            //val listView = findViewById<ListView>(R.id.listView)
                            //listView.adapter = myListAdapter
                        }
                    }
                }
                try {
                    Thread.sleep(500)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }


            }
        }).start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_play_select_game)
        val listView = findViewById<ListView>(R.id.listView)
            mUserId= intent.getStringExtra("UserId") as String
        //mGameId=intent.getStringExtra("GameId") as String
        mlongitude= intent.getStringExtra("Long") as String
        mlatitude= intent.getStringExtra("Lat") as String
        SocketHandler.mSocket = SocketHandler.getSocket()
        SocketHandler.mSocket!!.emit("GetListGamesUserWatingToPlay",mUserId)

        SocketHandler.mSocket.on("GetListGamesUserWatingToPlay") { args ->
            if (FlegDoTheFunction==true) {

                if (args[0] != null) {
                    Game_to_play = args[0] as JSONArray
                    var aag = Game_to_play
                    if (Game_to_play!!.length() > 0) {
                        for (i in 0 until Game_to_play!!.length()) {
                            //בודק שהמשחק כבר ירד למשתמש למכשיר
                            val IsGameSavad = Game_to_play!!.getJSONObject(i)
                                .getString("UserReciveAllFilesOfGame")
                            mGameId = Game_to_play!!.getJSONObject(i).getString("GameId")
                            if (IsGameSavad.equals("false")) {
                                val f = FileReadWriteService()
                                //מכין את הרשימה על הקבצים שיש אצל המשתמש כדי שהשרת ישלח מה שחסר
                                val ListFilesInDeir = f.GetListFilesInFolder(
                                    this.filesDir.toString() + "/GameWatingToStartPlay/" + mGameId + "/"
                                )
                                //val listFiles=f.checkMissingFileInUserFolder("GameWatingToStartPlay/"+mGameId,
                                //    "GameWatingToStartPlay/"+mGameId
                                //    ,"ListFilesInServer.txt",this)

                                //שולח לשרת את רשימת הקבצים שיש במשתמש עם הרשימה ריקה סימן שאין שם קובץ למשתמש
                                SocketHandler.mSocket!!.emit("GetGamesFromDBbyGameId",Game_to_play!!.getJSONObject(i).getString("GameId"),ListFilesInDeir)
                                mName += getString(R.string.start_play_select_game_butten_start_Wating_TO_Dwonload_Game)
                                mDescription +=Game_to_play!!.getJSONObject(i).getString("GameId")
                                imageId += R.drawable.upload
                                ThredCheckIfNeedToDwonloadFile()
                            }
                            else {
                                FlegDoTheFunction = false
                                mName +=getString(R.string.start_play_select_game_butten_start_Finsh_TO_Dwonload_Game)
                                mDescription +=  Game_to_play!!.getJSONObject(i).getString("GameId")
                                imageId += R.drawable.upload
                            }
                        }
                    } else {
                        // עם אין משחק שנבחר מציג את מפת המשחקים שהמשתמש יבחר משחק
                        val intent = Intent(
                            this,
                            show_exist_games_and_mishens_to_play_on_map_main_screen::class.java
                        )
                        intent.putExtra("Long", mlongitude)
                        intent.putExtra("Lat", mlatitude)
                        intent.putExtra("mUserId", mUserId)
                        startActivity(intent)

                    }
                    runOnUiThread {
                        val myListAdapter = MyListAdapter(this, mName, mDescription, imageId)
                        listView.adapter = myListAdapter
                    }

                }
            }

            }

        val StartSelfGame: CardView = findViewById(R.id.StartSelfGame)
/////////////////////////////// לחיצה על כפתור משחק משפחתי///////////////////
        StartSelfGame.setOnClickListener {
            if (FlegSelectGameFromList==false){
                MsgBox.showMessageDialog(this,getString(R.string.start_play_select_game_butten_start_Nedd_To_Select_Game_msg))
            }
            else{
            val f = FileReadWriteService()
            f.jonFiles(mGameId, this)

            manage_Mishen_When_Play_game.executeInitGame(mGameId, this)
            //MsgBox.showMessageDialog(this,"you start the game . are yuo reddy , you need to by in the start point game have fun")
            manage_Mishen_When_Play_game.executeTheWayToMishen(this)
            //manage_Mishen_When_Play_game.executeShowMishen(this)
            }
        }


/// todo  צריך לבדוק מה זה volatile. ואיך זה תורם לתוכנה
////////////////////////////////////////////////////////////////////////
        SocketHandler.mSocket. on("GetGamesFromDBbyGameId") { args ->

                if (args[0] != null) {
                    val FileList = args[1] as JSONArray
                    val GameID = args[0] as String
                    val FlegReciveAllFilesFromServer = args[2] as String
                    if (FileList.length() != 0) {
                        //var GameId= Game_to_play!!.getJSONObject(0).getJSONObject("GameInfo").getString("GameId")
                        //var aaa=GameInfo.getJSONObject("GameInfo")
                        //val f = FileReadWriteService()
                        //f.writePicherOnInternalStorage()
                        //שומר את המשחק ללא התוכן של התמונות . הופך את התמונות חזרה לתמונות ושומר אותם
                        //f.SavePicFromJASONGame(GameInfo!!,mGameId,this)
                        //f.writeFileOnInternalStorageGameToPlay(GameInfo!!, this)
                        //manage_Mishen_When_Play_game.executeInitGame(mGameId,this)
                        //manage_Mishen_When_Play_game.executeShowMishen()
                        //מעדכן כי יש לעבור למשימה הבאה  - פה הכוונה לעבור לתחנה הראשונה אחרי השמירה
                        //mSocket!!.emit("ChangPlayGameToNextMishenToShow",mUserId,mGameId)
                        //val PlayGameProsses=manage_Mishen_When_Play_game()
                        //var MishenNumber=Game_to_play!!.getJSONObject(0).getString("StutosTheNumberOfMishenIndex")
                        // בכ=גלל שהעידכון למעבר למשימה הבאה יקרה בתהליך אסימטרי אזי בנקודה פה מקדם לתחנה הבאה שהיא התחנה הראשונה
                        //MishenNumber= (MishenNumber.toInt()+1).toString()
                        //PlayGameProsses.ShowMishen(MishenNumber.toInt(),mGameId,GameInfo,this)
                        if (FlegReciveAllFilesFromServer.equals("true")) {
                            val f = FileReadWriteService()
                            f.jonFiles(GameID, this)

                            //SocketHandler.mSocket!!.emit("GetListGamesUserWatingToPlay", mUserId)

                        } else {
                            SocketHandler.mSocket!!.emit("GetListGamesUserWatingToPlay", mUserId)

                        }

                    } else {
                        // לא נוצר טבלת משחק יש לטפל בנושא TODO


                    }
                }



            runOnUiThread {
            }
        }
///////////////////////////////////////////////////////////////////////




/////////////////////////////////////המשתמש בחר את המשחק ורוצה להוסיף או למחוק משימות עובר למסך משימות////////////////////////////////////
        listView.setOnItemClickListener(){adapterView, view, position, id ->
            val listView = findViewById<ListView>(R.id.listView)
            if(listView.getChildAt(position).background!=null){
                val listViewColor = listView.getChildAt(position).background as ColorDrawable
                val colorId = listViewColor.color
                if(colorId==Color.GREEN)
                {
                    listView.getChildAt(position).setBackgroundColor(Color.WHITE);
                    FlegSelectGameFromList=false
                    mGameId=""
                }
                else
                {
                    listView.getChildAt(position).setBackgroundColor(Color.GREEN);
                    //יש להוסיף שעם המשחק לא ירד מדיע למשתמש להמתין
                    mGameId = Game_to_play!!.getJSONObject(position).getString("GameId")
                    FlegSelectGameFromList=true
                }
            }
            else{
                listView.getChildAt(position).setBackgroundColor(Color.GREEN);
                //יש להוסיף שעם המשחק לא ירד מדיע למשתמש להמתין
                mGameId = Game_to_play!!.getJSONObject(position).getString("GameId")
                FlegSelectGameFromList=true}
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////
/*
        listView.setOnItemLongClickListener(OnItemLongClickListener { arg0, arg1, pos, arg3 ->
            this.ListVewLongKlickIndex=pos
            listView.getChildAt(this.ListVewLongKlickIndex).setBackgroundColor(Color.BLUE);
            true}
        )*/
//////////////////////////////////////////////////////////////////////////////////////////////////

    }






}