package com.example.SonuzOvenUsers;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SonuzOvenUsers.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderCake} factory method to
 * create an instance of this fragment.
 */
public class OrderCake extends Fragment {



    /**
     * Use this factory method to create a new instance of
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    private Button btn_OrderCake;
    private TextView tv_CakeName;
    private TextView tv_CakeDescription;
    private TextView tv_CakePrice;
    private EditText et_CakeNotes;
    private ImageView iv_CakeImage;
    private ProgressBar progressBar;


    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private  StorageReference cakeImageRefrence;
    private DatabaseReference myCakeRef;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_order_cake, container, false);

        //Fetching bundle from adapter
        Bundle bundle = this.getArguments();
        //Fetching bandle values
        final String Cname =  bundle.getString("CakeName");
        String Cdesc =  bundle.getString("CakeDesc");
        String Cprice =  bundle.getString("CakePrice");
        final String Cpic =  bundle.getString("Cakepic");

        //Setting up view elements
        setUICakeViews(view);

        //Setting Firebase Auth instance
        firebaseAuth = FirebaseAuth.getInstance();
        //Setting firebase Storage reference
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference =  firebaseStorage.getReference();

        // Setting data to View elements
        setData(Cname,Cdesc,Cprice,Cpic);

        //Setting progress bar to hidden during load
        progressBar.setVisibility(view.INVISIBLE);

        /*
        On click listener to update Cake details
        */
        btn_OrderCake.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                    //Set order function
                    sendCakeOrderData(v,Cpic);
                }

        });



        return view;

    }

    private  void  setUICakeViews(View view){
        iv_CakeImage = (ImageView) view.findViewById(R.id.iv_OrderCakeImage);
        btn_OrderCake = (Button) view.findViewById(R.id.btn_PlaceOrder);
        tv_CakeName= (TextView) view.findViewById(R.id.tv_OrderCakeName);
        tv_CakeDescription = (TextView) view.findViewById(R.id.tv_OrderCakeDescription);
        tv_CakePrice = (TextView) view.findViewById(R.id.tv_OrderCakePrice);
        et_CakeNotes = (EditText)view.findViewById(R.id.et_OrderCakeNotes);
        progressBar = (ProgressBar) view.findViewById(R.id.pb_OrderCakeProgressBar);

    }

    private  void  setData(String Cname,String Cdesc, String Cprice, String Cpic){
        tv_CakeName.setText(Cname);
        tv_CakeDescription.setText(Cdesc);
        tv_CakePrice.setText(Cprice);
        Picasso.get().load(Cpic).fit().centerCrop().into(iv_CakeImage);
    }



    /*
Sending Cake date to Firebase
*
*/

    private void sendCakeOrderData(final View v, String Cpic){
        progressBar.setVisibility(v.VISIBLE);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        myCakeRef = firebaseDatabase.getReference("Orders");

        String CakeName = tv_CakeName.getText().toString().trim();
        String CakeDescription = tv_CakeDescription.getText().toString().trim();
        String CakePrice = tv_CakePrice.getText().toString().trim();
        String CakeNotes =et_CakeNotes.getText().toString().trim();
        long dateFormat = android.icu.util.Calendar.getInstance().getTimeInMillis();
        String Status = "Order Placed";

        OrderCakeClass orderCakeClass = new OrderCakeClass(CakeName,CakeDescription,CakePrice,Cpic,CakeNotes,Status,String.valueOf(dateFormat),firebaseAuth.getUid());
        myCakeRef.child(firebaseAuth.getUid()).child(String.valueOf(dateFormat)).setValue(orderCakeClass);

        progressBar.setVisibility(v.INVISIBLE);
        Toast.makeText(getContext(),"Order Placed, Check your status in Order Status page",Toast.LENGTH_SHORT).show();
        //getActivity().finish();
        getFragmentManager().beginTransaction().replace(R.id.container_fragment,new ViewCakes()).commit();

    }
}