package com.example.catans.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.catans.R
import com.google.android.material.snackbar.BaseTransientBottomBar

class CustomSnackBar(
    parent: ViewGroup,
    content: CustomSnackBarView
) : BaseTransientBottomBar<CustomSnackBar>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {

        fun make(viewGroup: ViewGroup): CustomSnackBar {
            val customView = LayoutInflater.from(viewGroup.context).inflate(
                R.layout.custom_snack_bar,
                viewGroup,
                false
            ) as CustomSnackBarView

            // customView.tvMessage.text = "Some Custom Message"

            return CustomSnackBar(viewGroup, customView)
        }

    }

}