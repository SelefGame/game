package com.example.test2

import MsgBox
import android.app.Application
import android.content.Context
import android.content.Intent
import org.json.JSONObject


class manage_Mishen_When_Play_game:Application () {




    companion object {
        var instance: manage_Mishen_When_Play_game? =null

        lateinit var mGameJSONObject:JSONObject
        lateinit var mDBGamesInfoUserWntsToPlay:JSONObject
        var mCorentMishenNumber:Int=0
        var mContext: Context? =null
        var mMishenResContext: Context? =null
        var mGameId="0"
        var mUserId="0"
        var mSocket: io.socket.client.Socket? =null
        var mPlaceMishen=""
        var mMishenlat=""
        var mMishenlong=""
        var mlistMishenInGameAndDescription=""
        var mNewMishen="false"
        var mclue=""
        var mInstractionToMishen=""
        var mDontShowMishenPoint=""
        private val GetGPS=12

        @JvmName("getInstance1")
        fun  getInstance():manage_Mishen_When_Play_game  {
            if (instance == null)  // NOT thread safe!
                instance = manage_Mishen_When_Play_game()

            return instance!!
        }



        fun executeInitGame(GameId:String,Context:Context) {
            getInstance().initGame(GameId,Context)
        }

        fun executeUpdateServerAndFilesIndexMishenToNextMishen() {
            getInstance().UpdateServerAndFilesIndexMishenToNextMishen()
        }

        fun executeShowMishen(context:Context) {
            getInstance().ShowMishen(context)
        }

        fun executeTheWayToMishen(context:Context) {
            getInstance().TheWayToMishen(context)
        }

        fun executeCheckMishen(anserJsonObject:JSONObject,MishenResContext:Context) {
            getInstance().checkMishen(anserJsonObject,MishenResContext)
        }
    }

    fun initGame(GameId:String,Context:Context){
        val f = FileReadWriteService()
        mSocket = SocketHandler.getSocket()
        mGameId=GameId
        mContext=Context
        val PlayGame =f.readFileOnInternalStorage(mGameId+"_GamesUserWantsToPlay.txt","GameWatingToStartPlay/"+mGameId,Context)
        mDBGamesInfoUserWntsToPlay= JSONObject(PlayGame)
        //mGameId=JSONObject.getString("GameId")
        mCorentMishenNumber =mDBGamesInfoUserWntsToPlay.getString("StutosTheNumberOfMishenIndex").toInt()
        mUserId=mDBGamesInfoUserWntsToPlay.getString("UserId")
        val GameInfo =f.readFileOnInternalStorage(mGameId+".txt","GameWatingToStartPlay", Context)


        mGameJSONObject= JSONObject(GameInfo)

    }

    fun UpdateServerAndFilesIndexMishenToStartGameFromBegining(){
        SocketHandler.mSocket.emit("ChangPlayGameToNextMishenToShow",mUserId,mGameId)
        var MishenNumber="0"
        MishenNumber= (MishenNumber.toInt()+1).toString()
        mDBGamesInfoUserWntsToPlay.put("StutosTheNumberOfMishenIndex",MishenNumber)
        val f = FileReadWriteService()
        f.writeFileOnInternalStorageGame(mGameId+"_GamesUserWantsToPlay.txt","GameWatingToStartPlay/"+mGameId,mDBGamesInfoUserWntsToPlay,mContext!!)
        mCorentMishenNumber=MishenNumber.toInt()

    }


    fun UpdateServerAndFilesIndexMishenToNextMishen(){
        SocketHandler.mSocket.emit("ChangPlayGameToNextMishenToShow",mUserId,mGameId)
        var MishenNumber=mDBGamesInfoUserWntsToPlay.getString("StutosTheNumberOfMishenIndex")
        MishenNumber= (MishenNumber.toInt()+1).toString()
        mDBGamesInfoUserWntsToPlay.put("StutosTheNumberOfMishenIndex",MishenNumber)
        val f = FileReadWriteService()
        f.writeFileOnInternalStorageGame(mGameId+"_GamesUserWantsToPlay.txt","GameWatingToStartPlay/"+mGameId,mDBGamesInfoUserWntsToPlay,mContext!!)
        mCorentMishenNumber=MishenNumber.toInt()

    }

