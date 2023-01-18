package com.android.smartgoals.adapters

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.smartgoals.R

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("cardExpanded")
        fun setImageViewCardExpanded(view: ImageView, isExpanded: Boolean) {
            val imageSource = if(isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
            view.setImageResource(imageSource)
        }

        @JvmStatic
        @BindingAdapter(value = ["textTitle", "colourFirst"])
        fun setSpannableTitle(view: TextView, text: String, colour: Int) {
            val spannableComponentName = SpannableString(text)

            spannableComponentName.setSpan(AbsoluteSizeSpan(22, true), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableComponentName.setSpan(StyleSpan(Typeface.BOLD), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableComponentName.setSpan(ForegroundColorSpan(colour),0,1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

            view.text = spannableComponentName
        }

        //TODO combine the two together?
        @JvmStatic
        @BindingAdapter(value = ["completedTextTitle", "completedColourFirst"])
        fun setCompletedSpannableTitle(view: TextView, text: String, colour: Int) {
            val spannableComponentName = SpannableString(text)

            spannableComponentName.setSpan(AbsoluteSizeSpan(18, true), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableComponentName.setSpan(StyleSpan(Typeface.BOLD), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableComponentName.setSpan(ForegroundColorSpan(colour),0,1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

            view.text = spannableComponentName
        }
    }
}