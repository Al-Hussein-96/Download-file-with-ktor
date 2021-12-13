package com.alhussein.downloadfilewithktor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alhussein.downloadfilewithktor.download.DownloadResult
import com.alhussein.downloadfilewithktor.download.downloadFile
import com.alhussein.downloadfilewithktor.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DownloadFileViewModel @Inject constructor(private val coroutineScope: CoroutineScope) : ViewModel() {



    private val _url = MutableLiveData("test")
    val url: LiveData<String> = _url

    private val _downloadProgress = MutableLiveData(0)
    val downloadProgress : LiveData<Int> = _downloadProgress

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText



    init {
        Timber.i("DownloadFileViewModel created!")
    }



    override fun onCleared() {
        super.onCleared()
        Timber.i("DownloadFileViewModel destroyed!")

    }

    @ExperimentalCoroutinesApi
    fun onDownload(url: String) {
        val httpClient = HttpClient(Android)

        coroutineScope.launch {
            httpClient.downloadFile(url).collect {
                withContext(Dispatchers.Main){
                    when(it){
                        is DownloadResult.Success -> {
                            _snackbarText.value = Event("Downloaded successfully")

                        }
                        is DownloadResult.Error -> {
                            _snackbarText.value = Event(it.message)

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