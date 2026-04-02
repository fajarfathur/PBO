package com.example.pbo

import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    lateinit var etNama: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirm: EditText

    lateinit var layoutNama: TextInputLayout
    lateinit var layoutEmail: TextInputLayout
    lateinit var layoutPassword: TextInputLayout
    lateinit var layoutConfirm: TextInputLayout

    lateinit var radioGroup: RadioGroup
    lateinit var cb1: CheckBox
    lateinit var cb2: CheckBox
    lateinit var cb3: CheckBox

    lateinit var spinner: Spinner
    lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        setupSpinner()
        setupRealtimeValidation()
        setupButton()
    }

    private fun initView() {
        etNama = findViewById(R.id.etNama)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirm = findViewById(R.id.etConfirm)

        layoutNama = findViewById(R.id.layoutNama)
        layoutEmail = findViewById(R.id.layoutEmail)
        layoutPassword = findViewById(R.id.layoutPassword)
        layoutConfirm = findViewById(R.id.layoutConfirm)

        radioGroup = findViewById(R.id.radioGroup)
        cb1 = findViewById(R.id.cb1)
        cb2 = findViewById(R.id.cb2)
        cb3 = findViewById(R.id.cb3)

        spinner = findViewById(R.id.spinner)
        btnSubmit = findViewById(R.id.btnSubmit)
    }

    private fun setupSpinner() {
        val data = arrayOf("Jakarta", "Bandung", "Surabaya")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        spinner.adapter = adapter
    }

    private fun setupRealtimeValidation() {
        etEmail.setOnFocusChangeListener { _, _ -> validateEmail() }
        etConfirm.setOnFocusChangeListener { _, _ -> validatePasswordMatch() }
    }

    private fun setupButton() {

        btnSubmit.setOnClickListener {
            if (validateAll()) {
                showDialog()
            }
        }

        // LONG PRESS
        btnSubmit.setOnLongClickListener {
            Toast.makeText(this, "Long Press Detected!", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun validateAll(): Boolean {
        var valid = true

        if (etNama.text.isEmpty()) {
            layoutNama.error = "Nama wajib diisi"
            valid = false
        } else layoutNama.error = null

        if (!validateEmail()) valid = false
        if (!validatePasswordMatch()) valid = false

        if (radioGroup.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Pilih gender", Toast.LENGTH_SHORT).show()
            valid = false
        }

        val checkCount = listOf(cb1, cb2, cb3).count { it.isChecked }
        if (checkCount < 1) {
            Toast.makeText(this, "Pilih minimal 1 hobi", Toast.LENGTH_SHORT).show()
            valid = false
        }

        return valid
    }

    private fun validateEmail(): Boolean {
        val email = etEmail.text.toString()
        return if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            layoutEmail.error = "Email tidak valid"
            false
        } else {
            layoutEmail.error = null
            true
        }
    }

    private fun validatePasswordMatch(): Boolean {
        return if (etPassword.text.toString() != etConfirm.text.toString()) {
            layoutConfirm.error = "Password tidak sama"
            false
        } else {
            layoutConfirm.error = null
            true
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi")
            .setMessage("Data sudah benar?")
            .setPositiveButton("Ya") { _, _ ->
                Toast.makeText(this, "Berhasil Submit", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}