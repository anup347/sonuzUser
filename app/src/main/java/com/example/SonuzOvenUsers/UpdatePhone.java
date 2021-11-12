package com.example.SonuzOvenUsers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SonuzOvenUsers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class UpdatePhone extends Fragment {


    private EditText et_Phone;
    private Button btn_Update;
    private TextView tv_GoBack;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;


/*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdatePhone() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdatePhone.
     */
    // TODO: Rename and change types and number of parameters
  /*  public static UpdatePhone newInstance(String param1, String param2) {
        UpdatePhone fragment = new UpdatePhone();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_update_phone, container, false);

        et_Phone = (EditText) v.findViewById(R.id.et_PhoneUpdate);
        Bundle bundle = this.getArguments();
        String Phone =  bundle.getString("PhoneKey");
        et_Phone.setText(Phone);
        //Create Action Bar
        //ActionBar actionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();
        //Set Action bar
        //actionBar.setDisplayHomeAsUpEnabled(true);

        btn_Update = (Button) v.findViewById(R.id.btn_UpdateUserData);



        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_Phone = et_Phone.getText().toString().trim();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference("Users").child(firebaseAuth.getUid()).child("phonenumber");
                UserProfile userProfile = new UserProfile();
                userProfile.setPhonenumber(new_Phone);
                myRef.setValue(new_Phone).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Phone Updated",Toast.LENGTH_SHORT).show();
                            getFragmentManager().beginTransaction().replace(R.id.container_fragment,new AccountFragment()).commit();
                        }else{
                            Toast.makeText(getContext(),"Unable to update, Please contact Admin",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        tv_GoBack = (TextView) v.findViewById(R.id.tv_GoBack);

        tv_GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container_fragment,new AccountFragment()).commit();
              /* if(getFragmentManager().getBackStackEntryCount() != 0)
                   getFragmentManager().popBackStack();
                */
            }
        });

        return v;




    }
}