package com.example.discipleshipsignup

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var daySpinner: Spinner
    private lateinit var timeSpinner: Spinner
    private lateinit var groupTypeSpinner: Spinner
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        genderSpinner = findViewById(R.id.genderSpinner)
        daySpinner = findViewById(R.id.daySpinner)
        timeSpinner = findViewById(R.id.timeSpinner)
        groupTypeSpinner = findViewById(R.id.groupTypeSpinner)
        submitButton = findViewById(R.id.submitButton)

        // Setup spinners
        setupSpinners()

        // Setup submit button
        submitButton.setOnClickListener {
            if (validateForm()) {
                sendEmail()
            }
        }
    }

    private fun setupSpinners() {
        // Gender options
        val genderOptions = arrayOf("Select Gender", "Male", "Female")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderOptions)
        genderSpinner.adapter = genderAdapter

        // Day options
        val dayOptions = arrayOf("Select Day", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val dayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dayOptions)
        daySpinner.adapter = dayAdapter

        // Time options
        val timeOptions = arrayOf("Select Time", "Morning", "Afternoon", "Evening")
        val timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, timeOptions)
        timeSpinner.adapter = timeAdapter

        // Group type options
        val groupOptions = arrayOf("Select Group Type", "Men's", "Women's", "Mixed", "Young Adults (18-25)", "Young Professionals (25-35)")
        val groupAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, groupOptions)
        groupTypeSpinner.adapter = groupAdapter
    }

    private fun validateForm(): Boolean {
        var isValid = true

        // Validate name
        if (nameEditText.text.toString().trim().isEmpty()) {
            nameEditText.error = "Please enter your name"
            isValid = false
        }

        // Validate email
        val email = emailEditText.text.toString().trim()
        if (email.isEmpty()) {
            emailEditText.error = "Please enter your email"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email address"
            isValid = false
        }

        // Validate spinners
        if (genderSpinner.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (daySpinner.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select a preferred day", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (timeSpinner.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select a preferred time", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (groupTypeSpinner.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select a group type", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun sendEmail() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val gender = genderSpinner.selectedItem.toString()
        val day = daySpinner.selectedItem.toString()
        val time = timeSpinner.selectedItem.toString()
        val groupType = groupTypeSpinner.selectedItem.toString()

        val subject = "New Discipleship Group Sign-up: $name"
        val body = """
            New Discipleship Group Sign-up
            
            Name: $name
            Email: $email
            Gender: $gender
            Preferred Day: $day
            Preferred Time: $time
            Preferred Group Type: $groupType
            
            Submitted on: ${java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())}
        """.trimIndent()

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("kevin@harbourfellowship.com")) // Replace with your email
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        try {
            startActivity(Intent.createChooser(emailIntent, "Send sign-up via email"))

            // Clear form after sending
            clearForm()
            Toast.makeText(this, "Thank you! Your sign-up has been submitted.", Toast.LENGTH_LONG).show()

        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "No email app found. Please install an email app.", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearForm() {
        nameEditText.text.clear()
        emailEditText.text.clear()
        genderSpinner.setSelection(0)
        daySpinner.setSelection(0)
        timeSpinner.setSelection(0)
        groupTypeSpinner.setSelection(0)
    }
}