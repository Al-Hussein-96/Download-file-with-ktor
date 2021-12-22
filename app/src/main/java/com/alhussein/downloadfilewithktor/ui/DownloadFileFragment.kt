package com.alhussein.downloadfilewithktor.ui

import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.fragment.app.activityViewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.alhussein.downloadfilewithktor.R
import com.alhussein.downloadfilewithktor.databinding.FragmentDownloadFileBinding
import com.alhussein.downloadfilewithktor.utils.setupSnackbar
import com.alhussein.downloadfilewithktor.viewmodel.DownloadFileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class DownloadFileFragment : Fragment() {

    private lateinit var binding: FragmentDownloadFileBinding

    private val viewModel by activityViewModels<DownloadFileViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_download_file, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSnackbar()

        viewModel.downloadProgress.observe(viewLifecycleOwner, {
            binding.progressCircular.progress = it
        })

        binding.buttonPaste.setOnClickListener {
            onPaste()
        }

        binding.downloadButton.setOnClickListener {
            onDownload()
        }
        binding.pauseButton.setOnClickListener {
            viewModel.onPause()
        }
        binding.resumeButton.setOnClickListener {
            viewModel.onResume()
        }


    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)

    }

    private fun onDownload() {
        with(viewModel) { onDownload("https://shaadoow.net/recording/video/vRP8OcKzFBM85q0OrLUpqsPxJkdiUiXLmVRfSylH.mov") }

    }

    private fun onPaste() {
        val dataPaste: String = getDataPaste();

        Timber.i("dataPaste123 $dataPaste")

        binding.uriEditText.setText(dataPaste, TextView.BufferType.EDITABLE)

        viewModel.onPaste(dataPaste)

        Toast.makeText(context, "onPaste", Toast.LENGTH_SHORT).show()
    }

    private fun getDataPaste(): String {
        val myClipboard: ClipboardManager? =
            requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?

        return getStringFromClipboard(myClipboard);
    }

    private fun getStringFromClipboard(myClipboard: ClipboardManager?): String {
        val abc = myClipboard?.primaryClip

        return abc?.getItemAt(0)?.text.toString()
    }


}