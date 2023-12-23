package com.example.test2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.*

object json_service {




    fun CreateGameJson(PersonName:String,GameDescription:String,GameKind:String,PlaceGame:String,
                       Gamesubject:String,GameDateCreate:String,GameId:String,GameLat:String,
                        GameLong:String,updateDate:String,Remarks:String):JSONObject{
        val json = JSONObject()
        try {

            json.put("GameId", GameId)
            json.put("PlaceGame", PlaceGame)
            json.put("GameLat", GameLat)
            json.put("GameLong", GameLong)
            json.put("PersonName", PersonName)
            json.put("GameDescription", GameDescription)
            json.put("GameKind", GameKind)
            json.put("GameDateCreate", GameDateCreate)
            json.put("updateDate", updateDate)
            json.put("Gamesubject", Gamesubject)
            json.put("Remarks", Remarks)





            //json.put("offices", listOf("California", "Washington", "Virginia"))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return json
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun CreateGamesJsonFromAllFiles(context:Context, mGameId:String): JSONObject {
        val f = FileReadWriteService()
        val finalobject = JSONObject()
        val gameJsonFile = f.readFileOnInternalStorage(mGameId+".txt", "Gamefiles", context)
        val jsonObj = JSONObject(gameJsonFile)




        finalobject.put("GameInfo",jsonObj)

        val directory = File(context.filesDir, "Gamefiles/"+mGameId)

        if(directory==null){
            Toast.makeText(context, "ERROR ERROR ERROR FOLDER GAME NOT EXIST IS NULL", Toast.LENGTH_LONG).show()
        }

        var files: Array<File> = directory.listFiles()
        var index=0
        val jsonArray = JSONArray()



        for (i in files.indices) {
            //Log.d("Files", "FileName:" + files[i].getName())
            if(files[i].isFile && files[i].toString().contains(".jpg")==false && files[i].isFile && files[i].toString().contains(".mp4")==false ) {
                //val tempmissen=missen()
                var CountSplitFiles=""
                val jsonStr = f.readFileOnInternalStorage(files[i].getName(), "Gamefiles/"+mGameId, context)
                val jsonObj = JSONObject(jsonStr)
                jsonArray.put(jsonObj)
                ////////// for create the vido base 64 /////////
                val VidioPath = jsonObj.getString("VidioPath")
                if (!VidioPath.equals(""))
                 {
                     val file = File(context?.filesDir.toString() +"/Gamefiles/"+VidioPath)
                    if (file.exists() ) {
                        CountSplitFiles=f.splitFile(file)
                        }


                    jsonObj.put("VideoStr",files[i].name)
                    jsonObj.put("VideoSplitNumber",CountSplitFiles)


                 }

            }
            else{
                if (files[i].toString().contains(".jpg")==true){
                    val fileName=files[i].toString().split("/")
                    f.CopyFile(files[i].toString(),"/GamesInUploudProsses/"+mGameId+"/"+fileName[8],context)
                    f.DeletFile("/GamesInUploudProsses/"+mGameId+"/"+fileName[8],context)
                }
            }


        }
        //ללא שבסוף בגלל שיצרנו פיצול של וידאו שלא נשלחו שוח כל מה שיש בספריה
        files = directory.listFiles()
        for (i in files.indices) {
            val fileName = files[i].toString().split("/")
            val len=fileName.size
            if (fileName[len-1].contains(".split")) {
                f.moveFile(files[i],context.filesDir.toString()+"/GamesInUploudProsses/"+ mGameId+"/"+fileName[len-1])
                /*val buffer = f.VideoToBase64(File(files[i].toString()))
                f.writeVideoFileOnInternalStorageGameToUploudProsses(
                    buffer!!,
                    context,
                    fileName[len-1],
                    mGameId
                )
                files[i].delete()*/
                //f.CopyFile(files[i].toString(),"/GamesInUploudProsses/"+mGameId+"/"+fileName[8],context)
            }
        }

        finalobject.put("Mishens", jsonArray);

        return finalobject;
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodeImage(path: String): String? {
        val imagefile = File(path)
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(imagefile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val bm = BitmapFactory.decodeStream(fis)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        //Base64.de
        return Base64.getEncoder().encodeToString(b)
    }

}