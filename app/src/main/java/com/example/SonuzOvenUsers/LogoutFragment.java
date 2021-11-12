package com.example.SonuzOvenUsers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.SonuzOvenUsers.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogoutFragment extends Fragment{

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListner;
    private ProgressBar progressBar;

    private TextView tv_AreYouSure;
    private Button btn_cancel,btn_signout;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logout_fragment,container,false) ;

        progressBar = (ProgressBar) view.findViewById(R.id.pb_progressbarLogout);
        tv_AreYouSure = (TextView) view.findViewById(R.id.tv_signout);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_signout = (Button) view.findViewById(R.id.btn_signout);

        //Create Action Bar
        //ActionBar actionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();
        //Set Action bar
        //actionBar.setDisplayHomeAsUpEnabled(false);

       progressBar.setVisibility(view.INVISIBLE);
        setupFirebaseAuth();
        /*
        Signout on button  click
        */
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(v.VISIBLE);
                firebaseAuth.signOut();
                getActivity().finish();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                progressBar.setVisibility(v.INVISIBLE);
                startActivity(intent);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container_fragment,new ViewCakes()).commit();
            }
        });

        return view;
    }

    private void setupFirebaseAuth(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user!=null){
                    Log.d("TAG","User Logged in : " + user.getEmail());
                    }else{
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    }

            }
        };

    }
}
