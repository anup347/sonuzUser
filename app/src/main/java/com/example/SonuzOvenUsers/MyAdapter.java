package com.example.SonuzOvenUsers;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    Context context;
    ArrayList<AddCakeClass> cakeClasses;

    public MyAdapter(Context c, ArrayList<AddCakeClass> p){
        context =c;
        cakeClasses =p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_view,parent,false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        String CakeName =cakeClasses.get(position).getCakeName();
        String CakeDescr =cakeClasses.get(position).getCakeDescription();
        String CakePrice = cakeClasses.get(position).getCakePrice();
        String CakePic = cakeClasses.get(position).getCakePicture();

        holder.rCakeName.setText(CakeName);
        holder.rCakePrice.setText("$"+CakePrice);
        holder.rCakeDescription.setText(CakeDescr);
        Picasso.get().load(CakePic).into(holder.rCakePicture);

        holder.OnClick(position,CakeName,CakePrice,CakeDescr,CakePic);
    }

    @Override
    public int getItemCount() {
        return cakeClasses.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView rCakeName,rCakePrice,rCakeDescription;
        ImageView rCakePicture;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rCakeName = (TextView) itemView.findViewById(R.id.tv_rCakeName);
            rCakePrice = (TextView) itemView.findViewById(R.id.tv_rPrice);
            rCakeDescription= (TextView) itemView.findViewById(R.id.tv_rDescription);
            rCakePicture = (ImageView) itemView.findViewById(R.id.iv_ViewOrderCakeProfilePic);
        }

        public void OnClick(int position, final String Cname, final String Cprice, final String Cdesc, final String Cpic){
            rCakePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Fragment fragment = new Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("CakeName",Cname);
                    bundle.putString("CakeDesc",Cdesc);
                    bundle.putString("CakePrice",Cprice);
                    bundle.putString("Cakepic",Cpic);

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    OrderCake orderCake = new OrderCake();
                    orderCake.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, orderCake).commit();
                }
            });
        }


    }

}
