package com.example.test2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class missen  {

    var mKind=""
    var mGameId=""
    var mQwesten=""
    var manser1 = ""
    var manser2 = ""
    var manser3 = ""
    var manser4 = ""
    var mAnserQwestenIs=ArrayList<String>()
    var mlat = ""
    var mlong= ""
    var mCoordinates=arrayOf(1.000000,1.00000)
    var mremarks= ""
    var mNewMishen=true
    var mMishenId=""
    var mPlaceOrderOfMissen="0"
    var mpicherName:String=""
    var mpicJpeg:Bitmap? =null
    var mVidioPath=""
    val mfileWrite=FileReadWriteService()
    lateinit var mContex: Context
    var mclue = ""
    var mInstractionToMishen = ""
    var mDontShowMishenPoint = ""


    fun missen(mKind:String,mGameId:String){
        this.mKind= mKind
        this.mGameId=mGameId
        this.mQwesten=mQwesten
        this.manser1 =manser1
        this.manser2 =manser2
        this.manser3 =manser3
        this.manser4 =manser4
        this.mAnserQwestenIs=mAnserQwestenIs
        this.mlat =mlat
        this.mlong=mlong
        this.mremarks=mremarks
        this.mNewMishen=mNewMishen
        this.mMishenId=mMishenId
        this.mPlaceOrderOfMissen=mPlaceOrderOfMissen
        this.mpicherName=mpicherName
        this.mVidioPath=mVidioPath
        this.mContex=mContex
        this.mclue = mclue
        this.mInstractionToMishen = mInstractionToMishen
        this.mDontShowMishenPoint = mDontShowMishenPoint
    }

    fun GetQwesten(): String {return mQwesten}

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation")
    fun addInfo(
        kind:String="",
        gameId:String="",
        Qwesten:String="",
        anser1:String="",
        anser2:String="",
        anser3:String="",
        anser4:String="",
        AnserQwestenIs:ArrayList<String>,
        lat:String="",
        long:String="",
        remarks:String="",
        MishenId:String="",
        NewMishen:Boolean=true,
        pichername:String="",
        picJpeg: Bitmap? =null,
        VidioPath:String="",
        PlaceOrderOfMissen:String="",
        Contex: Context,
        clue:String,
        InstractionToMishen:String,
        DontShowMishenPoint:String ){
        mKind=kind
        mGameId=gameId
        mQwesten=Qwesten
        manser1 = anser1
        manser2 = anser2
        manser3 = anser3
        manser4 = anser4
        mAnserQwestenIs=AnserQwestenIs
        mlat = lat
        mlong= long
        mCoordinates[0]=mlong.toDouble()
        mCoordinates[1]=mlat.toDouble()
        mremarks= remarks
        mNewMishen=NewMishen
        mMishenId=MishenId
        mPlaceOrderOfMissen=PlaceOrderOfMissen
        mpicherName=pichername
        if(Contex!=null){
            mContex=Contex
        }
        //if (mNewMishen==true)
        //    if (mMishenId.equals("")) {
        //        mMishenId= randomUUID().toString()
        if(picJpeg!=null)
            mpicherName=mMishenId+".jpg"
        mpicJpeg=picJpeg
        mVidioPath=VidioPath
        mclue=clue
        mInstractionToMishen=InstractionToMishen
        mDontShowMishenPoint=DontShowMishenPoint



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodeImage(bm: Bitmap?): String? {
        val baos = ByteArrayOutputStream()
        if (bm != null) {
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        }
        val b = baos.toByteArray()
        return Base64.getEncoder().encodeToString(b)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun AddMishen(){

            val file = File(mContex.filesDir, "Gamefiles//"+mGameId)
            if (!file.exists()) {
                file.mkdir()
            }
        var picStr =""
        //if (mpicJpeg!=null) {
            //הפיכת התמונה למחרוזת בסיס 64
        //    picStr = encodeImage(mpicJpeg)!!
        //}
        //בסיס 64 לוידאו עם קיים
        /*if (!mVidioPath.equals("")){
            val vidiobuffer=mfileWrite.readFileOnInternalStorageByPath(mVidioPath,mContex)
            val vedioBase64=Base64.getEncoder().encode(vidiobuffer.toByteArray())
        }*/


        if (mNewMishen == true){

                mfileWrite.writeFileOnInternalStorageMishen1(mMishenId+".txt","Gamefiles//"+mGameId,mMishenId,
                     mKind,mQwesten,manser1,manser2,manser3,manser4,mAnserQwestenIs,mlat,mlong,mCoordinates,mremarks,mGameId,
                    mNewMishen,mpicherName,picStr,"",mVidioPath,"",mPlaceOrderOfMissen,mContex,
                    mclue,mInstractionToMishen,mDontShowMishenPoint)
            }
            else{
                mfileWrite.writeFileOnInternalStorageMishenUpdate1("Gamefiles//"+mGameId,mMishenId,mKind,
                    mQwesten,manser1,manser2,manser3,manser4,mAnserQwestenIs,
                    mlat,mlong,mCoordinates,mremarks,mGameId,mNewMishen,mpicherName,picStr,"",mVidioPath,"",mPlaceOrderOfMissen
                    ,mContex,mclue,mInstractionToMishen,mDontShowMishenPoint)
            }

        }

}