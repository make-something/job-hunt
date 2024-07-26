package com.example.jobhunt

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {


    private lateinit var btnSelectFile: Button
    private lateinit var tvSelectedFile: TextView
    private lateinit var btnUploadFile: Button

    private var selectedFileUri: Uri? = null
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        btnSelectFile = findViewById(R.id.btnSelectFile)
        tvSelectedFile = findViewById(R.id.tvSelectedFile)
        btnUploadFile = findViewById(R.id.btnUploadFile)

        btnSelectFile.setOnClickListener {
            selectFile()
        }

        btnUploadFile.setOnClickListener {
            selectedFileUri?.let { uri ->
                uploadFile(uri)
            }
        }
        //bottom navbar//
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.selectedItemId = R.id.bottom_profile
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true}
                R.id.bottom_search -> {
                    startActivity(Intent(applicationContext, SearchActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    finish()
                    true
                }
                R.id.bottom_profile -> {

                    true
                }
                else -> false
            }
        }
    }

    private fun selectFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_FILE && resultCode == Activity.RESULT_OK) {
            selectedFileUri = data?.data
            tvSelectedFile.text = selectedFileUri?.path ?: "No file selected"
        }
    }
    private fun uploadFile(fileUri: Uri) {
        val file = File(fileUri.path!!)
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                file.name,
                RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file)
            )
            .build()

        val request = Request.Builder()
            .url("https://jobhunt-0761-default-rtdb.firebaseio.com/")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Handle failure
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                // Handle success
            }
        })
    }

    companion object {
        private const val REQUEST_CODE_SELECT_FILE = 1
    }



}