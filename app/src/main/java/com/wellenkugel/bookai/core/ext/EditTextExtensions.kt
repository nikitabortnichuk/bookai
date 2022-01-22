package com.wellenkugel.bookai.core.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onTextChange(onTextChanged: (text: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {

        override fun afterTextChanged(editable: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s.toString())
        }
    })
}