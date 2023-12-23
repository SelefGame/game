package com.example.test2

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class main_create_game(): AppCompatActivity()  {



    var language = arrayOf<String>("הסף משימה עם שאולות","משימת צילום","Java",".Net","Kotlin","Ruby","Rails","Python","Java Script","Php","Ajax","Perl","Hadoop")
    val description = arrayOf<String>(
        "משימה בה יש שאלות ןצריך לרשום שאלה ותשובה",
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
        //var mName:Array<String> = intent.getStringArrayExtra  ("Name") as Array<String>

        //val mDescription:Array<String> = intent.getStringArrayExtra  ("Description") as Array<String>

        //language[0]=mTopic[0]
        //    language[1]=mTopic[1]
        //    language[2]=mTopic[2]
        //array = mTopic?.split(",")
        setContentView(R.layout.games_exist)
        val listView = findViewById<ListView>(R.id.listView)

        // The following lines connects the Android app to the server.
        SocketHandler.setSocket()
        SocketHandler.establishConnection()
        val mSocket = SocketHandler.getSocket()
        //mSocket.emit("create_game_list_kind")
        //mSocket.emit("GetGaems")

        val myListAdapter = MyListAdapter(this,language,description,imageId)
        listView.adapter = myListAdapter



        listView.setOnItemClickListener(){adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()
            if(itemIdAtPos.toInt()==0){
                val intent = Intent(this, create_mishen_kind_1_1_radiobutten::class.java)
                startActivity(intent)
            }
        }

    }



}