package com.example.SonuzOvenUsers;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import static android.content.ContentValues.TAG;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolders> {

    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    Context context;
    ArrayList<OrderCakeClass> cakeOClasses;

    public MyOrderAdapter(Context c, ArrayList<OrderCakeClass> p2){
        context =c;
        cakeOClasses =p2;
    }

    @NonNull
    @Override
    public MyViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolders(LayoutInflater.from(context).inflate(R.layout.card_view_orders, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolders holder, final int position) {
        String CakeName =cakeOClasses.get(position).getoCakeName();
       // String CakeDescr =cakeClasses.get(position).getCakeDescription();
        String CakeNotes = cakeOClasses.get(position).getoCakeNotes();
        String CakeStatus = cakeOClasses.get(position).getoCakeStatus();
        String CakePrice = cakeOClasses.get(position).getoCakePrice();
        String CakePic = cakeOClasses.get(position).getoCakePic();


        Log.d(TAG,"P value :::::Cake name"+ cakeOClasses.get(position).getoCakeName());
        holder.oCakeName.setText(CakeName);
        holder.oCakePrice.setText("$"+CakePrice);
        holder.oCakeNotes.setText(CakeNotes);
        holder.oCakeStatus.setText(CakeStatus);
        Picasso.get().load(CakePic).into(holder.oCakePicture);

        //holder.OnClick(position,CakeName,CakePrice,CakeDescr,CakePic);
    }

    @Override
    public int getItemCount() {
        return cakeOClasses.size();
    }



    static class MyViewHolders extends RecyclerView.ViewHolder
    {

        TextView oCakeName,oCakePrice,oCakeNotes,oCakeStatus;
        ImageView oCakePicture;
        public MyViewHolders(@NonNull View itemView) {
            super(itemView);

            oCakeStatus = (TextView) itemView.findViewById(R.id.tv_ViewOrderStatus);
            oCakeName = (TextView) itemView.findViewById(R.id.tv_ViewOrderCakeName);
            oCakePrice = (TextView) itemView.findViewById(R.id.tv_ViewOrderPrice);
            oCakeNotes= (TextView) itemView.findViewById(R.id.tv_ViewOrderNotes);
            oCakePicture = (ImageView) itemView.findViewById(R.id.iv_ViewOrderCakeProfilePic);
        }

   /*     public void OnClick(int position, final String Cname, final String Cprice, final String Cdesc, final String Cpic){
            oCakePrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Fragment fragment = new Fragment();
                   /* Bundle bundle = new Bundle();
                    bundle.putString("CakeName",Cname);
                    bundle.putString("CakeDesc",Cdesc);
                    bundle.putString("CakePrice",Cprice);
                    bundle.putString("Cakepic",Cpic);

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    OrderCake orderCake = new OrderCake();
                    orderCake.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, orderCake).commit();*/
               /* }
            });
        }
*/

    }

}
