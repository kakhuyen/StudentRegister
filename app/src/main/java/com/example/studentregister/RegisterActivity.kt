package com.example.studentregister

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.studentregister.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private var registeredName = ""
    private var registeredEmail = ""
    private var registeredPhone = ""
    private var registeredMsv = ""
    private var registeredClass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRealtimeValidation()

        registeredName = intent.getStringExtra("KEY_NAME") ?: ""
        registeredEmail = intent.getStringExtra("KEY_EMAIL") ?: ""
        registeredPhone = intent.getStringExtra("KEY_PHONE") ?: ""
        registeredMsv = intent.getStringExtra("KEY_MSV") ?: ""
        registeredClass = intent.getStringExtra("KEY_CLASS") ?: ""
        binding.etName.setText(registeredName)
        binding.etEmail.setText(registeredEmail)
        binding.etPhone.setText(registeredPhone)
        binding.etMsv.setText(registeredMsv)
        binding.etClass.setText(registeredClass)

        binding.btnRegister.setOnClickListener { handleRegister() }

        binding.btnGoDetail.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java).apply {
                if (registeredName.isNotBlank()) putExtra("KEY_NAME", registeredName)
                if (registeredEmail.isNotBlank()) putExtra("KEY_EMAIL", registeredEmail)
                if (registeredPhone.isNotBlank()) putExtra("KEY_PHONE", registeredPhone)
                if (registeredMsv.isNotBlank()) putExtra("KEY_MSV", registeredMsv)
                if (registeredClass.isNotBlank()) putExtra("KEY_CLASS", registeredClass)
            }
            startActivity(intent)
        }
    }

    private fun setupRealtimeValidation() {
        binding.etName.doAfterTextChanged { binding.tilName.error = null }
        binding.etEmail.doAfterTextChanged { binding.tilEmail.error = null }
        binding.etPhone.doAfterTextChanged { binding.tilPhone.error = null }
        binding.etMsv.doAfterTextChanged { binding.tilMsv.error = null }
        binding.etClass.doAfterTextChanged { binding.tilClass.error = null }
    }

    private fun handleRegister() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val msv = binding.etMsv.text.toString().trim()
        val className = binding.etClass.text.toString().trim()

        var isValid = true

        if (name.isBlank()) {
            binding.tilName.error = getString(R.string.err_name_empty)
            if (isValid) binding.etName.requestFocus()
            isValid = false
        } else if (name.split(Regex("\\s+")).size < 2) {
            binding.tilName.error = getString(R.string.err_name_format)
            if (isValid) binding.etName.requestFocus()
            isValid = false
        } else {
            binding.tilName.error = null
        }

        if (email.isBlank()) {
            binding.tilEmail.error = getString(R.string.err_email_empty)
            if (isValid) binding.etEmail.requestFocus()
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = getString(R.string.err_email_format)
            if (isValid) binding.etEmail.requestFocus()
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        val phoneRegex = "^(03|05|07|08|09)\\d{8}$".toRegex()
        if (phone.isBlank()) {
            binding.tilPhone.error = getString(R.string.err_phone_empty)
            if (isValid) binding.etPhone.requestFocus()
            isValid = false
        } else if (!phone.matches(phoneRegex)) {
            binding.tilPhone.error = getString(R.string.err_phone_format)
            if (isValid) binding.etPhone.requestFocus()
            isValid = false
        } else {
            binding.tilPhone.error = null
        }

        val msvRegex = "^\\d{13}$".toRegex()
        if (msv.isBlank()) {
            binding.tilMsv.error = getString(R.string.err_msv_empty)
            if (isValid) binding.etMsv.requestFocus()
            isValid = false
        } else if (!msv.matches(msvRegex)) {
            binding.tilMsv.error = getString(R.string.err_msv_format)
            if (isValid) binding.etMsv.requestFocus()
            isValid = false
        } else {
            binding.tilMsv.error = null
        }

        val classRegex = "^[A-Za-z0-9]{4,}$".toRegex()
        if (className.isBlank()) {
            binding.tilClass.error = getString(R.string.err_class_empty)
            if (isValid) binding.etClass.requestFocus()
            isValid = false
        } else if (!className.matches(classRegex)) {
            binding.tilClass.error = getString(R.string.err_class_format)
            if (isValid) binding.etClass.requestFocus()
            isValid = false
        } else {
            binding.tilClass.error = null
        }

        if (isValid) {
            registeredName = name
            registeredEmail = email
            registeredPhone = phone
            registeredMsv = msv
            registeredClass = className
            val snackbar = Snackbar.make(
                binding.root,
                getString(R.string.msg_success),
                Snackbar.LENGTH_LONG
            ).setAction(getString(R.string.action_view_detail)) {
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra("KEY_NAME", name)
                    putExtra("KEY_EMAIL", email)
                    putExtra("KEY_PHONE", phone)
                    putExtra("KEY_MSV", msv)
                    putExtra("KEY_CLASS", className)
                }
                startActivity(intent)
            }
            snackbar.setActionTextColor(getColor(R.color.white))
            snackbar.show()
        }
    }
}
