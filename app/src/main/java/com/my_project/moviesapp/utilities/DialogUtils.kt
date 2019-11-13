package com.my_project.moviesapp.utilities

import android.content.Context
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.LifecycleOwner
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.my_project.moviesapp.R


/**
 * Created Максим on 27.10.2019.
 * Copyright © Max
 */
class DialogUtils (val context: Context) {

    fun showNoConnect() {
        MaterialDialog(context).show {
            title(R.string.no_connect)
            message(R.string.no_connect_message)
            cornerRadius(16f)
            icon(R.drawable.ic_signal_wifi_off)
            positiveButton(R.string.ok_value) { dialog ->
                dialog.dismiss()
            }
        }
    }


    fun showDetailMessage(owner:LifecycleOwner,showVideo: () -> Unit, showReview: () -> Unit) {
        MaterialDialog(context).show {
            cornerRadius(16f)
            customView(R.layout.dialog_layout, scrollable = true)
            val showVideoButton= getCustomView().findViewById<AppCompatButton>(R.id.showVideoButton)
            val showReviewButton= getCustomView().findViewById<AppCompatButton>(R.id.showReviewButton)
            showVideoButton.setOnClickListener {
                showVideo()
                dismiss()
            }
            showReviewButton.setOnClickListener {
                showReview()
                dismiss()
            }
            negativeButton(R.string.cancel) { dialog -> dialog.dismiss() }
            lifecycleOwner(owner)
        }
    }

    fun showMessage(title: String, message: String, action: () -> Unit? = { null }) {
        MaterialDialog(context).show {
            title(null, title)
            message(null, message, null)
            cornerRadius(16f)
            positiveButton(R.string.cancel_value) { dialog ->
                dialog.dismiss()
            }
            negativeButton(R.string.ok_value) { dialog ->
                action()
                dialog.dismiss()
            }
        }
    }

    fun showBottomSheet(title: String, message: String, action: () -> Unit? = { null }) {
        MaterialDialog(context, BottomSheet()).show {
            title(null, title)
            message(null, message, null)
            cornerRadius(16f)
            positiveButton(R.string.ok_value) { dialog ->
                action()
                dialog.dismiss()
            }
        }
    }

}