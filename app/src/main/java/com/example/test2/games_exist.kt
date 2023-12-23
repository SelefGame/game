package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray


class
games_exist(): AppCompatActivity() {

    // receive the value  by getStringExtra() method and            mSocket.emit("GetMain")
    //            mSocket.emit("GetGaems")
    // key must be same which is send by first activity
    //var arrayGamesName1:Array<String>
    var arrayGamesName1 = kotlin.arrayOfNulls<String>(0)

    var language = arrayOf<String>("C","C++","Java",".Net","Kotlin","Ruby","Rails","Python","Java Script","Php","Ajax","Perl","Hadoop")
    val description = arrayOf<String>(
        "C programming is considered as the base for other programming languages",
        "C++ is an object-oriented programming language.",
        "Java is a programming language and a platform.",
        ".NET is a framework which is used to develop software applications.",
        "Kotlin is a open-source programming language, used to develop Android apps and much more.",
        "Ruby is an open-source and fully object-oriented programming language.",
        "Ruby on Rails is a server-side web application development framework written in Ruby language.",
        "Python is interpreted scripting  and object-oriented programming language.",
        "JavaScript is an object-based scripting language.",
        "PHP is an interpreted language, i.e., there is no need for compilation.",
        "AJAX allows you to send and receive data asynchronously without reloading the web page.",
        "Perl is a cross-platform environment used to create network and server-side applications.",
        "Hadoop is an open source framework from Apache written in Java."
    )

    val imageId = arrayOf<Int>(
        R.drawable.test,R.drawable.test,R.drawable.test,
        R.drawable.test,R.drawable.test,R.drawable.test,
        R.drawable.test,R.drawable.test,R.drawable.test,
        R.drawable.test,R.drawable.test,R.drawable.test,
        R.drawable.test
    )




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mName:Array<String> = intent.getStringArrayExtra  ("Name") as Array<String>
        val mDescription:Array<String> = intent.getStringArrayExtra  ("Description") as Array<String>
        var arrayGames = JSONArray()

        //language[0]=mTopic[0]
        //    language[1]=mTopic[1]
        //    language[2]=mTopic[2]
        //array = mTopic?.split(",")
        setContentView(R.layout.games_exist)
        val listView = findViewById<ListView>(R.id.listView)

        var jsonStringGame= intent.getStringExtra("jsonStringGame") as String



        // The following lines connects the Android app to the server.
       // SocketHandler.setSocket()
       // SocketHandler.establishConnection()
       // val mSocket = SocketHandler.getSocket()
        //mSocket.emit("OpenDB")
        //mSocket.emit("GetGaems")

        val myListAdapter = MyListAdapter(this,mName,mDescription,imageId)
        listView.adapter = myListAdapter



        listView.setOnItemClickListener(){adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()


        }
/*
        mSocket.on("OpenDB") { args ->
            if (args[0] != null) {
                val res = args[0] as Boolean
                runOnUiThread {
                    //MainTextView.text = counter.toString()
                }
            }
        }


        fun <String> append(arr: Array<String>, element: String): Array<String?> {
            val array = arr.copyOf(arr.size + 1)
            array[arr.size] = element
            return array
        }


        mSocket.on("GetGaems") { args ->
           // val root = JSONObject(json_string)
           // val array: JSONArray = root.getJSONArray("array")
            if (args[0] != null) {
                val jsonArray =args[0] as JSONArray
                for (i in 0 until jsonArray.length()) {

                    //  Name
                    val employee = jsonArray.getJSONObject(i).getString("userNameCreateRes")
                    val ss=employee
                    language=language+","+employee

                    var namesList = arrayGamesName1.toMutableList()
                    namesList.add(employee)
                    arrayGamesName1 = namesList.toTypedArray()
                    append(arrayGamesName1,employee)

                    val a=arrayGamesName1[0]

                }

                runOnUiThread {
                    //val myListAdapter = MyListAdapter(this,language,description,imageId)
                    //listView.adapter = myListAdapter
                }
            }
        }*/


    }





}
