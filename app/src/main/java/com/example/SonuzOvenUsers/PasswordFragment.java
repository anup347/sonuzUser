package com.example.SonuzOvenUsers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.SonuzOvenUsers.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PasswordFragment extends Fragment{

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private Button btn_ChangePassword;
    private EditText et_ChangePassword,et_RepeatPassword;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_fragment,container,false);

        btn_ChangePassword = (Button) view.findViewById(R.id.btn_changePassword);
        et_ChangePassword = (EditText) view.findViewById(R.id.et_ChangePassword);
        et_RepeatPassword = (EditText) view.findViewById(R.id.et_RepeatPassword);
        //Create Action Bar
        //ActionBar actionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();
        //Set Action bar
        //actionBar.setDisplayHomeAsUpEnabled(false);

        btn_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_Password = et_ChangePassword.getText().toString();
                String repeat_Password = et_RepeatPassword.getText().toString();
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                if(new_Password.equals(repeat_Password) && !new_Password.isEmpty() && !repeat_Password.isEmpty() ) {

                    firebaseUser.updatePassword(new_Password).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                            } else {

                                String errorMessage = task.getException().getMessage().toString();
                                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(getContext(), "Password mismatch or empty", Toast.LENGTH_SHORT).show();
                }



            }
        });

        return view;
    }

}
