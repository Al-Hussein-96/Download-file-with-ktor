package com.alhussein.downloadfilewithktor

import android.content.ClipData
import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
//    private var myClipboard: ClipboardManager? = null
//    private var myClip: ClipData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?;
//
//        pasteText()

        /// url https://shaadoow.net/recording/video/vRP8OcKzFBM85q0OrLUpqsPxJkdiUiXLmVRfSylH.mov
    }
    // on click copy button
//    fun pasteText() {
//        val abc = myClipboard?.primaryClip
//        val item = abc?.getItemAt(0)
//
//
//        Toast.makeText(applicationContext, item?.text.toString(), Toast.LENGTH_SHORT).show()
//    }
}