package com.example.test2

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import com.example.test2.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

import java.util.*
import kotlin.collections.ArrayList


private const val CAMERA_PERMISSION_CODE = 100
private const val STORAGE_PERMISSION_CODE = 101

private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
private const val MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION = 66
public const val  GET_USER_ID=8723
class MainActivity<string> : AppCompatActivity() {
    private var fusedLocationProvider: FusedLocationProviderClient? = null
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 30
        fastestInterval = 10
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        maxWaitTime = 60
    }
    // Creating firebaseAuth object
    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainBinding: ActivityMainBinding
    private val permissionId = 2
    private var Counter=1
    var arrayGamesName = arrayOfNulls<String>(0)
    var arrayGamesDescription = arrayOfNulls<String>(0)
    var mUserId=""
    var longitude=0.0
    var latitude=0.0
    val f=FileReadWriteService()
    var arrayGames = JSONArray()
    var FlegGetInfoFromServer=false
    var IdToken=""
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient






    // זה המערך שצריך לעדכן שבודק את כל ההרשאות
    val list = listOf<String>(
        Manifest.permission.CAMERA,Manifest.permission.READ_MEDIA_VIDEO,Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.POST_NOTIFICATIONS//,Manifest.permission.READ_EXTERNAL_STORAGE
        //Manifest.permission.READ_EXTERNAL_STORAGE  נותן שגיאה בבקשה להרשאות הללו - צריך לבדוק
    )



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val  languageMnager= LanguageMnager(this)
        languageMnager.UpdateResource("he")







        //recreate()

        /*val file = File(filesDir.toString() +"/Gamefiles/"+"af1523e3-3582-4d3e-b4b5-0ae263481d1e/16815371-8ceb-4128-8386-67c1318e908f.mp4")
        if (file.exists() ) {
            val CountSplitFiles=f.splitFile(file)
        }

        val f = FileReadWriteService()
        f.jonFiles("16815371-8ceb-4128-8386-67c1318e908f",this)


        val directory = File(filesDir, "Gamefiles/af1523e3-3582-4d3e-b4b5-0ae263481d1e")

        var files: Array<File> = directory.listFiles()
        var index=0
        files = directory.listFiles()
        for (i in files.indices) {
            val fileName = files[i].toString().split("/")
            val len=fileName.size
            if (fileName[len-1].contains(".split")) {
                val buffer = f.VideoToBase64(File(files[i].toString()))
                f.writeVideoFileOnInternalStorageGameToUploudProsses(
                    buffer!!,
                    this,
                    fileName[len-1],
                    "16815371-8ceb-4128-8386-67c1318e908f"
                )
                //files[i].delete()
                //f.CopyFile(files[i].toString(),"/GamesInUploudProsses/"+mGameId+"/"+fileName[8],context)
            }
        }
        f.jonFiles("16815371-8ceb-4128-8386-67c1318e908f",this)
        */




        checkPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            STORAGE_PERMISSION_CODE)
        checkPermission(
            Manifest.permission.CAMERA,
            CAMERA_PERMISSION_CODE)
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()
        //checkInternalStoragPremissen()


        // Initialize a new instance of ManagePermissions class
        managePermissions = ManagePermissions(this,list,PermissionsRequestCode)
        managePermissions.checkPermissions()
        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@MainActivity)
        CreateFoldersAplication()
        //put in remark for working in amulator only
        CheckUSerLogin()



        fun CreateUserId(mContex:Context){
            var uniqueID = UUID.randomUUID().toString()
            f.writeFileOnInternalStorageSingelInfo(uniqueID,"setings","UserId.txt",mContex)
            mUserId=uniqueID
        }


        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener {
            if (it != null) {
                currentLocation = it
                longitude =currentLocation.longitude
                latitude=currentLocation.latitude
                Toast.makeText(
                    applicationContext, currentLocation.latitude.toString() + "" +
                            currentLocation.longitude, Toast.LENGTH_SHORT
                ).show()

            }
        }




        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        ///mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        // The following lines connects the Android app to the server.
        SocketHandler.setSocket()

        SocketHandler.establishConnection()
        //11.72864 MB



        val mSocket = SocketHandler.getSocket()

        val res=mSocket!!.connected()
        if (res==false)
        {
            val a=1
        }

        //mSocket.emit("SendPicherFromServerToUser","","")
        val linearStart: CardView = findViewById(R.id.Start)
        val selectGameExist: CardView = findViewById(R.id.Games_exist)
        val create_game_main_screan: CardView = findViewById(R.id.main_create_game)
        val main_settings: CardView = findViewById(R.id.main_settings)

        ///getLocation()
        //val button: Button = findViewById(R.id.button)
        //val MainTextView = findViewById<TextView>(R.id.maintextView)
        //mSocket.emit("GetMain")
        //button.setOnClickListener {
            // Do something in response to button click
            //mSocket.emit("OpenDB")
            //mSocket.emit("counter1")
            //mSocket.emit("GetMain")
            //mSocket.emit("GetGaemsFromServer",latitude,longitude)
        //}

        ///// wating to get info from server
