package com.example.secdevlab4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText etEmail, etPass, etRepass, etfName, etlName;
    Button btnRegister, btnLogin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.emailRegEditText);
        etPass = findViewById(R.id.passRegEditText);
        etRepass = findViewById(R.id.rePassRegEditText);
        etfName = findViewById(R.id.fNameEditText);
        etlName = findViewById(R.id.lNameEditText);

        btnRegister = findViewById(R.id.createButton);
        btnLogin = findViewById(R.id.backToLoginButton);

        dbHelper = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            // Takes the user back to the login page
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Registers the user
        btnRegister.setOnClickListener(view -> {
            String fname, lname, email, pass, repass;
            email = etEmail.getText().toString();
            pass = etPass.getText().toString();
            repass = etRepass.getText().toString();
            fname = etfName.getText().toString();
            lname = etlName.getText().toString();

            // Validation
            // Checks if all fields are filled
            if (email.isEmpty() || pass.isEmpty() || repass.isEmpty() || fname.isEmpty() || lname.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            } else {
                // Checks if the email is valid
                if (!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                }
                // Checks if the password is valid
                else if (!isValidPassword(pass)) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters long, at least one uppercase letter, one number, and one special character.", Toast.LENGTH_LONG).show();
                }
                // Checks if the passwords match
                else if (!pass.equals(repass)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
                }
                else {
                    // Checks the database to see if the user already exists
                    if (dbHelper.checkUserEmail(email)) {
                        Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Hashes the password with a new salt
                    String salt = HashingHelper.generateSalt();
                    String hashedPassword = HashingHelper.hashPassword(pass, salt);

                    // Continues with registration if validation passes
                    boolean registeredSuccess = dbHelper.insertData(fname, lname, email, hashedPassword, salt);
                    if (registeredSuccess) {
                        Toast.makeText(RegisterActivity.this, "User registered Successfully", Toast.LENGTH_LONG).show();
                        // Ends the activity and takes the user back to the login page.
                        finish();
                    } else {
                        // Shows an error message if registration fails
                        Toast.makeText(RegisterActivity.this, "User registration Failed. Please try again.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    // Method to validate email
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Method to validate password
    private boolean isValidPassword(String password) {
        // Password must have at least 8 characters, at least one uppercase letter, one lowercase letter, one number, and one special character
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@#$%^&+=!.].*");
    }
}