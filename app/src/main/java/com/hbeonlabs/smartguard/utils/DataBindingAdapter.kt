package com.hbeonlabs.smartguard.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.imageview.ShapeableImageView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@BindingAdapter("bgColor")
fun ConstraintLayout.setBackgroundColor(color: Int) {
    this.setBackgroundColor(color)
}



