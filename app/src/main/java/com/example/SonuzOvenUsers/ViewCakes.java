package com.example.SonuzOvenUsers;

import android.os.Build;
import android.os.Bundle;

import java.util.Collections;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.SonuzOvenUsers.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewCakes} factory method to
 * create an instance of this fragment.
 */
public class ViewCakes extends Fragment {


    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<AddCakeClass> list;
    MyAdapter myAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.from(getContext()).inflate(R.layout.fragment_view_cakes, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


       /* LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
       layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);*/

        //list = new ArrayList<AddCakeClass>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cakes");
        //databaseReference.orderByChild("currentDateTime");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<AddCakeClass>();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    AddCakeClass p = snapshot1.getValue(AddCakeClass.class);
                    // To be deleted before live
                    if(p.currentDateTime == null){
                        p.currentDateTime = "0";
                    }
                   // Log.d(TAG,"V object:::::"+ snapshot1.toString());
                    list.add(p);
                }

                /*
                Sorting List in descending order
                */
                Collections.sort(list, new Comparator<AddCakeClass>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public int compare(AddCakeClass lhs, AddCakeClass rhs) {
                        return rhs.currentDateTime.compareTo(lhs.currentDateTime);
                    }

                });
                myAdapter = new MyAdapter(getContext(),list);
                recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Database Error",Toast.LENGTH_SHORT).show();
            }
        });

        return  view;
    }
}