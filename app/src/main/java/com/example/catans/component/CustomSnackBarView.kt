package com.example.catans.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.catans.R
import com.google.android.material.snackbar.ContentViewCallback

class CustomSnackBarView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defaultStyle), ContentViewCallback {

    init {
        View.inflate(context, R.layout.item_snackbar, this)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        // TODO("Use some animation")
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        // TODO("Use some animation")
    }

}