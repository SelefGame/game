package com.example.test2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.util.*

class FileReadWriteService {

    //private var context: Context? = ContextHolder.instance.appContext

    /*fun writeFileOnInternalStorageMishenUpdate(DirName:String,MishebId:String,kind:String, Qwesten: String,
                                               anser1: String, anser2: String, anser3: String, anser4: String,lat:String,long:String,
                                               remarks:String,GameId:String,context: Context) {
        val file = File(context?.filesDir, DirName+"//"+MishebId+".txt")
        if (file.exists()) {
            file.delete()
        }
        writeFileOnInternalStorageMishen(MishebId+".txt",DirName,MishebId,kind, Qwesten, anser1, anser2, anser3, anser4,lat,long,remarks,MishebId,context)
    }*/


    fun writeFileOnInternalStorageMishenUpdate1(DirName:String,MishebId:String,kind:String, Qwesten: String,
                                               anser1: String, anser2: String, anser3: String, anser4: String,AnserQwestenIs:ArrayList<String>,lat:String,long:String,mCoordinates:Array<Double> ,remarks:String,gameId:String,
                                                NewMishen:Boolean,picher:String,picStr:String,picherSplitNumber:String,VidioPath:String,VideoSplitNumber:String,
                                                PlaceOrderOfMissen:String,context: Context,clue:String,
                                                InstractionToMishen:String,DontShowMishenPoint:String) {
        val file = File(context?.filesDir, DirName+"//"+MishebId+".txt")
        if (file.exists()) {
            file.delete()
        }
        writeFileOnInternalStorageMishen1(MishebId+".txt",DirName,MishebId,kind, Qwesten, anser1, anser2,
            anser3, anser4,AnserQwestenIs,lat,long,mCoordinates,remarks,gameId,NewMishen,picher,picStr,picherSplitNumber,VidioPath,
            VideoSplitNumber,PlaceOrderOfMissen,context,clue,InstractionToMishen,DontShowMishenPoint)
    }




    /*fun writeFileOnInternalStorageMishen(fileKey: String,DirName:String,MishebId:String,kind:String,
                                         Qwesten: String, anser1: String, anser2: String, anser3: String,
                                         anser4: String,lat:String,long:String,remarks:String,GameId:String,
                                         context: Context) {
        val file = File(context?.filesDir, DirName)
        try {
            if (!file.exists()) {
                file.mkdir()
            }
            val json = JSONObject()
            try {
                json.put("MishebId", MishebId)
                json.put("kind", kind)
                json.put("Qwesten", Qwesten)
                json.put("anser1", anser1)
                json.put("anser2", anser2)
                json.put("anser3", anser3)
                json.put("anser4", anser4)
                json.put("lat", lat)
                json.put("long", long)
                json.put("remarks", remarks)
                json.put("GameId", GameId)


                //json.put("offices", listOf("California", "Washington", "Virginia"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val fileToWrite = File(file, fileKey)
            val writer = FileWriter(fileToWrite)
            writer.append(json.toString())
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            //Logger.e(classTag, e)
            var a=1
        }
    }*/


