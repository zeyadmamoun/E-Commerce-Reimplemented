package com.example.e_commmercefixed.utils.auth

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class PasswordValidator(private var passwordEditText: TextInputLayout) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val password = s.toString()
        if (password.length < 6){
            passwordEditText.error = "Password must be more than 6 letters";
        }else{
            passwordEditText.error = null
        }
    }

    override fun afterTextChanged(s: Editable?) {}
}