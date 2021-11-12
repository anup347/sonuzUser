package com.example.SonuzOvenUsers;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.SonuzOvenUsers.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class AddCake extends Fragment{


    private Button btn_AddCake;
    private EditText et_CakeName;
    private EditText et_CakeDescription;
    private EditText et_CakePrice;
    private ImageView et_CakeImage;
    private static int PICK_CAKE= 347;
    Uri imagePath;

    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private  StorageReference cakeImageRefrence;
    private DatabaseReference myCakeRef;
    String type;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_cake_fragment,container,false);
        //Setting View elements
        setUICakeViews(view);
        firebaseAuth = FirebaseAuth.getInstance();
        //Setting firebase reference
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference =  firebaseStorage.getReference();

        //Action on image Click
        /*et_CakeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"), PICK_CAKE);
            }
        });*/

        //Action on "Add" button click
       /* btn_AddCake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Implementing validation logic
                if(validate()){
                    //Send data to database
                    sendCakeData(v);
                }


            }
        });*/

        progressBar.setVisibility(view.INVISIBLE);

        return view;
    }

    /*
    Setting UI Elements
    *
    */
    private  void  setUICakeViews(View view){
        et_CakeImage = (ImageView) view.findViewById(R.id.iv_OrderCakeImage);
        btn_AddCake = (Button) view.findViewById(R.id.btn_PlaceOrder);
        et_CakeName = (EditText) view.findViewById(R.id.tv_OrderCakeName);
        et_CakeDescription = (EditText) view.findViewById(R.id.tv_OrderCakeDescription);
        et_CakePrice = (EditText) view.findViewById(R.id.tv_OrderCakePrice);
        progressBar = (ProgressBar) view.findViewById(R.id.pb_OrderCakeProgressBar);
    }

    /*
     Validate data
     */
    private  boolean validate(){
        Boolean result = false;

        String Cakename = et_CakeName.getText().toString();
        String CakePrice = et_CakePrice.getText().toString();
        String CakeDesc = et_CakeDescription.getText().toString();

        if(Cakename.isEmpty() || CakeDesc.isEmpty() || CakePrice.isEmpty() || imagePath == null){
            Toast.makeText(getContext(),"Please enter all details",Toast.LENGTH_SHORT).show();
        }
        else {result = true;
        }

        return result;
    }
    /*
    On image upload activity
    *
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_CAKE && resultCode == RESULT_OK && data.getData() != null  )
        {
            imagePath =  data.getData();
            ContentResolver cR = getContext().getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getExtensionFromMimeType(cR.getType(imagePath));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imagePath);
                et_CakeImage.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    /*
    Sending Cake date to Firebase
    *
    */
    private void sendCakeData(final View v){
        progressBar.setVisibility(v.VISIBLE);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        myCakeRef = firebaseDatabase.getReference("Cakes");

        final String CakeName = et_CakeName.getText().toString().trim();
        final String CakeDescription = et_CakeDescription.getText().toString().trim();
        final String CakePrice = et_CakePrice.getText().toString().trim();


        cakeImageRefrence = storageReference.child("Cakes").child(CakeName).child(CakeName);
        //check if the Cake name already exists
        cakeImageRefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Cake already exists
                Toast.makeText(getContext(),"Cake exists, Please type in a new name" , Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(v.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               //Cake does not exist
               //Upload Cake Picture
                UploadTask uploadTask = cakeImageRefrence.putFile(imagePath);
                //If upload failed
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Cake Image upload failed",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(v.INVISIBLE);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    //if upload successful
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> downloadUrl =cakeImageRefrence.getDownloadUrl();
                        downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onSuccess(Uri uri) {
                                Date currentDateTime = Calendar.getInstance().getTime();

                                long dateFormat = android.icu.util.Calendar.getInstance().getTimeInMillis();

                                String imageRefrence = uri.toString();
                                AddCakeClass addCakeClass = new AddCakeClass(CakeName,CakeDescription,CakePrice,imageRefrence,String.valueOf(dateFormat));
                                myCakeRef.child(CakeName).setValue(addCakeClass);
                                Toast.makeText(getContext(),"Cake uploaded",Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(v.INVISIBLE);
                                getFragmentManager().beginTransaction().replace(R.id.container_fragment,new ViewCakes()).commit();

                            }
                        });

                    }
                });

            }
        });

    }
}
