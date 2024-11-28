package com.example.secdevlab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
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
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(view -> {
            String fname, lname, email, pass, repass;
            email = etEmail.getText().toString();
            pass = etPass.getText().toString();
            repass = etRepass.getText().toString();
            fname = etfName.getText().toString();
            lname = etlName.getText().toString();

            if (email.isEmpty() || pass.isEmpty() || repass.isEmpty() || fname.isEmpty() || lname.isEmpty()) {
                Toast.makeText(Register.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            } else {
                if(pass.equals(repass)) {
                    //Checks the database to see if the user already exists
                    if (dbHelper.checkUserEmail(email)){
                        Toast.makeText(Register.this, "User already exists", Toast.LENGTH_LONG).show();
                        return;
                    }
                    //Continues with registration if both password and retype password fields are matching.
                    boolean registeredSuccess = dbHelper.insertData(fname, lname, email,pass);
                    if (registeredSuccess) {
                        Toast.makeText(Register.this, "User registered Successfully", Toast.LENGTH_LONG).show();
                        //Ends the activity and takes the user back to the login page.
                        finish();
                    }
                    else {
                        Toast.makeText(Register.this, "User registration Failed. Please try again.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}