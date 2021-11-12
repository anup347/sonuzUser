package com.example.SonuzOvenUsers;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.SonuzOvenUsers.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.ContentValues.TAG;

public class OrderStatusFragment  extends Fragment{

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<OrderCakeClass> olist;
    MyOrderAdapter myOrderAdapter;
    private FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_status_fragment,container,false);

        //Create Action Bar
        //ActionBar actionBar =  ((AppCompatActivity) getActivity()).getSupportActionBar();
        //Set Action bar
        //actionBar.setDisplayHomeAsUpEnabled(false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_CakeOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseAuth = FirebaseAuth.getInstance();

        //Setttng database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders").child(firebaseAuth.getUid());
       // Log.d(TAG,"P value :::::"+firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                olist = new ArrayList<OrderCakeClass>();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                   // AddCakeClass p = snapshot1.getValue(AddCakeClass.class);
                    OrderCakeClass p2 = snapshot1.getValue(OrderCakeClass.class);

                    olist.add(p2);
                   // Log.d(TAG,"P object:::::"+ snapshot1.toString());
                }

                /*
                Sorting List in descending order
                */
                Collections.sort(olist, new Comparator<OrderCakeClass>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public int compare(OrderCakeClass lhs, OrderCakeClass rhs) {
                        return rhs.ocurrentDateTime.compareTo(lhs.ocurrentDateTime);
                    }

                });
                myOrderAdapter = new MyOrderAdapter(getContext(),olist);
                recyclerView.setAdapter(myOrderAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Database Error",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
