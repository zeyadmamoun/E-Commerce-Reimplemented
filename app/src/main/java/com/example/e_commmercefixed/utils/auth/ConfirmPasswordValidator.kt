package com.example.e_commmercefixed.utils.auth

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class ConfirmPasswordValidator(
    private var passwordEditText: TextInputLayout,
    private var confirmPasswordEditText: TextInputLayout,
): TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val passwordConfirmation = s.toString()
        val userPassword = passwordEditText.editText?.text.toString()
        if (passwordConfirmation != userPassword){
            confirmPasswordEditText.error = "its not the same password as above";
        }else{
            confirmPasswordEditText.error = null
        }
    }

    override fun afterTextChanged(s: Editable?) {

    }
}