    fun checkMishen(anserJsonObject:JSONObject,mMishenResContext:Context){
        val MishensArry = mGameJSONObject.getJSONArray("Mishens")
        val MishenKind= MishensArry!!.getJSONObject(mCorentMishenNumber).getString("kind")

        when (MishenKind) {
            "1.1" ->{
                val AnserQwestenIs= MishensArry!!.getJSONObject(mCorentMishenNumber).getString("AnserQwestenIs")
                var UserAnserQwestenIs=anserJsonObject.getString("mAnserQwestenIs").replace("[", "")
                UserAnserQwestenIs=UserAnserQwestenIs.replace("]","")
                UserAnserQwestenIs=UserAnserQwestenIs.replace(" ","")
                val arrayAnserQwestenIs = ArrayList<String>(AnserQwestenIs.split(","))
                val arrayUserAnserQwestenIs = ArrayList<String>(UserAnserQwestenIs.split(","))
                val foundItems =arrayUserAnserQwestenIs.containsAll(arrayAnserQwestenIs)

                if (foundItems) {
                    println("Items found in the list")
                    executeUpdateServerAndFilesIndexMishenToNextMishen()
                    //executeShowMishen(mMishenResContext)
                    executeTheWayToMishen(mMishenResContext)
                } else {
                    println("Items not found in the list")
                    MsgBox.showMessageDialog(mMishenResContext, mMishenResContext.getString(R.string.manage_Mishen_When_Play_game_MSB_Txt_RongEnser).toString())
                }
            }
            "1.2" ->{
                val AnserQwestenIs= MishensArry!!.getJSONObject(mCorentMishenNumber).getString("AnserQwestenIs")
                var UserAnserQwestenIs=anserJsonObject.getString("mAnserQwestenIs").replace("[", "")
                UserAnserQwestenIs=UserAnserQwestenIs.replace("]","")
                UserAnserQwestenIs=UserAnserQwestenIs.replace(" ","")
                //val arrayAnserQwestenIs = ArrayList<String>(AnserQwestenIs.split(","))
                //val arrayUserAnserQwestenIs = ArrayList<String>(UserAnserQwestenIs.split(","))
                //val foundItems =arrayUserAnserQwestenIs.containsAll(arrayAnserQwestenIs)

                if (AnserQwestenIs.equals(UserAnserQwestenIs)) {
                    println("Items found in the list")
                    executeUpdateServerAndFilesIndexMishenToNextMishen()
                    //executeShowMishen(mMishenResContext)
                    executeTheWayToMishen(mMishenResContext)
                } else {
                    println("Items not found in the list")
                    MsgBox.showMessageDialog(mMishenResContext, mMishenResContext.getString(R.string.manage_Mishen_When_Play_game_MSB_Txt_RongEnser).toString())
                }
            }
            "2.1" -> {
                val AnserQwestenIs= MishensArry!!.getJSONObject(mCorentMishenNumber).getString("AnserQwestenIs")
                var UserAnserQwestenIs=anserJsonObject.getString("mAnserQwestenIs").replace("[", "")
                UserAnserQwestenIs=UserAnserQwestenIs.replace("]","")
                UserAnserQwestenIs=UserAnserQwestenIs.replace(" ","")
                val arrayAnserQwestenIs = ArrayList<String>(AnserQwestenIs.split(","))
                val arrayUserAnserQwestenIs = ArrayList<String>(UserAnserQwestenIs.split(","))
                val foundItems =arrayUserAnserQwestenIs.containsAll(arrayAnserQwestenIs)

                if (foundItems) {
                    println("Items found in the list")
                    executeUpdateServerAndFilesIndexMishenToNextMishen()
                    //executeShowMishen(mMishenResContext)
                    executeTheWayToMishen(mMishenResContext)
                } else {
                    println("Items not found in the list")
                    MsgBox.showMessageDialog(mMishenResContext, mMishenResContext.getString(R.string.manage_Mishen_When_Play_game_MSB_Txt_RongEnser).toString())
                    //val MsgBox=MsgBox()
                    //MsgBox.show(mContext!!,getString(R.string.manage_Mishen_When_Play_game_MSB_Txt_RongEnser),getString(R.string.manage_Mishen_When_Play_game_Title_RongEnser))
                    //ShowMishen()
                }

            }
            "2.2" -> {
                val AnserQwestenIs= MishensArry!!.getJSONObject(mCorentMishenNumber).getString("AnserQwestenIs")
                var UserAnserQwestenIs=anserJsonObject.getString("mAnserQwestenIs").replace("[", "")
                UserAnserQwestenIs=UserAnserQwestenIs.replace("]","")
                UserAnserQwestenIs=UserAnserQwestenIs.replace(" ","")
                //val arrayAnserQwestenIs = ArrayList<String>(AnserQwestenIs.split(","))
                //val arrayUserAnserQwestenIs = ArrayList<String>(UserAnserQwestenIs.split(","))
                //val foundItems =arrayUserAnserQwestenIs.containsAll(arrayAnserQwestenIs)

                if (AnserQwestenIs.equals(UserAnserQwestenIs)) {
                    println("Items found in the list")
                    executeUpdateServerAndFilesIndexMishenToNextMishen()
                    //executeShowMishen(mMishenResContext)
                    executeTheWayToMishen(mMishenResContext)
                } else {
                    println("Items not found in the list")
                    MsgBox.showMessageDialog(mMishenResContext, mMishenResContext.getString(R.string.manage_Mishen_When_Play_game_MSB_Txt_RongEnser).toString())
                    //val MsgBox=MsgBox()
                    //MsgBox.show(mContext!!,getString(R.string.manage_Mishen_When_Play_game_MSB_Txt_RongEnser),getString(R.string.manage_Mishen_When_Play_game_Title_RongEnser))
                    //ShowMishen()
                }
            }
        }

    }

