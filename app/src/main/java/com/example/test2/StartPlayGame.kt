package com.example.test2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test2.SocketHandler.mSocket
import org.json.JSONArray

class StartPlayGame : AppCompatActivity() {
    var mUserId=""
    var mGameId=""
    var mlongitude=""
    var mlatitude=""
    var Game_to_play: JSONArray? = null
    //val manage_Mishen_When_Play_game = manage_Mishen_When_Play_game()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_play_game)
        mUserId= intent.getStringExtra("UserId") as String
        //mGameId=intent.getStringExtra("GameId") as String
        mlongitude= intent.getStringExtra("Long") as String
        mlatitude= intent.getStringExtra("Lat") as String
        mSocket = SocketHandler.getSocket()
        // בדיקה שיש תקשורת עם אין בודק עם כבר הוריד קובץ וניתן לשחק ללא אינטארנט
        // קבלת קבצים שהמשתמש רוצה לשחק מהשרת
        mSocket!!.emit("GetListGamesUserWatingToPlay",mUserId)

        mSocket.on("GetListGamesUserWatingToPlay") { args ->
            if (args[0] != null) {
                Game_to_play = args[0] as JSONArray
                var aag=Game_to_play
                //עם יש רק משחק אחד שהמשתמש נרשם  מוריד רק משחק אחד
                if (Game_to_play!!.length()==1){
                    val IsGameSavad= Game_to_play!!.getJSONObject(0).getString("UserReciveAllFilesOfGame")
                    mGameId = Game_to_play!!.getJSONObject(0).getString("GameId")
                    // בדיקה האם יש בספריה משימות עם קבצים שצריך לחבר אותם חזרה לקובץ
                    val f = FileReadWriteService()
                    f.jonFiles(mGameId,this)
                    // עם המשחק עדיין לא נשמר והסטאטוס הוא false שומר אותו לדיסק
                    if (IsGameSavad.equals("false")) {
                        mSocket!!.emit("GetGamesFromDBbyGameId",mGameId)
                        }
                    else{
                            manage_Mishen_When_Play_game.executeInitGame(mGameId,this)
                            manage_Mishen_When_Play_game.executeShowMishen(this)
                        }
                }
                // עם יש מספר משחקים שהמשתמש נרשם מוריד את כולם
                else{
                    // עם אין משחק שנבחר מציג את מפת המשחקים שהמשתמש יבחר משחק
                    val intent = Intent(this, show_exist_games_and_mishens_to_play_on_map_main_screen::class.java)
                    intent.putExtra("Long",mlongitude)
                    intent.putExtra("Lat",mlatitude)
                    intent.putExtra("mUserId",mUserId)
                    startActivity(intent)
                }


                runOnUiThread {

                }
            }
        }


        mSocket. on("GetGamesFromDBbyGameId") { args ->
            if (args[0] != null) {
                val GameInfo = args[0] as JSONArray

                if (GameInfo!!.length()==1){
                    //var GameId= Game_to_play!!.getJSONObject(0).getJSONObject("GameInfo").getString("GameId")
                    //var aaa=GameInfo.getJSONObject("GameInfo")
                    val f = FileReadWriteService()
                    //שומר את המשחק ללא התוכן של התמונות . הופך את התמונות חזרה לתמונות ושומר אותם
                    f.SavePicFromJASONGame(GameInfo!!,mGameId,this)
                    f.writeFileOnInternalStorageGameToPlay(GameInfo!!, this)
                    manage_Mishen_When_Play_game.executeInitGame(mGameId,this)
                    manage_Mishen_When_Play_game.executeShowMishen(this)
                    //מעדכן כי יש לעבור למשימה הבאה  - פה הכוונה לעבור לתחנה הראשונה אחרי השמירה
                    //mSocket!!.emit("ChangPlayGameToNextMishenToShow",mUserId,mGameId)
                    //val PlayGameProsses=manage_Mishen_When_Play_game()
                    //var MishenNumber=Game_to_play!!.getJSONObject(0).getString("StutosTheNumberOfMishenIndex")
                    // בכ=גלל שהעידכון למעבר למשימה הבאה יקרה בתהליך אסימטרי אזי בנקודה פה מקדם לתחנה הבאה שהיא התחנה הראשונה
                    //MishenNumber= (MishenNumber.toInt()+1).toString()
                    //PlayGameProsses.ShowMishen(MishenNumber.toInt(),mGameId,GameInfo,this)


                    }
                else{
                        // לא נוצר טבלת משחק יש לטפל בנושא TODO


                    }
                }



                runOnUiThread {
            }
        }

/*
///////////// קבלת תמונות מהשרת במידה ויש במשחק תמונות
        mSocket.on("SendPicherFromServerToUser") { args ->
            if (args[0] != null) {
               // val res = args[0]
                val f=FileReadWriteService()
                val res=args[0] as JSONObject
                val buf = res.get("PicherAsString") as ByteArray
                val FileName=res.getString("file")
                val Bitmap=f.Base64ToPicher(String(buf))
                // val picStr=Base64.getDecoder().decode(buf)
                //val Bitmap=byteArrayToBitmap(picStr)
                if (Bitmap!=null) {
                    f.writePicherOnInternalStorage(FileName, mGameId, Bitmap!!, this)
                }
                runOnUiThread {
                    //MainTextView.text = counter.toString()
                }
            }
        }
*/
        //משאיר את זה פה כי זה הנקודה הראשונה של שמירת הקובץ בפימים הבאות ישתמש באביוקט משחק
    /*    mSocket. on("ChangPlayGameToNextMishenToShow") { args ->
            if (args[0] != null) {
                val ChangPlayGameToNextMishenToShow = args[0] as JSONObject

                if (ChangPlayGameToNextMishenToShow!!.length()!=0){

                    val f = FileReadWriteService()
                    f.writeFileOnInternalStorageGame(mGameId+"_GamesUserWantsToPlay.txt","GameWatingToStartPlay/"+mGameId,ChangPlayGameToNextMishenToShow,this)


                }

                else{
                    // לא נוצר טבלת משחק יש לטפל בנושא TODO


                }
                manage_Mishen_When_Play_game.executeInitGame(mGameId,this)
                manage_Mishen_When_Play_game.executeUpdateServerAndFilesIndexMishenToNextMishen()
                manage_Mishen_When_Play_game.executeShowMishen()
            }

        }*/








    }



}

