package com.example.discipleshipsignup

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var whoWouldJoinSpinner: Spinner
    private lateinit var submitButton: Button

    // Day checkboxes
    private lateinit var mondayCheckBox: CheckBox
    private lateinit var tuesdayCheckBox: CheckBox
    private lateinit var wednesdayCheckBox: CheckBox
    private lateinit var thursdayCheckBox: CheckBox
    private lateinit var fridayCheckBox: CheckBox
    private lateinit var saturdayCheckBox: CheckBox
    private lateinit var sundayCheckBox: CheckBox

    // Time checkboxes
    private lateinit var morningCheckBox: CheckBox
    private lateinit var afternoonCheckBox: CheckBox
    private lateinit var eveningCheckBox: CheckBox

    // Group type checkboxes
    private lateinit var mensCheckBox: CheckBox
    private lateinit var womensCheckBox: CheckBox
    private lateinit var mixedCheckBox: CheckBox
    private lateinit var youngAdultsCheckBox: CheckBox
    private lateinit var youngProfessionalsCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        whoWouldJoinSpinner = findViewById(R.id.whoWouldJoinSpinner)
        submitButton = findViewById(R.id.submitButton)

        // Initialize day checkboxes
        mondayCheckBox = findViewById(R.id.mondayCheckBox)
        tuesdayCheckBox = findViewById(R.id.tuesdayCheckBox)
        wednesdayCheckBox = findViewById(R.id.wednesdayCheckBox)
        thursdayCheckBox = findViewById(R.id.thursdayCheckBox)
        fridayCheckBox = findViewById(R.id.fridayCheckBox)
        saturdayCheckBox = findViewById(R.id.saturdayCheckBox)
        sundayCheckBox = findViewById(R.id.sundayCheckBox)

        // Initialize time checkboxes
        morningCheckBox = findViewById(R.id.morningCheckBox)
        afternoonCheckBox = findViewById(R.id.afternoonCheckBox)
        eveningCheckBox = findViewById(R.id.eveningCheckBox)

        // Initialize group type checkboxes
        mensCheckBox = findViewById(R.id.mensCheckBox)
        womensCheckBox = findViewById(R.id.womensCheckBox)
        mixedCheckBox = findViewById(R.id.mixedCheckBox)
        youngAdultsCheckBox = findViewById(R.id.youngAdultsCheckBox)
        youngProfessionalsCheckBox = findViewById(R.id.youngProfessionalsCheckBox)

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
        // Who would join options
        val whoWouldJoinOptions = arrayOf("Select option", "Just me", "Myself and my spouse", "Myself, my spouse and our kids")
        val whoWouldJoinAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, whoWouldJoinOptions)
        whoWouldJoinSpinner.adapter = whoWouldJoinAdapter
    }

    private fun getSelectedDays(): String {
        val selectedDays = mutableListOf<String>()

        if (mondayCheckBox.isChecked) selectedDays.add("Monday")
        if (tuesdayCheckBox.isChecked) selectedDays.add("Tuesday")
        if (wednesdayCheckBox.isChecked) selectedDays.add("Wednesday")
        if (thursdayCheckBox.isChecked) selectedDays.add("Thursday")
        if (fridayCheckBox.isChecked) selectedDays.add("Friday")
        if (saturdayCheckBox.isChecked) selectedDays.add("Saturday")
        if (sundayCheckBox.isChecked) selectedDays.add("Sunday")

        return if (selectedDays.isEmpty()) {
            ""
        } else {
            selectedDays.joinToString(", ")
        }
    }

    private fun getSelectedTimes(): String {
        val selectedTimes = mutableListOf<String>()

        if (morningCheckBox.isChecked) selectedTimes.add("Morning")
        if (afternoonCheckBox.isChecked) selectedTimes.add("Afternoon")
        if (eveningCheckBox.isChecked) selectedTimes.add("Evening")

        return if (selectedTimes.isEmpty()) {
            ""
        } else {
            selectedTimes.joinToString(", ")
        }
    }

    private fun getSelectedGroupTypes(): String {
        val selectedGroupTypes = mutableListOf<String>()

        if (mensCheckBox.isChecked) selectedGroupTypes.add("Men's")
        if (womensCheckBox.isChecked) selectedGroupTypes.add("Women's")
        if (mixedCheckBox.isChecked) selectedGroupTypes.add("Mixed")
        if (youngAdultsCheckBox.isChecked) selectedGroupTypes.add("Young Adults (18-25)")
        if (youngProfessionalsCheckBox.isChecked) selectedGroupTypes.add("Young Professionals (25-35)")

        return if (selectedGroupTypes.isEmpty()) {
            ""
        } else {
            selectedGroupTypes.joinToString(", ")
        }
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

        // Validate who would join spinner
        if (whoWouldJoinSpinner.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select who would join the group", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Validate at least one day is selected
        if (getSelectedDays().isEmpty()) {
            Toast.makeText(this, "Please select at least one preferred day", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Validate at least one time is selected
        if (getSelectedTimes().isEmpty()) {
            Toast.makeText(this, "Please select at least one preferred time", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Validate at least one group type is selected
        if (getSelectedGroupTypes().isEmpty()) {
            Toast.makeText(this, "Please select at least one group type", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun sendEmail() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val whoWouldJoin = whoWouldJoinSpinner.selectedItem.toString()
        val days = getSelectedDays()
        val times = getSelectedTimes()
        val groupTypes = getSelectedGroupTypes()

        val subject = "New Discipleship Group Sign-up: $name"
        val body = """
            New Discipleship Group Sign-up
            
            Name: $name
            Email: $email
            Who would join: $whoWouldJoin
            Preferred Days: $days
            Preferred Times: $times
            Preferred Group Types: $groupTypes
            
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
        whoWouldJoinSpinner.setSelection(0)

        // Clear all day checkboxes
        mondayCheckBox.isChecked = false
        tuesdayCheckBox.isChecked = false
        wednesdayCheckBox.isChecked = false
        thursdayCheckBox.isChecked = false
        fridayCheckBox.isChecked = false
        saturdayCheckBox.isChecked = false
        sundayCheckBox.isChecked = false

        // Clear all time checkboxes
        morningCheckBox.isChecked = false
        afternoonCheckBox.isChecked = false
        eveningCheckBox.isChecked = false

        // Clear all group type checkboxes
        mensCheckBox.isChecked = false
        womensCheckBox.isChecked = false
        mixedCheckBox.isChecked = false
        youngAdultsCheckBox.isChecked = false
        youngProfessionalsCheckBox.isChecked = false
    }
}