    fun TheWayToMishen(context: Context){
        val MishensArry = mGameJSONObject.getJSONArray("Mishens")
        // בדיקה שהמשתמש שיחק והגיע לתחנה האחרונה
        if(MishensArry.length()==mCorentMishenNumber){
            MsgBox.showMessageDialog(context,"הגעתה לסוף המשחק - אין יותר משימות")
            // לצורך פיתוח מתחיל את המשלק מחדש עם נגמר - יש להוסיף ממשק משתמש לשחק מחדש
            UpdateServerAndFilesIndexMishenToStartGameFromBegining()
            return
        }
        /*mPlaceMishen=mCorentMishenNumber.toString()
        mMishenlat=MishensArry!!.getJSONObject(mCorentMishenNumber).getString("lat")
        mMishenlong=MishensArry!!.getJSONObject(mCorentMishenNumber).getString("long")
        mlistMishenInGameAndDescription=""
        mNewMishen="false"
        mclue=MishensArry!!.getJSONObject(mCorentMishenNumber).getString("clue")
        mInstractionToMishen=MishensArry!!.getJSONObject(mCorentMishenNumber).getString("InstractionToMishen")
        mDontShowMishenPoint=MishensArry!!.getJSONObject(mCorentMishenNumber).getString("DontShowMishenPoint")


        intent2.putExtra("TotalNumberOfMishen",mPlaceMishen.toString())
        intent2.putExtra("lat",mMishenlat )
        intent2.putExtra("long",mMishenlong)
        intent2.putExtra("listMishenInGameAndDescription",mlistMishenInGameAndDescription)
        intent2.putExtra("NewMishen",mNewMishen)
        intent2.putExtra("clue",mclue)
        intent2.putExtra("InstractionToMishen",mInstractionToMishen)
        intent2.putExtra("DontShowMishenPoint",mDontShowMishenPoint)*/
        val intent2 = Intent(mContext, map_the_way_to_mishen_free_map::class.java)
        intent2.putExtra("MishenString",MishensArry[mCorentMishenNumber].toString())
        mContext!!.startActivity(intent2)
    }



    fun ShowMishen(context: Context){
        val MishensArry = mGameJSONObject.getJSONArray("Mishens")
        // בדיקה שהמשתמש שיחק והגיע לתחנה האחרונה
        if(MishensArry.length()==mCorentMishenNumber){
            MsgBox.showMessageDialog(context,"הגעתה לסוף המשחק - אין יותר משימות")
            return
        }


        val MishenKind= MishensArry!!.getJSONObject(mCorentMishenNumber).getString("kind")
        MishensArry!!.getJSONObject(mCorentMishenNumber).put("picStr","")
        when (MishenKind) {
            "1.1" ->{
                val intent = Intent(mContext, show_mishen_kind_1_1_radiobutten::class.java)
                intent.putExtra("MishenString",MishensArry[mCorentMishenNumber].toString())
                mContext!!.startActivity(intent)
            }
            "1.2" ->{
                val intent = Intent(mContext, show_mishen_kind_1_2_yes_no::class.java)
                intent.putExtra("MishenString",MishensArry[mCorentMishenNumber].toString())
                mContext!!.startActivity(intent)
            }
            "2.1" -> {
                val intent = Intent(mContext, show_mishen_kind_2_1_radiobutten::class.java)
                intent.putExtra("MishenString",MishensArry[mCorentMishenNumber].toString())
                mContext!!.startActivity(intent)
            }
            "2.2" -> {
                val intent = Intent(mContext, show_mishen_kind_2_2_yes_no::class.java)
                intent.putExtra("MishenString",MishensArry[mCorentMishenNumber].toString())
                mContext!!.startActivity(intent)
            }
        }


    }




    override fun onCreate(){
        super.onCreate()

        // call evry place to manage_Mishen_When_Play_game.instance.
        SocketHandler.mSocket. on("ChangPlayGameToNextMishenToShow") { args ->
            if (args[0] != null) {
                mDBGamesInfoUserWntsToPlay = args[0] as JSONObject

            }

        }
    }



}