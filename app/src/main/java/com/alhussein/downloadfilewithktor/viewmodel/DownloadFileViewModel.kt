package com.alhussein.downloadfilewithktor.viewmodel

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alhussein.downloadfilewithktor.download.DownloadResult
import com.alhussein.downloadfilewithktor.download.Downloading
import com.alhussein.downloadfilewithktor.download.downloadFile
import com.alhussein.downloadfilewithktor.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DownloadFileViewModel @Inject constructor(
    private val coroutineScope: CoroutineScope
) : ViewModel() {
    private var downloading: Downloading = Downloading()


    private val _url = MutableLiveData("test")
    val url: LiveData<String> = _url

    private val _downloadProgress = MutableLiveData(0)
    val downloadProgress: LiveData<Int> = _downloadProgress

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    private val _stopButton = MutableLiveData<Event<Boolean>>(Event(false))
    val stopButton: LiveData<Event<Boolean>> = _stopButton


    init {
        Timber.i("DownloadFileViewModel created!")
    }


    override fun onCleared() {
        super.onCleared()
        Timber.i("DownloadFileViewModel destroyed!")

    }

    @ExperimentalCoroutinesApi
    fun onDownload(url: String) {

        _stopButton.value = Event(true)

        val httpClient = HttpClient(Android)
        coroutineScope.launch {
            httpClient.downloadFile("https://shaadoow.net/recording/video/vRP8OcKzFBM85q0OrLUpqsPxJkdiUiXLmVRfSylH.mov", downloading).collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is DownloadResult.Success -> {
                            _snackbarText.value = Event("Downloaded successfully")

                        }
                        is DownloadResult.Error -> {
                            _snackbarText.value = Event(it.message)

                        }
                        is DownloadResult.Progress -> {
                            println("Download Progress: ${it.progress}")
                            _downloadProgress.value = it.progress
                        }
                    }

                }
            }
        }


    }

    fun onPause() {
        downloading.pause()
    }

    fun onResume() {
        downloading.resume()
    }


    fun onPaste(dataPaste: String) {
        _url.value = dataPaste
    }


}