package com.samehf.alsalert

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class CalibrationInstructionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calibration_instructions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

            }
    }
}

//Exit Instructions
class CalibrationInstructionActivity : Fragment(R.layout.activity_calibration_instructions) {

    private lateinit var listener: ListenerRegistration
    private lateinit var listener2: ListenerRegistration
    private val db = FirebaseFirestore.getInstance()
    private var clickHandled = false
    private lateinit var uid: String
//
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
//
//
    val leaveButton = view.findViewById<Button>(R.id.leaveButton)
//
//
    val preferences =
        requireActivity().getSharedPreferences("LoginPrefs", AppCompatActivity.MODE_PRIVATE)
    uid = preferences.getString("uid", null).toString()

    //leave calibration
    leaveButton.setOnClickListener {

        ///This part make use of exit button
    //        val intent = Intent(activity, Home::class.java)
    //        startActivity(intent)
//        @Override
//        public void onClick(View view) {
//
//            Intent intent = new Intent(CalibrationInstructionsActivity.this,Home.class);
//            startActivity(intent);
//        }

// Leave calibration
        //leaveButton.setOnClickListener {

                // Get the Activity context from the View's context
                //        val context = it.context
                //          val intent = Intent(context, Home::class.java)
//            context.startActivity(intent)
            }


        }


    }



