package com.alhussein.downloadfilewithktor


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alhussein.downloadfilewithktor.databinding.FragmentDownloadFileBinding
import com.alhussein.downloadfilewithktor.utils.setupSnackbar
import com.alhussein.downloadfilewithktor.viewmodel.DownloadFileViewModel
import com.google.android.material.snackbar.Snackbar


class DownloadFileFragment : Fragment() {
    private lateinit var binding: FragmentDownloadFileBinding

    private lateinit var viewModel: DownloadFileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_download_file, container, false)

        Log.i("Mohammad", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(DownloadFileViewModel::class.java)

        binding.buttonPaste.setOnClickListener {
            onPaste()
        }

        binding.downloadButton.setOnClickListener {
            onDownload()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSnackbar()

        viewModel.downloadProgress.observe(viewLifecycleOwner, {
            binding.progressCircular.progress = it
        })

    }

    private fun setupSnackbar() {
        view?.setupSnackbar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)

    }

    private fun onDownload() {
        with(viewModel) { onDownload("https://shaadoow.net/recording/video/vRP8OcKzFBM85q0OrLUpqsPxJkdiUiXLmVRfSylH.mov") }

    }

    private fun onPaste() {
        viewModel.onPaste()
        binding.uriEditText.setText(viewModel.url.value,TextView.BufferType.EDITABLE)
        Toast.makeText(context,"onPaste",Toast.LENGTH_SHORT).show()
    }


}