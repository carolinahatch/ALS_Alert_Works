package com.samehf.alsalert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale










// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [calibrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class calibrationFragment : Fragment(R.layout.fragment_calibration) {

    //Do some calibration
    private lateinit var listener: ListenerRegistration
    private lateinit var listener2: ListenerRegistration
    private val db = FirebaseFirestore.getInstance()
    private var clickHandled = false
    private lateinit var uid: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //calibration
        val startCalButton = view.findViewById<Button>(R.id.startCalButton)

        val preferences =
            requireActivity().getSharedPreferences("LoginPrefs", AppCompatActivity.MODE_PRIVATE)
        uid = preferences.getString("uid", null).toString()

        //calibration
        startCalButton.setOnClickListener {

            ///This part will open the new page, hopefully
            //val intent = Intent(activity, CalibrationInstructionsActivity::class.java)
            // startActivity(intent)

            if (uid != null) {
                db.collection("ALSAlert").document(uid)
                    .update("startCal", "1")


            }
        }
        if (uid != null) {
            listener2 = db.collection("ALSAlert").document(uid)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        // Handle errors
                        return@addSnapshotListener
                    }


                    if (snapshot != null && snapshot.exists()) {
                        val lastConnectTime = snapshot.getTimestamp("lastConnection")
                        if (lastConnectTime != null) {
                            // Use the updated value of lastConnection here
                            // For example, set it to a TextView
                            val dateFormat =
                                SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.getDefault())
                            val lastConnectTimeFormatted =
                                dateFormat.format(lastConnectTime.toDate())

                            // wifiTestText.text = lastConnectTimeFormatted
                        } else {
                            // Handle the case when "lastConnection" field is null
                        }
                    } else {
                        // Handle the case when document doesn't exist
                    }
                }
        }

    }

    fun sendHttpRequest(url: String, onSuccess: (String) -> Unit, onError: (Exception) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = reader.use(BufferedReader::readText)

                onSuccess(response)

                reader.close()
                inputStream.close()
                connection.disconnect()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun checknow() {
        var ipaddress: String
        db.collection("ALSAlert").document(uid)
            .get(Source.DEFAULT).addOnCompleteListener {

                ipaddress = it.result.data?.get("ipaddress").toString()
//                        Log.d("ipaddres", ipaddress)
                val url = "http://${ipaddress}:80/checknow"
                sendHttpRequest(url,
                    onSuccess = { response ->
                        // Handle successful response
                        Log.d("Response", response)
                    },
                    onError = { error ->
                        // Handle error
                        error.printStackTrace()
                    }
                )
            }
    }
}



//
//
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_calibration, container, false)
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment calibrationFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            calibrationFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
//}
