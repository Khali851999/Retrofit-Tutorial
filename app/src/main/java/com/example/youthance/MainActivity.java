package com.example.youthance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.youthance.Model.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText nameEditText, mobileEditText, emailEditText, passwordEditText;
    Button signupButton;
    String name, mobile, email, password;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    TextView privacyTextView, loginTextView;

    //successCode = 1 if registration is successful
    //successCode = 0 if user already exists
    //any other integer for other purposes
    public static final int SUCCESS_REGISTER_CODE = 1;
    public static final int USER_EXISTS_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering..");

        nameEditText = findViewById(R.id.nameEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signupButton = findViewById(R.id.signupButton);
        privacyTextView = findViewById(R.id.privacyTextView);
        loginTextView = findViewById(R.id.loginTextView);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for test purposes
                startActivity(new Intent(MainActivity.this, HomeScreen.class));
                finish();
            }
        });

        String text = "By signing in I agree to the youthance  <a href='http://www.google.com'> Privacy Policy </a> " +
                "and <a href='http://www.google.com'> Terms and Conditions </a>";

        privacyTextView.setMovementMethod(LinkMovementMethod.getInstance());
        privacyTextView.setText(Html.fromHtml(text));

        sharedPreferences = getSharedPreferences("AuthenticationDetails", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn == true) {
            //TODO: Open homescreen acivity
            startActivity(new Intent(MainActivity.this, HomeScreen.class));
            finish();
        }

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isValidDetails()) {
                    progressDialog.show();
                    registerUser();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void registerUser() {

        name = nameEditText.getText().toString();
        mobile = mobileEditText.getText().toString();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();


        Call<LoginModel> call = RetrofitAPIClient.getInstance().getApi().createUser(name, mobile, email, password);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                progressDialog.dismiss();

                if(response.isSuccessful()){
                    if (response.body().getSuccessCode() == SUCCESS_REGISTER_CODE) {

                        Toast.makeText(MainActivity.this, "Registered Successfully...", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);

                        startActivity(new Intent(MainActivity.this, HomeScreen.class));
                        finish();

                    } else if (response.body().getSuccessCode() == USER_EXISTS_CODE) {

                        //TODO Start Login Activity
                        Toast.makeText(MainActivity.this, "User already exists...", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(MainActivity.this, "Error Occurred..", Toast.LENGTH_SHORT).show();
                }

                
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    boolean isValidDetails() {

        name = nameEditText.getText().toString().trim();
        mobile = mobileEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        boolean isValid = true;

        if (name.isEmpty()) {
            nameEditText.setError("Cannot be empty");
            isValid = false;
        }
        if (mobile.isEmpty()) {
            mobileEditText.setError("Cannot be empty");
            isValid = false;
        }
        if (!Patterns.PHONE.matcher(mobile).matches()) {
            emailEditText.setError("Invalid Phone Number");
            isValid = false;
        }
        if (email.isEmpty()) {
            emailEditText.setError("Cannot be empty");
            isValid = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid Email");
            isValid = false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Cannot be empty");
            isValid = false;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Must be more than 6 characters");
            isValid = false;
        }

        return isValid;

    }
}