/**      val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Geting info from server");
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Horizontal
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Progress Dialog Max Value
        progressDialog.setMax(100);
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

val dialogBuilder = AlertDialog.Builder(this)



            val index = 0
            var NumberTocheck= index+2
            Thread(Runnable() {
                run  {
                    for (index in 1..20) {
                        try {
                            if (arrayGames != null && arrayGames.length() != 0) {
                                progressDialog.dismiss();
                                return@run
                            }
                            Thread.sleep(6000);
                        } catch (e: Exception) {
                            e.printStackTrace();
                        }
                        if(index==NumberTocheck){
                            runOnUiThread{
                            NumberTocheck= index+2
                            // set message of alert dialog
                            dialogBuilder.setMessage("No Internet , Check zoers conection !")
                                // if the dialog is cancelable
                                .setCancelable(false)
                                // positive button text and action
                                .setPositiveButton("try egan", DialogInterface.OnClickListener { dialog, id ->

                                })
                                // negative button text and action
                                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                                        dialog, id -> dialog.cancel()
                                        finish()
                                })
                        // create dialog box
                        val alert = dialogBuilder.create()
                        // set title for alert dialog box
                        alert.setTitle("No interanet")

                        // show alert dialog
                        alert.show()}}

                    }
                }
            }).start();
            //exit the program if didnt get info from server
            if(index==20){
                finish()
            }

**/




        ////

        main_settings.setOnClickListener{
           // val intent = Intent(this, Activity_Screan_to_get_Video::class.java)
            val intent = Intent(this, uploaud_Files::class.java)
            intent.putExtra("Long",longitude.toString())
            intent.putExtra("Lat",latitude.toString())
            intent.putExtra("mUserId",mUserId)


            startActivity(intent)
        }




///////////////////////////////////////////////////////////////////////////////////////////
// when the user select to show the existing games ,show mapgame activity
        selectGameExist.setOnClickListener{
            // for google maps
            //val intent = Intent(this, show_exist_games_and_mishens_to_play_on_map_main_screen::class.java)
            // for free maps
            val intent = Intent(this, MapsActivity1 ::class.java)

            intent.putExtra("Long",longitude.toString())
            intent.putExtra("Lat",latitude.toString())
            intent.putExtra("mUserId",mUserId)


            startActivity(intent)
        }

///////////////////////////////////////////////////////////////////////////////////////////////////
// when the user select to create new game  , show the create game activity
        create_game_main_screan.setOnClickListener{
            //mSocket.emit("GetMain")
            //mSocket.emit("GetGaems","aaa")
            val intent = Intent(this, Create_Or_Update_Game::class.java)
            intent.putExtra("UserId",mUserId)
             startActivity(intent)

        }
///////////////////////////////////////////////////////////////////////////////////////////////////

        linearStart.setOnClickListener {

            val intent = Intent(this, Start_Play_SelectGame::class.java)
            intent.putExtra("UserId",mUserId)
            //intent.putExtra("GameId","Nedd ToCheck From StartGame Colection")
            intent.putExtra("Long",longitude.toString())
            intent.putExtra("Lat",latitude.toString())
            startActivity(intent)


        }
