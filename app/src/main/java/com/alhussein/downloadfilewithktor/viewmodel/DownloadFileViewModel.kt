package com.alhussein.downloadfilewithktor.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alhussein.downloadfilewithktor.download.DownloadResult
import com.alhussein.downloadfilewithktor.download.downloadFile
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class DownloadFileViewModel : ViewModel() {

    private val _url = MutableLiveData("test")
    val url: LiveData<String> = _url

    private val _downloadProgress = MutableLiveData(0)
    val downloadProgress : LiveData<Int> = _downloadProgress



    init {
        Log.i("Mohammad", "DownloadFileViewModel created!")
    }



    override fun onCleared() {
        super.onCleared()
        Log.i("Mohammad", "DownloadFileViewModel destroyed!")

    }

    @ExperimentalCoroutinesApi
    fun onDownload(url: String) {

        val ktor = HttpClient(Android)



        CoroutineScope(Dispatchers.IO).launch {
            ktor.downloadFile("https://shaadoow.net/recording/video/vRP8OcKzFBM85q0OrLUpqsPxJkdiUiXLmVRfSylH.mov").collect {
                withContext(Dispatchers.Main){
                    when(it){
                        is DownloadResult.Success -> {

                        }
                        is DownloadResult.Error -> {

                        }
                        is DownloadResult.Progress -> {
                            println("Download Progress: ${it.progress}" )
                            _downloadProgress.value = it.progress
                        }
                    }

                }
            }
        }

    }


    fun onPaste() {
        val paste = getPasteText()

        _url.value = paste
    }

    private fun getPasteText(): String {


        return ""

    }
}