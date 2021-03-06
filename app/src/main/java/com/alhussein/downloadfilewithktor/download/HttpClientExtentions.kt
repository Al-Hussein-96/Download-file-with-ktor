package com.alhussein.downloadfilewithktor.download


import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.sync.Mutex
import timber.log.Timber
import java.io.File


@ExperimentalCoroutinesApi
suspend fun HttpClient.downloadFile(url: String,downloading: Downloading): Flow<DownloadResult> {

    return callbackFlow {
        try {
            val client = HttpClient(Android)
            val file = File.createTempFile("files", "index")

            client.get<HttpStatement>(url).execute { httpResponse ->
                val channel: ByteReadChannel = httpResponse.receive()
                while (!channel.isClosedForRead) {

                    val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                    while (!packet.isEmpty) {
                        downloading.suspendIfNeeded()
                        val bytes = packet.readBytes()
                        file.appendBytes(bytes)

                        val percent: Int =
                            (file.length() * 100 / httpResponse.contentLength()!!).toInt()


                        trySend(DownloadResult.Progress(percent))
                        println("Received ${file.length()} bytes from ${httpResponse.contentLength()}")
                    }
                }
                if (httpResponse.status.isSuccess()) {
                    trySend(DownloadResult.Success)
                } else {
                    trySend(DownloadResult.Error("File not downloaded"))
                }
                println("A file saved to ${file.path}")
            }

        } catch (e: TimeoutCancellationException) {
            trySend(DownloadResult.Error("Connection timed out"))
        } catch (t: Throwable) {
            trySend(DownloadResult.Error("Failed to connect"))
        }
        awaitClose()

    }

}
class Downloading {
    private val mutex = Mutex(locked = false)
    private var stopped: Boolean = false
    fun pause() {
        stopped = true
        Timber.d("Mohammad pause: $stopped")
    }
    fun resume() {
        stopped = false
        mutex.unlock()
    }
    suspend fun suspendIfNeeded() {
        if (stopped) {
            mutex.lock()
        }
    }
}