package com.example.SonuzOvenUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SonuzOvenUsers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText Password;
    private TextView no_of_attempts;
    private Button login,registartion;
    private int counter = 5;
    private TextView  tv_forgotPassord;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //username
        userName = (EditText) findViewById(R.id.user_username);
        //Password
        Password = (EditText) findViewById(R.id.user_password);
        //No of attempts
        no_of_attempts = (TextView) findViewById(R.id.tv_no_of_attempts);
        //Login button
        login = (Button)findViewById(R.id.lgn_button);
        //registration text view
        registartion = (Button) findViewById(R.id.tv_Register);
        //Forgot pasword text view
        tv_forgotPassord = (TextView) findViewById(R.id.tv_forgotPassword);

        // Fetching the current firebase instance
        firebaseAuth  = FirebaseAuth.getInstance();
        //Fetching current user
        FirebaseUser User = firebaseAuth.getCurrentUser();
        //Setting progress bar
        progressBar = (ProgressBar) findViewById(R.id.pb_progressBar);
        //
        progressBar.setVisibility(View.INVISIBLE);


        //checking if the user is already logged
        if( User != null){
            //Stop activity
            finish();
            // move to new activity
            startActivity(new Intent(MainActivity.this,ProcessDrawer.class));
        }else{

        }
        // Set text view
        //no_of_attempts.setText("Number of attempts : 5");

        //Onclick listner for Login buttom
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(userName.getText().toString(),Password.getText().toString());
            }
        });

        // on click listener for Registration button
        registartion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });

        //on click listener for forgot password
        tv_forgotPassord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ForgotPassword.class));
            }
        });

     }
    /*
    Function to validate username and password
    params @String username
    params @string password
     */
    private void validate (String username, String password){
        // progressBar
        progressBar.setVisibility(View.VISIBLE);
        // Check firebase authentication
        firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //If the login is successful
                if(task.isSuccessful()){
                    //Set progress bar visibility
                    progressBar.setVisibility(View.INVISIBLE);
                    //Check email verification
                    checkEmailVerification();
                    //Movie to Process screen
                    //startActivity(new Intent(MainActivity.this,ProcessDrawer.class));
                    //Display Toast
                    //Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                } else{
                    //if login unsucessful
                    //Display toast
                    Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    //Decrement counter
                    /*counter--;
                    //Setting Counter value of attempts
                    no_of_attempts.setText("Number of attempts : "+String.valueOf(counter));
                    //disabling Login Button
                    if(counter == 0){
                        login.setEnabled(false);
                    }*/
                    //Set progress bar
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }
        });

    }


    /*
    Check Email verification
     */
    private void checkEmailVerification(){
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        Boolean EmailFalg = user.isEmailVerified();

        if(EmailFalg){
            finish();
            startActivity(new Intent(MainActivity.this,ProcessDrawer.class));
        }else{
            Toast.makeText(this,"Please verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}