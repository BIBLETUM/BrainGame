package com.example.braingame.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
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

@BindingAdapter("scoredAnswers")
fun bindScoredAnswers(textView: TextView, count: Int) {
    val scoreText = String.format(
        textView.context.getString(R.string.score_answers),
        count
    )
    textView.text = scoreText
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView: TextView, count: Int) {
    val minPercentText = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )
    textView.text = minPercentText
}

@BindingAdapter("scoredPercent")
fun bindScoredPercent(textView: TextView, count: Int) {
    val scorePercentageText = String.format(
        textView.context.getString(R.string.score_percentage),
        count
    )
    textView.text = scorePercentageText
}

@BindingAdapter("isWinner")
fun bindIsWinner(imageView: ImageView, winner: Boolean) {
    val resId = if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
    val drawable = ContextCompat.getDrawable(imageView.context, resId)
    imageView.background = drawable
}

@BindingAdapter("sumNumber")
fun bindSumNumber(textView: TextView, sum: Int) {
    textView.text = sum.toString()
}

@BindingAdapter("visibleNumber")
fun bindVisibleNumber(textView: TextView, visibleNumber: Int) {
    textView.text = visibleNumber.toString()
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enough: Boolean) {
    val color = getColor(textView.context, enough)
    textView.setTextColor(color)
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean) {
    val color = getColor(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString())
    }
}

interface OnOptionClickListener {
    fun onOptionClick(option: String)
}

private fun getColor(context: Context, bool: Boolean): Int {
    val colorResId = if (bool) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}