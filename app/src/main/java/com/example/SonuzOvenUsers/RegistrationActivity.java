package com.example.SonuzOvenUsers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SonuzOvenUsers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {

    // initializing variables
    private EditText userFullName;
    private EditText userPassword;
    private EditText userEmail;
    private EditText et_phoneNumber;
    private Button btnRegister;
    private TextView userLogin;
    private Spinner mySpinner;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private ImageView user_profilePic;
    String name, email, password,phone_number,spinner_text;
    private static int PICK_IMAGE = 347;
    Uri imagePath;
    private StorageReference storageReference;


    /*
    Function to set the Image Bitmap to the variable
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                user_profilePic.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //calling the views funtion
        setUIViews();
        //Setting the firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();
        //Setting firebase reference
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference =  firebaseStorage.getReference();

        user_profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"), PICK_IMAGE);
            }
        });



        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.userType, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mySpinner.setAdapter(adapter);

        //onlcik listner for button to register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String password = userPassword.getText().toString().trim();

                        // upload to database
                        String email_address = userEmail.getText().toString().trim();

                        //Creating a user in firebase to authenticate app
                        firebaseAuth.createUserWithEmailAndPassword(email_address, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // if created
                                if (task.isSuccessful()) {

                                    sendEmailVerification();
                                    //Display success message
                                    //Toast.makeText(RegistrationActivity.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                                    //Redirect to main activity
                                    //startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                                } else {
                                    //Display unsucessful message
                                    Toast.makeText(RegistrationActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                }
            }
        });

        //Onclick listener to go back to the login page
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));

            }
        });
    }
    /*
    Function set the ui variavbles
     */
    private void setUIViews(){
        userFullName = (EditText) findViewById(R.id.et_full_name);
        userPassword = (EditText) findViewById(R.id.et_password);
        userEmail = (EditText) findViewById(R.id.et_email_address);
        btnRegister =(Button) findViewById(R.id.btn_Register);
        userLogin = (TextView) findViewById(R.id.tv_userLogin);
        mySpinner =(Spinner) findViewById(R.id.sp_userType);
        et_phoneNumber = (EditText)findViewById(R.id.et_phoneNumber);
        user_profilePic = (ImageView) findViewById(R.id.iv_Profile);
    }
    /*
    Function to Validate the fields
    return boolean result
     */
    private boolean validate(){
        // initialize result
        Boolean result = false;

        name = userFullName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        phone_number =  et_phoneNumber.getText().toString().trim();
        spinner_text = mySpinner.getSelectedItem().toString().trim();
        //Setting value for return
        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || phone_number.isEmpty() || imagePath == null){
            Toast.makeText(this,"Please enter all details",Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        //Return result
        return result;
    }


    /*
    Send Email verification
     */
    private void sendEmailVerification(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            // Upload the user data
                            sendUserData();
                            Toast.makeText(RegistrationActivity.this,"Verification email sent",Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            finish();
                            startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                        }else{
                            Toast.makeText(RegistrationActivity.this,"Verification email not sent",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }


    /*
    sending user data
     */
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users");

        StorageReference imageReference =  storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePicture");
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this,"Image upload failed",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(RegistrationActivity.this,"Profile picture uploaded",Toast.LENGTH_SHORT).show();
            }
        });

        UserProfile userProfile = new UserProfile(name,phone_number,spinner_text,email);
        myRef.child(firebaseAuth.getUid()).setValue(userProfile);
    }
}