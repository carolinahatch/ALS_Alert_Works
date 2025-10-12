//This one should be for creating the three app homepages
package com.samehf.alsalert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val token = task.result
//                // Handle the token, e.g., send it to your server
//            } else {
//                // Handle the error
//                // task.exception?.printStackTrace()
//            }
//        }

        //val intent = Intent(this, connection::class.java)
        //startActivity(intent)

        val homeFragment = homeFragment()
        val connectionFragment = connectionFragment()
        val settingsFragment = settingsFragment()

        //calibration
        val calibrationFragment = calibrationFragment()

        setCurrentFragment(homeFragment)

        var bottomNavigationView = findViewById<NavigationBarView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.connection -> setCurrentFragment(connectionFragment)
                R.id.settings -> setCurrentFragment(settingsFragment)

                //calibrationFragment
                R.id.calibration -> setCurrentFragment(calibrationFragment)

            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}
