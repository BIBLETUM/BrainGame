package com.example.braingame.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.braingame.R

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    val minCountOfRightAnswersText = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
    textView.text = minCountOfRightAnswersText
}