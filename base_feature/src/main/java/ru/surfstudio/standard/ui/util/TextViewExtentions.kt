package ru.surfstudio.standard.ui.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING


/**
 * Расширение для EditText, добавляет listener на изменение текста и меняет его согласно коллбеку
 *
 * @param onTextChanged - коллбек, вызываемый каждый раз при изменении текста
 */
fun EditText.setOnTextChanged(onTextChanged: (String) -> Unit) {
    tag = true
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (tag == true) {
                removeTextChangedListener(this)
                onTextChanged.invoke(s?.toString() ?: EMPTY_STRING)
                addTextChangedListener(this)
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}