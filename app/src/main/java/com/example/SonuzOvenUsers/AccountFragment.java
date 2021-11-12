package com.example.SonuzOvenUsers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.SonuzOvenUsers.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment{

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 347;
    Uri imagePath;
    private StorageReference storageReference;
   // public static ContentResolver contextResolver;
    private ImageView profilePic;
    private TextView tv_name,tv_email,tv_phone;
    private Button btn_updateProfile;
    String PhoneNumber;


    // numlock+Alt+0
   /* public static ContentResolver getContextResolver()
    {
        return contextResolver;
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imagePath);
                profilePic.setImageBitmap(bitmap);

                StorageReference imageReference =  storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePicture");
                UploadTask uploadTask = imageReference.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Image upload failed",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(),"Profile picture uploaded",Toast.LENGTH_SHORT).show();
                    }
                });


            }catch (IOException e){
                e.printStackTrace();
            }
        }
        /*if(resultCode == RESULT_CANCELED){
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseStorage = FirebaseStorage.getInstance();
            storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePicture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(profilePic);
                }
            });

        }*/
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment,container,false);

        profilePic = (ImageView) view.findViewById(R.id.ivProfile_Pic);
        tv_name = (TextView) view.findViewById(R.id.tvProfileName);
        tv_email = (TextView) view.findViewById(R.id.tvProfileEmail);
        tv_phone = (TextView) view.findViewById(R.id.tvProfilePhoneNo);
        btn_updateProfile = (Button) view.findViewById(R.id.btnProfileUpdate);

        //Create Action Bar
        //ActionBar actionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();
        //Set Action bar
        //actionBar.setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images").child("ProfilePicture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilePic);
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Upload new image"), PICK_IMAGE);

            }
        });

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Users").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                PhoneNumber = userProfile.getPhonenumber();

                tv_name.setText(userProfile.getName());
                tv_email.setText(userProfile.getEmail());
                tv_phone.setText(PhoneNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                String message = error.getCode()+ " :" +error.getDetails();
                Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
            }
        });

        btn_updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("PhoneKey",PhoneNumber.toString());
                UpdatePhone  updatePhone = new UpdatePhone();
                updatePhone.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container_fragment,updatePhone).commit();

            }
        });

        return view;

    }
}
