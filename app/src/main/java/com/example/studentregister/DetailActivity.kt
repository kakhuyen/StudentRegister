package com.example.studentregister

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studentregister.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("KEY_NAME") ?: getString(R.string.no_info)
        val email = intent.getStringExtra("KEY_EMAIL") ?: getString(R.string.no_info)
        val phone = intent.getStringExtra("KEY_PHONE") ?: getString(R.string.no_info)
        val msv = intent.getStringExtra("KEY_MSV") ?: getString(R.string.no_info)
        val className = intent.getStringExtra("KEY_CLASS") ?: getString(R.string.no_info)

        binding.tvName.text = getString(R.string.detail_name, name)
        binding.tvEmail.text = getString(R.string.detail_email, email)
        binding.tvPhone.text = getString(R.string.detail_phone, phone)
        binding.tvMsv.text = getString(R.string.detail_msv, msv)
        binding.tvClass.text = getString(R.string.detail_class, className)

        val receivedName = intent.getStringExtra("KEY_NAME")
        val receivedEmail = intent.getStringExtra("KEY_EMAIL")
        val receivedPhone = intent.getStringExtra("KEY_PHONE")
        val receivedMsv = intent.getStringExtra("KEY_MSV")
        val receivedClass = intent.getStringExtra("KEY_CLASS")

        binding.btnGoRegister.setOnClickListener {
            val backIntent = Intent(this, RegisterActivity::class.java).apply {
                if (receivedName != null) putExtra("KEY_NAME", receivedName)
                if (receivedEmail != null) putExtra("KEY_EMAIL", receivedEmail)
                if (receivedPhone != null) putExtra("KEY_PHONE", receivedPhone)
                if (receivedMsv != null) putExtra("KEY_MSV", receivedMsv)
                if (receivedClass != null) putExtra("KEY_CLASS", receivedClass)
            }
            startActivity(backIntent)
            finish()
        }
    }
}
