package com.example.SonuzOvenUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.SonuzOvenUsers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText et_Password;
    private Button btn_Reset,btn_Cancel;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

    et_Password = (EditText) findViewById(R.id.et_resetPassword);
    btn_Reset = (Button) findViewById(R.id.btn_resetPassword);
    btn_Cancel = (Button) findViewById(R.id.btn_fpCancel);
    firebaseAuth = FirebaseAuth.getInstance();


    /*
    Back to login activity
    */
    btn_Cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(ForgotPassword.this,MainActivity.class));
        }
    });
    /*
    resetting password
    */
    btn_Reset.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //user email
            String email = et_Password.getText().toString().trim();
            if(email.equals("")){
                //Set toast
                Toast.makeText(ForgotPassword.this, "Please enter your email",Toast.LENGTH_SHORT).show();

            }else {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(ForgotPassword.this, "Reset email send",Toast.LENGTH_SHORT).show();
                           finish();
                           startActivity(new Intent(ForgotPassword.this,MainActivity.class));
                       }else{
                           Toast.makeText(ForgotPassword.this, "Reset email not send",Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        }
    });


    }
}