//////////////////////////////////////////////////////////////////////////////////

        fun byteArrayToBitmap(data: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(data, 0, data.size)
        }




        ///////////// קבלת תמונות מהשרת במידה ויש במשחק תמונות
        SocketHandler.mSocket.on("SendPicherFromServerToUser") { args ->
            if (args[0] != null) {
                // val res = args[0]
                val f=FileReadWriteService()
                val res=args[0] as JSONObject
                val buf = res.get("PicherAsString") as ByteArray
                val FileName=res.getString("file")
                val GameId=res.getString("mGameId")

                if(FileName.contains(".jpg")) {
                    val Bitmap = f.Base64ToPicher(String(buf))
                    // val picStr=Base64.getDecoder().decode(buf)
                    //val Bitmap=byteArrayToBitmap(picStr)
                    if (Bitmap != null) {
                        f.writePicherOnInternalStorage(FileName, GameId, Bitmap!!, this)
                        val ListFilesInDeir = f.GetListFilesInFolder(
                            this.filesDir.toString() + "/GameWatingToStartPlay/" + GameId + "/")
                        //SocketHandler.mSocket.emit("MishenFilesReciveFromServerForGame",mUserId,GameId,ListFilesInDeir)
                    }
                }
                else{
                    if(FileName.contains(".mp4")) {
                        f.writeFileOnInternalStorageGameWatingToPlay( String(buf), GameId, FileName, this)
                        val ListFilesInDeir = f.GetListFilesInFolder(
                            this.filesDir.toString() + "/GameWatingToStartPlay/" + GameId + "/"
                        )
                        //SocketHandler.mSocket.emit("MishenFilesReciveFromServerForGame",mUserId,GameId,ListFilesInDeir)
                    }
                    else{
                        if(FileName.contains(".txt")) {
                            f.writeFileOnInternalStorageGameWatingToPlay( String(buf), GameId, FileName, this)
                            val ListFilesInDeir = f.GetListFilesInFolder(
                                this.filesDir.toString() + "/GameWatingToStartPlay/" + GameId + "/"
                            )
                        }
                    }
                }
                runOnUiThread {
                    //MainTextView.text = counter.toString()
                }
            }
        }

        /*mSocket.on("SendPicherFromServerToUser") { args ->
            if (args[0] != null) {
                val res = args[0] as JSONObject
                val f=FileReadWriteService()
                val buf = res.getString("PicherAsString")
                val FileName = res.getString("file")
                val Bitmap=f.Base64ToPicher(buf)
               // val picStr=Base64.getDecoder().decode(buf)
                //val Bitmap=byteArrayToBitmap(picStr)
                if (Bitmap!=null) {
                    f.writePicherOnInternalStorage(FileName, "system", Bitmap!!, this)
                }
                    runOnUiThread {
                    //MainTextView.text = counter.toString()
                }
            }
        }*/


        //val navView: BottomNavigationView = binding.navView

        //val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // If you have multiple activities returning results then you should include unique request codes for each
        //if (requestCode == GET_USER_ID) {

            // The result code from the activity started using startActivityForResults
            if (resultCode == GET_USER_ID) {
                mUserId=intent.getStringExtra("IdToken").toString()
            }
        //}
    }

    fun CheckUSerLogin():Boolean {
        // initialising Firebase auth object
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
       // val intent = Intent(this, login::class.java)
        //startActivity(intent)
        if (user != null) {
            //User is Logged in
            mUserId= auth.getCurrentUser()?.getUid().toString();
            //val intent = Intent(this, login::class.java)
            //startActivity(intent)
            return true
        } else {
            val intent = Intent(this, login::class.java)
            startActivityForResult(Intent(this@MainActivity, login::class.java), 1234)
            //startActivity(intent)
            return  true
        }

    return false


    }


/*
    @SuppressLint("MissingPermission", "SetTextI18n")
    public fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        mainBinding.apply {
                            var tvLatitude = "Latitude\n${list[0].latitude}"
                            var tvLongitude = "Longitude\n${list[0].longitude}"
                            var tvCountryName = "Country Name\n${list[0].countryName}"
                            var tvLocality = "Locality\n${list[0].locality}"
                            var tvAddress = "Address\n${list[0].getAddressLine(0)}"
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )


    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }


    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }*/




    fun CreateFoldersAplication(){
        val f=FileReadWriteService()
        f.CreateDir("Gamefiles",this)
    }
////////////////////////// gps premishen //////////////////////////////////////
/**
override fun onResume() {
    super.onResume()
    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED
    ) {

        fusedLocationProvider?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}

    override fun onPause() {
        super.onPause()
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {

            fusedLocationProvider?.removeLocationUpdates(locationCallback)
        }
    }
 **/

    private fun checkInternalStoragPremissen(){

        /*
        if( Environment.isExternalStorageManager()){

        }
        else{
            val intent=Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data=Uri.parse("pakage:"+"com.example.test2")
            startActivity(intent)
        }*/
    }


    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        //Prompt the user once explanation has been shown
                        requestLocationPermission()
                    }
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                requestLocationPermission()
            }
        } else {
            //checkBackgroundLocation()
        }
    }

    private fun checkBackgroundLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestBackgroundLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            MY_PERMISSIONS_REQUEST_LOCATION
        )
    }

    private fun requestBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ),
                MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {/**
                        fusedLocationProvider?.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.getMainLooper()
                        )

                        // Now check background location
                        checkBackgroundLocation()**/
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()

                    // Check if we are in a state where the user has denied the permission and
                    // selected Don't ask again
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this.packageName, null),
                            ),
                        )
                    }
                }
                return
            }
            MY_PERMISSIONS_REQUEST_BACKGROUND_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {/**
                        fusedLocationProvider?.requestLocationUpdates(
                            locationRequest,
                            locationCallback,
                            Looper.getMainLooper()
                        )

                        Toast.makeText(
                            this,
                            "Granted Background Location Permission",
                            Toast.LENGTH_LONG
                        ).show()**/
                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
                return

            }
            CAMERA_PERMISSION_CODE ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
                }


            }

            STORAGE_PERMISSION_CODE ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }
    //////////////////////////////////////////////////////////
// Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }



////////////////////////////////////////////////////////////////

}
