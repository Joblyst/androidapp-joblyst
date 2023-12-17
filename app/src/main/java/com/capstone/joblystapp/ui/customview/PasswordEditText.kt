package com.capstone.joblystapp.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.capstone.joblystapp.R

class PasswordEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                error = if (s.toString().length < 8) {
                    context.getString(R.string.error_password)
                } else {
                    null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