    fun writeFileOnInternalStorageMishen1(
        fileName: String,
        DirName:String,
        MishebId:String,
        kind:String, Qwesten: String, anser1: String, anser2: String, anser3: String,
        anser4: String,
        AnserQwestenIs:ArrayList<String>,
        lat:String,
        long:String,
        mCoordinates:Array<Double>,
        remarks:String,
        gameId:String,
        NewMishen:Boolean,
        picherName:String,
        picStr: String?,
        picherSplitNumber:String,
        VidioPath:String,
        VideoSplitNumber:String,
        PlaceOrderOfMissen:String,
        context: Context,
        mclue:String,
        mInstractionToMishen:String,
        mDontShowMishenPoint:String) {
        val file = File(context?.filesDir, DirName)
        var AnserQwesten=""
        try {
            if (!file.exists()) {
                file.mkdir()
            }
            val json = JSONObject()
            try {
                json.put("MishebId", MishebId)
                json.put("kind", kind)
                json.put("Qwesten", Qwesten)
                json.put("anser1", anser1)
                json.put("anser2", anser2)
                json.put("anser3", anser3)
                json.put("anser4", anser4)
                var index:Int=1
                for( anser in AnserQwestenIs)
                {
                    AnserQwesten=AnserQwesten+anser.trim()
                    AnserQwesten=AnserQwesten+","

                    index=index+1
                }
                AnserQwesten=AnserQwesten.removeSuffix(",")
                AnserQwesten=AnserQwesten+""

               // AnserQwesten="{[AnserIs1:" + AnserQwestenIs.get(0)+",AnserIs2:"+ AnserQwestenIs.get(1)+",AnserIs3:"+ AnserQwestenIs.get(2)+",AnserIs4:"+ AnserQwestenIs.get(4)+"]}"

                json.put("AnserQwestenIs",AnserQwesten)
                json.put("lat", lat)
                json.put("long", long)
                ///////////////////////////
                val jsonString = "{\"type\":\"Point\"," +
                        "\"coordinates\":["+lat+","+long+"]}"
                var obj = JSONObject(jsonString)
                json.put("location",obj)
                //////////////////////////

                json.put("remarks", remarks)
                json.put("GameId", gameId)
                json.put("PlaceOrderOfMissen", PlaceOrderOfMissen)
                json.put("picherName", picherName)
                json.put("picStr", picStr)
                json.put("picherSplitNumber", picherSplitNumber)
                json.put("VidioPath",VidioPath )
                json.put("VideoSplitNumber",VideoSplitNumber )
                json.put("clue",mclue )
                json.put("InstractionToMishen",mInstractionToMishen )
                json.put("DontShowMishenPoint",mDontShowMishenPoint )




                //json.put("offices", listOf("California", "Washington", "Virginia"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val fileToWrite = File(file, fileName)
            val writer = FileWriter(fileToWrite)
            writer.append(json.toString())
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            //Logger.e(classTag, e)
            var a=1
        }
    }




    fun writeFileOnInternalStorageMishenPic(FileName: String,DirName:String,MishebId:String,GameId:String,
                                            bitmapMishen:Bitmap , context: Context) {
        val file = File(context?.filesDir, DirName)
        try {
            if (!file.exists()) {
                file.mkdir()
            }



            val fileToWrite = File(file, FileName)
            if (fileToWrite.exists()) {
                fileToWrite.delete()
            }
            val fOut = FileOutputStream(fileToWrite)
            bitmapMishen.compress(Bitmap.CompressFormat.JPEG, 40, fOut) // was 85
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            //Logger.e(classTag, e)
            var a=1
        }
    }


////////////////////////////////////////////////////////////////////////////////////
//////////////////פונקציה שבודקת עם כל הקבצים בספריה זהים לרשימה של הקבצים שיש בשרת
fun checkMissingFileInUserFolder(FolderListFilesToCheckPath:String,FileListFilesInServer:String,FileNameSaveListFilesInServer:String, context: Context): String {
    var FileListInServer=""
    var pathFolderListFilesToCheckPath=context?.filesDir.toString()+"/"+FolderListFilesToCheckPath
    val PathFileListFilesInServer=context?.filesDir.toString()+"/"+FileListFilesInServer
    val ListFilesInDeir = GetListFilesInFolder(pathFolderListFilesToCheckPath)!!.split(",")
    var ArryListFilesInDeir=ArrayList<String>()
    var ArryMissingFileInUser=JSONArray()
    for(item in ListFilesInDeir){
        if (item.contains("[") || item.contains("]")) {
            item.replace("[","")
            item.replace("]","")
        }
        if (!item.contains(".txt")|| !item.contains(".mp4")){
            ArryListFilesInDeir.add(item.substring(1, item.length - 1))
        }
    }
    val index=0
    var foundFile=""
    if (!PathFileListFilesInServer.equals("")){
        val file = File(PathFileListFilesInServer+"/"+FileNameSaveListFilesInServer)
        if (file.exists()) {
            FileListInServer=readFileOnInternalStorage(PathFileListFilesInServer,FileNameSaveListFilesInServer,context )

            for(item in FileListInServer)
            {

                foundFile= ArryListFilesInDeir.find { actor -> item.toString().equals(actor) }.toString()
                if(foundFile!=null)
                {

                    ArryMissingFileInUser.put(item.toString())
                }

            }
        }


    }
    // עם הרשימה רקיה סימן שאין שום קובץ מהשרת בספריה של המשתמש
    if (ArryMissingFileInUser.length()==0){
        ArryMissingFileInUser.put("Send All Files")
    }
    return ArryMissingFileInUser.toString()


/*
        val res=ArryListFilesInDeir.equals(FileListInServer)
        if (res==false){

        }*/
}
////////////////////////////////////////////////////////////////////////////////////


    fun GetListFilesInFolder(Path :String): String? {
        val directory = File(Path)
        if(directory==null){
            return ""
        }
        if (directory.listFiles()==null)
            return ""

        val files: Array<File> = directory.listFiles()
        var index=0
        val jsonArray = JSONArray()
        //jsonArray.put(GameId)
        for (i in files.indices) {
            val fileName = files[i].name.toString()
            jsonArray.put(fileName)
        }


    return jsonArray.toString()
    }



    @SuppressLint("SuspiciousIndentation")
    fun writePicherOnInternalStorage(FilaName:String, GameId:String,
                                     bitmapMishen:Bitmap, context: Context) {
        var file = File(context?.filesDir, "GameWatingToStartPlay")


            if (!file.exists()) {
                file.mkdir()
            }
            //val FileName = MishebId + ".jpg"

            file = File(context?.filesDir, "GameWatingToStartPlay/" + GameId)
            try {
                if (!file.exists()) {
                    file.mkdir()
                }

                val fileToWrite = File(file,FilaName)
                if (fileToWrite.exists()) {
                    fileToWrite.delete()
                }
                val fOut = FileOutputStream(fileToWrite)
                bitmapMishen.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
                fOut.flush()
                fOut.close()
            } catch (e: Exception) {
                //Logger.e(classTag, e)
                var a = 1
            }


    }


    fun writeFileOnInternalStorageGameToPlay( Gamejson: JSONArray, context:Context){
        var file = File(context?.filesDir, "PlayGamefiles")
        val GameId=Gamejson.getJSONObject(0).getJSONObject("GameInfo").getString("GameId")
        val GameFileName = GameId+".txt"
        if (!file.exists()) {
            file.mkdir()
        }

        file = File(context?.filesDir,"PlayGamefiles/"+ GameId)
        try {
            if (!file.exists()) {
                file.mkdir()
            }


            val fileToWrite = File(file, GameFileName)
            val writer = FileWriter(fileToWrite)
            writer.write(Gamejson.toString())
            writer.flush()
            writer.close()
        } catch (e: Exception) {
            //Logger.e(classTag, e)
            var a=1
        }
    }
/////////////////////// העתקת קבצים ////////////////////

    fun CopyFile(surc:String,Dest:String,context:Context){
        var file = File(surc)
        var path=context?.filesDir.toString() +"/"+ Dest
        var fileDest=File(path)
        if (fileDest.exists()==false) {
            val res=file.copyTo(File(context?.filesDir,Dest),true)
        }

    }
///////////////////////////////////////////////////////////





    @RequiresApi(Build.VERSION_CODES.O)
    fun splitFile(f: File):String {
        var partCounter = 1 //I like to name parts from 001, 002, 003, ...
        //you can change it to 0 if you want 000, 001, ...
        val sizeOfFiles = 1024 * 1024 // 1MB
        //val sizeOfFiles = 512 * 512
        val buffer = ByteArray(sizeOfFiles)
        var Base64buffer = ByteArray(sizeOfFiles)
        val fileName: String = f.getName()
        FileInputStream(f).use { fis ->
            BufferedInputStream(fis).use { bis ->
                var bytesAmount = 0
                while (bis.read(buffer).also { bytesAmount = it } > 0) {
                    //write each chunk of data into separate file with different number in name
                    var filePartName =
                        String.format("%s.%03d", fileName, partCounter++)
                    val newFile = File(f.getParent(), filePartName+".split")
                    Base64buffer=java.util.Base64.getEncoder().encode(buffer)
                    val sss=Base64buffer.size
                    FileOutputStream(newFile).use {
                            out -> out.write(Base64buffer, 0, Base64buffer.size)
                    }
                    //mSocket!!.emit("uploadFiles",arrayGamesId[ListVewLongKlickIndex],filePartName,buffer,mUserId)

                }
            }
        }

    return (partCounter-1).toString()
    }


    fun jonFiles(GameId:String,context:Context){
        val MishensArry:JSONArray
        val GameInfo =readFileOnInternalStorage(GameId +".txt","GameWatingToStartPlay", context)
        //val GameInfo =readFileOnInternalStorage(GameId +".txt","/Gamefiles/af1523e3-3582-4d3e-b4b5-0ae263481d1e/", context)
        val GameJSONObject = JSONObject(GameInfo)
        MishensArry = GameJSONObject.getJSONArray("Mishens")

        try {

            for (i in 0 until MishensArry.length()) {
                val Mishen = MishensArry.get(i)as JSONObject
                //val Mishen:JSONObject =GameJSONObject
                val MishenId =Mishen.getString("MishebId").toString()
                val VideoSplitNumber =Mishen.getString("VideoSplitNumber").toString()
                //val VideoSplitNumber ="4"
                if(!VideoSplitNumber.equals("0") && !VideoSplitNumber.equals("") ){
                    val arryFiles=ArrayList<String>()
                    for(index in  1 until  VideoSplitNumber.toInt()+1)
                    {
                        var numberStr=""
                        if(index>9){
                            numberStr="0"+index.toString()
                        }
                        else{
                            numberStr="00"+index.toString()
                        }
                        arryFiles.add(context?.filesDir.toString()+"/GameWatingToStartPlay/" +GameId+"/"+MishenId+".mp4."+numberStr+".split")
                        //arryFiles.add(context?.filesDir.toString()+"/Gamefiles/af1523e3-3582-4d3e-b4b5-0ae263481d1e/" +MishenId+".mp4."+numberStr+".split")
                        //arryFiles.add(context?.filesDir.toString()+"/GamesInUploudProsses/16815371-8ceb-4128-8386-67c1318e908f/" +MishenId+".mp4."+numberStr+".split")
                    }
                    join(arryFiles,context?.filesDir.toString()+"/GameWatingToStartPlay/" +GameId+"/"+MishenId+".mp4",context)
                    //join(arryFiles,context?.filesDir.toString()+"/Gamefiles/af1523e3-3582-4d3e-b4b5-0ae263481d1e/" +MishenId+"_join.mp4",context)
                    //join(arryFiles,context?.filesDir.toString()+"/GamesInUploudProsses/16815371-8ceb-4128-8386-67c1318e908f/" +MishenId+"_join.mp4",context)
                }
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

/////////////////////////////////////////////////////
/*  @Throws(IOException::class)
/  fun join(baseFilename: String,numberParts:Int,context: Context) {
      //val numberParts: Int = getNumberParts(baseFilename)

      // now, assume that the files are correctly numbered in order (that some joker didn't delete any part)
      val out = BufferedOutputStream(FileOutputStream(baseFilename))
      for (part in 1 until numberParts+1) {
          val `in` = Base64ToVideo(BufferedInputStream(FileInputStream("$baseFilename.00$part.split")).toString(),context)
          var b: Int
          while (`in`.read().also { b = it } != -1) out.write(b)
          `in`.close()
      }
      out.close()
  }*/


  fun join(chunkFilePaths: List<String>, outputFilePath: String,context: Context) {
      try {
          val outputFile = File(outputFilePath)
          val outputStream = FileOutputStream(outputFile)

          for (chunkFilePath in chunkFilePaths) {
              val chunkFile = File(chunkFilePath)
              val inputStream = FileInputStream(chunkFile)
              val sizeOfFiles =  1024 * 1024
              //val sizeOfFiles = 512 * 512
              var buffer = ByteArray(sizeOfFiles)
              var bytesRead: Int

              buffer=inputStream.readBytes()

                  val cc=buffer.size
                  val newBuffr=Base64ToVideo( buffer ,context)
                  val a=newBuffr!!.size
                 // val b=bytesRead
                  //outputStream.write(newBuffr, 0, a)
                  outputStream.write(newBuffr, 0, a)
                  //outputStream.write(Base64ToVideo( buffer ,context), 0, bytesRead)


              inputStream.close()
          }

          outputStream.close()

          println("Binary file merging complete.")

      } catch (e: IOException) {
          e.printStackTrace()
      }
  }



  ///////////////////////////////////////////////////////////////////////////

fun moveFile(file: File,DestFile:String){
    val DestFile=File(DestFile)
    file.copyTo(DestFile,true)
    file.delete()
}


  fun writeVideoFileOnInternalStorageGameToUploudProsses( buffer: String, context:Context,FileName:String,GameId:String){
      var file = File(context?.filesDir, "GamesInUploudProsses")
      if (!file.exists()) {
          file.mkdir()
      }

      file = File(context?.filesDir,"GamesInUploudProsses/"+ GameId)
      try {
          if (!file.exists()) {
              file.mkdir()
          }
          //val GameFileName = FileName+".txt"
          //file = File(context?.filesDir,"GamesInUploudProsses/"+ GameId+"/"+GameFileName)
          val fileToWrite = File(file, FileName)
          val writer = FileWriter(fileToWrite)
          writer.write(buffer)
          writer.flush()
          writer.close()
      } catch (e: Exception) {
          //Logger.e(classTag, e)
          var a=1
      }
  }








  fun writeFileOnInternalStorageGameToUploudProsses( Gamejson: JSONObject, context:Context,FileName:String,GameId:String){
      var file = File(context?.filesDir, "GamesInUploudProsses")
      if (!file.exists()) {
          file.mkdir()
      }

      file = File(context?.filesDir,"GamesInUploudProsses/"+ GameId)
      try {
          if (!file.exists()) {
              file.mkdir()
          }

          //file = File(context?.filesDir,"GamesInUploudProsses/"+ GameId+"/"+GameFileName)
          val fileToWrite = File(file, FileName)
          val writer = FileWriter(fileToWrite)
          writer.write(Gamejson.toString())
          writer.flush()
          writer.close()
      } catch (e: Exception) {
          //Logger.e(classTag, e)
          var a=1
      }
  }





  fun writeFileOnInternalStorageGameWatingToPlay( GameStr: String, GameId: String,FileName:String,context:Context){
      var file = File(context?.filesDir, "GameWatingToStartPlay")

      //val GameId=Gamejson.getJSONObject("GameInfo").getString("GameId")

      //val GameFileName = GameId+".txt"
      if (!file.exists()) {
          file.mkdir()
      }

      file = File(context?.filesDir,"GameWatingToStartPlay/"+ GameId)
      try {
          if (!file.exists()) {
              file.mkdir()
          }


          val fileToWrite = File(file, FileName)
          if (!fileToWrite.exists()) {
              val writer = FileWriter(fileToWrite)
              writer.write(GameStr)
              writer.flush()
              writer.close()
          }
      } catch (e: Exception) {
          //Logger.e(classTag, e)
          var a=1
      }
  }



  fun writeFileOnInternalStorageGame(PersonName: String, GameDescription: String, GameKind: String,
                                     PlaceGame: String, Gamesubject: String, GameDateCreate:
                                     String,GameId:String,Remarks:String,updateDate:String,
                                     placeLat:String,placeLong:String,context: Context,
                                     clue:String,InstractionToMishen:String,DontShowMishenPoint:String   ) {
      val file = File(context?.filesDir, "Gamefiles")
      // val f=FileReadWriteService()
      val GameFileName = GameId+".txt"
      try {
          if (!file.exists()) {
              file.mkdir()
          }
          val json = JSONObject()
          try {
              json.put("PersonName", PersonName)
              json.put("GameDescription", GameDescription)
              json.put("GameKind", GameKind)
              json.put("PlaceGame", PlaceGame)
              json.put("Gamesubject", Gamesubject)
              json.put("GameDateCreate", GameDateCreate)
              json.put("GameId", GameId)
              json.put("Remarks", Remarks)
              json.put("updateDate", updateDate)
              json.put("placeLat", placeLat)
              json.put("placeLong", placeLong)
              ///////////////////////////
              val jsonString = "{\"type\":\"Point\"," +
                      "\"coordinates\":["+placeLong+","+placeLat+"]}"
              var obj = JSONObject(jsonString)
              json.put("location",obj)
              json.put("clue",clue)
              json.put("InstractionToMishen",InstractionToMishen)
              json.put("DontShowMishenPoint",DontShowMishenPoint)
              //////////////////////////

              //json.put("offices", listOf("California", "Washington", "Virginia"))
          } catch (e: JSONException) {
              e.printStackTrace()
          }

          val fileToWrite = File(file, GameFileName)
          val writer = FileWriter(fileToWrite)
          writer.append(json.toString())
          writer.flush()
          writer.close()
      } catch (e: Exception) {
          //Logger.e(classTag, e)
          var a=1
      }
  }


  fun CreateDir(DirName:String,context: Context) {
      val file = File(context?.filesDir, DirName)
      if (!file.exists()) {
          file.mkdir()


      }
  }

  @SuppressLint("SuspiciousIndentation")
  fun DeletDir(DirName:String, context: Context) {
      var path="Gamefiles/"+ DirName
      val file = File(context?.filesDir,path)

          file.deleteRecursively()

  }

  fun DeletFile(FileName:String,context: Context) {
      var path="Gamefiles/"+ FileName
      val file = File(context?.filesDir, path)
      file.delete()



  }




  fun writeFileOnInternalStorageGame(GameFileName:String,FolderName:String,Gamejson:JSONObject ,context:Context){
      val file = File(context?.filesDir, FolderName)
      try {
          if (!file.exists()) {
              file.mkdir()
          }


          val fileToWrite = File(file, GameFileName)
          val writer = FileWriter(fileToWrite)
          writer.write(Gamejson.toString())
          writer.flush()
          writer.close()
      } catch (e: Exception) {
          //Logger.e(classTag, e)
          var a=1
      }
  }

  fun Base64ToPicher(encodedImageString: String): Bitmap? {
      try {
          //val base64Image = encodedImageString.split(",")[1]
          val decodedString = Base64.decode(encodedImageString, Base64.DEFAULT)
          val bitmap =
              BitmapFactory.decodeByteArray(decodedString,0,decodedString.size)
          return bitmap
      } catch (e: Exception) {
          return null
      }
  }


  @RequiresApi(Build.VERSION_CODES.O)
  private fun encodeImage(bm: Bitmap?): String? {
      val baos = ByteArrayOutputStream()
      if (bm != null) {
          bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
      }
      val b = baos.toByteArray()
      return java.util.Base64.getEncoder().encodeToString(b)
  }

/////////////////////////////////////
@RequiresApi(Build.VERSION_CODES.O)
  fun VideoToBase64(tempFile:File): String? {

      var encodedString: String? = null

      var inputStream: InputStream? = null
      try {
          inputStream = FileInputStream(tempFile)
      } catch (e: java.lang.Exception) {
          // TODO: handle exception
      }
      val bytes: ByteArray
      val sizeOfFiles = 1024 * 1024
        //val sizeOfFiles = 512 * 512
        val buffer = ByteArray(sizeOfFiles)
      var bytesRead: Int
      val output = ByteArrayOutputStream()
      try {
          while ((inputStream!!.read(buffer).also { bytesRead = it }) != -1) {
              output.write(buffer, 0, bytesRead)
          }
      } catch (e: IOException) {
          e.printStackTrace()
      }
      bytes = output.toByteArray()
      encodedString =java.util.Base64.getEncoder().encodeToString(bytes)
      return encodedString
  }

  @SuppressLint("SuspiciousIndentation")
  fun Base64ToVideo(OrigString: ByteArray, context:Context): ByteArray? {
      val decodedString = Base64.decode(OrigString, Base64.DEFAULT)
      //val decodedBytes: ByteArray = Base64.decodeFast(encodedString.getBytes())
        val ss=decodedString.size
      //writeVideoFileOnInternalStorageGameToUploudProsses(String(decodedString),context,FileName,GameId)
     val cc=decodedString.toString()
      return decodedString

  }


/////////////////////////////////////






  @RequiresApi(Build.VERSION_CODES.O)
  fun PicherToBase64(bm: Bitmap?): String?  {
      try {
          val baos = ByteArrayOutputStream()
          if (bm != null) {
              bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
          }
          val b = baos.toByteArray()
          return java.util.Base64.getEncoder().encodeToString(b)


      } catch (e: Exception) {
          return null
      }
  }




  fun SavePicFromJASONGame(GameInfo:JSONArray,mGameId:String,context:Context){
      val MishensArry = GameInfo.getJSONObject(0).getJSONArray("Mishens")
      var Mishen:JSONObject
      val lenArry=MishensArry.length()-1
      for (index in 0 ..lenArry){
          Mishen= MishensArry[index] as JSONObject
          val pic=Mishen.getString("picStr")
          val PicName=Mishen.getString("picherName")
          if (!pic.equals("")){
              var PicBitMap = Base64ToPicher(pic)
              writeFileOnInternalStorageMishenPic(PicName,"PlayGamefiles//"+mGameId,"",
                  "",PicBitMap!! ,context)
          }

      }

  }




  fun writeFileOnInternalStorageSingelInfo( Info: String,FolderName:String,fileName:String,context:Context) {
      val file = File(context?.filesDir, FolderName)
      try {
          if (!file.exists()) {
              file.mkdir()
          }


          val fileToWrite = File(file, fileName)
          val writer = FileWriter(fileToWrite)
          writer.write(Info)
          writer.flush()
          writer.close()
      } catch (e: Exception) {
          //Logger.e(classTag, e)
          var a=1
      }
  }


////////////////////////////////////////////////////////קריאת קובת תמונה והחזרה של קובץ תמונה///////////
  fun readPpicherFromInternalStorageByPathReturnBitmap(filePath: String, context: Context?): Bitmap {

      var bitmap: Bitmap? =null

      try {
          val options = BitmapFactory.Options()
          options.inPreferredConfig = Bitmap.Config.ARGB_8888
          bitmap = BitmapFactory.decodeFile(filePath, options)

      } catch (e: Exception) {
          var a=1
      }
      return bitmap!!
  }



//////////////////////////////////////////////////////////////////////////////////קורא קבצים לפי המסולל שניתן בשם הקובץ - לקראה של וידאו או כל קובץ בטלפון////////////
  fun readFileOnInternalStorageByPath(fileName: String, context: Context?): String {

      var ret = ""
      try {
          val fileToRead = File(fileName)
          val reader = FileReader(fileToRead)
          ret = reader.readText()
          reader.close()
      } catch (e: Exception) {
          var a=1
      }
      return ret
  }


//////////////////////////////////////////////////////////////////////////////////קורא קבצים מספריית הקבצים של האפליקציה בלבד////////////
  fun readFileOnInternalStorage(fileName: String,FolderName: String,context: Context): String {
      val file = File(context?.filesDir, FolderName)
      var ret = ""
      try {
          if (!file.exists()) {
              return ret
          }
          val fileToRead = File(file, fileName)
          val reader = FileReader(fileToRead)
          ret = reader.readText()
          reader.close()
      } catch (e: Exception) {
          var a=1
      }
      return ret
  }
/////////////////////////////////////////////////////////////////////////////////////////////////////////

  fun CheckIfFileExistInSetings(fileName:String,FolderName:String,context: Context): String {
      val file = File(context?.filesDir, FolderName+"/"+fileName)

      try {
          if (file.exists()) {
              var info=readFileOnInternalStorage(fileName,FolderName,context)
              return info
          }

      } catch (e: Exception) {
          var a=1
      }
      return ""
  }

}