package com.alhussein.downloadfilewithktor.utils

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {


    @Suppress("unused")
    @BindingAdapter("goneUnless")
    @JvmStatic fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }


}