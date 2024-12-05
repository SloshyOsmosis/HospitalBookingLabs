package com.example.secdevlab4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText editTextEmailAddress, editTextNewPassword, editTextRetypeNewPassword;
    Button updatePassButton;

    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextRetypeNewPassword = findViewById(R.id.editTextRetypeNewPassword);

        updatePassButton = findViewById(R.id.updatePassButton);

        myDB = new DBHelper(this);

        updatePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // Reset the password when the button is clicked
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    // Helper method to reset the password
    private void resetPassword() {
        String email = editTextEmailAddress.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();
        String retypePassword = editTextRetypeNewPassword.getText().toString().trim();

        //Validation
        if (email.isEmpty() || newPassword.isEmpty() || retypePassword.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        } else{
            // Checks if the passwords match
            if (!newPassword.equals(retypePassword)) {
                Toast.makeText(ForgotPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            // Checks if the password is valid
            else if (!isValidPassword(newPassword, retypePassword)) {
                Toast.makeText(ForgotPasswordActivity.this, "Password must be at least 8 characters long, at least one uppercase letter, one number, and one special character.", Toast.LENGTH_LONG).show();
            }
            // Checks if the user exists
             else if (!myDB.checkUserEmail(email)) {
                Toast.makeText(ForgotPasswordActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hash the new password with a new salt
            String salt = HashingHelper.generateSalt();
            String hashedNewPassword = HashingHelper.hashPassword(newPassword, salt);

            // Update the password
            boolean isUpdated = myDB.updatePassword(email, hashedNewPassword, salt);
            if (isUpdated) {
                Toast.makeText(ForgotPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                // Ends the activity and takes the user back to the login page
                finish();
            } else {
                // Shows an error message if the password update fails
                Toast.makeText(ForgotPasswordActivity.this, "Password update failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
        // Checks if the passwords match


    }

    // Method to validate password
    private boolean isValidPassword(String password, String retypePassword) {
        // Password must have at least 8 characters, at least one uppercase letter, one lowercase letter, one number, and one special character
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@#$%^&+=!.].*");
    }
}