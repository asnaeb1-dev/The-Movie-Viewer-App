package com.abhigyan.user.movieviewer1;
//DESIGNED BY ABHIGYAN RAHA

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
//            Toast.makeText(this, "Logging in user!", Toast.LENGTH_SHORT).show();

public class LoginActivity extends AppCompatActivity {

    TextView chooseText, loginRegisterText, tagText;
    Button loginRegisterButton;
    EditText usernameText, passwordText;
    ProgressBar loginRegisterPB;

    ParseUser parseUser;
    Boolean loginIsShowing = true;
    int value =0;

    public void loginRegisterFunction(View view) {
        if (value == 0) {
            //login user
            loginRegisterText.setVisibility(View.VISIBLE);
            loginRegisterText.setText("Logging in. Please wait...");
            loginRegisterPB.setVisibility(View.VISIBLE);

            ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {

                        loginRegisterPB.setVisibility(View.INVISIBLE);
                        loginRegisterText.setVisibility(View.INVISIBLE);

                        Intent homeActivity = new Intent(getApplicationContext(), LatestActivity.class);
                        startActivity(homeActivity);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }
                    else
                        {
                        loginRegisterPB.setVisibility(View.INVISIBLE);
                        loginRegisterText.setVisibility(View.INVISIBLE);
                        Log.i("abhigyans exception", e.getMessage());
                        Toast.makeText(getApplicationContext(), "Username/Password Incorrect! Try again!", Toast.LENGTH_SHORT).show();
                        usernameText.setText(null);
                        passwordText.setText(null);
                    }
                }
            });
        } else {
            //register user
            loginRegisterText.setVisibility(View.VISIBLE);
            loginRegisterText.setText("Registering. Please wait...");
            loginRegisterPB.setVisibility(View.VISIBLE);

            parseUser = new ParseUser();
            parseUser.setUsername(usernameText.getText().toString());
            parseUser.setPassword(passwordText.getText().toString());
            parseUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                            loginRegisterPB.setVisibility(View.INVISIBLE);
                            loginRegisterText.setVisibility(View.INVISIBLE);
                            Intent homeActivity = new Intent(getApplicationContext(), LatestActivity.class);
                            startActivity(homeActivity);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        } else {
                            loginRegisterText.setVisibility(View.INVISIBLE);
                            loginRegisterPB.setVisibility(View.INVISIBLE);
                            Log.i("abhigyans exception", e.getMessage());
                            Toast.makeText(getApplicationContext(), "Sorry! Couldn't Login! Try again!", Toast.LENGTH_SHORT).show();
                            usernameText.setText(null);
                            passwordText.setText(null);
                    }
                }
            });
        }
    }

    public void chooseFunction(View view)
    {
        if(loginIsShowing == true)
        {
            chooseText.setText("or Login?");
            loginRegisterButton.setText("Register");
            tagText.setText("Register");
            loginIsShowing = false;
            value=1;
        }
        else
        {
            chooseText.setText("or Register?");
            loginRegisterButton.setText("Login");
            tagText.setText("Login");
            loginIsShowing = true;
            value=0;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        chooseText  = findViewById(R.id.choiceText);
        loginRegisterButton = findViewById(R.id.loginRegisterButton);
        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        loginRegisterText = findViewById(R.id.loginRegisterText);
        loginRegisterPB = findViewById(R.id.loginRegisterPB);
        tagText = findViewById(R.id.tagText);
        loginRegisterText.setVisibility(View.INVISIBLE);
        loginRegisterPB.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, LatestActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();

    }
}
