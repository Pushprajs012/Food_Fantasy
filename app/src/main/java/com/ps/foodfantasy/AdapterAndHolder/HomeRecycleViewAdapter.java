package com.ps.foodfantasy.AdapterAndHolder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps.foodfantasy.Data.CartData;
import com.ps.foodfantasy.Data.FoodFantasyData;

import com.ps.foodfantasy.UI.ProductDetailAcivity;
import com.ps.foodfantasy.databinding.RowLayoutProdectBinding;

import java.util.List;

public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeHolder> {

    private List<FoodFantasyData> fantasyDataList;
    private FirebaseDatabase firebaseDatabase;
    private String uid;

    // Corrected Constructor Name
    public HomeRecycleViewAdapter(List<FoodFantasyData> fantasyDataList, FirebaseDatabase firebaseDatabase, String uid) {
        this.fantasyDataList = fantasyDataList;
        this.firebaseDatabase = firebaseDatabase;
        this.uid = uid;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutProdectBinding binding = RowLayoutProdectBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new HomeHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        FoodFantasyData foodFantasyData = fantasyDataList.get(position);

        // Binding data to views
        holder.binding.name.setText(foodFantasyData.getName());
        holder.binding.price.setText("$"+foodFantasyData.getPrice());
     //   holder.binding.shoeColors.setText("Size: "+foodFantasyData.getSize());
        Glide.with(holder.binding.pizzaImage.getContext())
                .load(foodFantasyData.getImage())
                .into(holder.binding.pizzaImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ProductDetailAcivity.class);
            intent.putExtra("fooddata", foodFantasyData.getProduct_id());
            holder.itemView.getContext().startActivity(intent);
        });

        holder.binding.addButton.setOnClickListener(v -> {
            //check to data is already in cart
            firebaseDatabase.getReference("Users")
                    .child(uid)
                    .child("Cart")
                    .child(foodFantasyData.getProduct_id())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(holder.itemView.getContext(), "Product already in cart", Toast.LENGTH_SHORT).show();

                    } else {
                        CartData cartData=new CartData(foodFantasyData.getProduct_id(),foodFantasyData.getName(),foodFantasyData.getPrice(),foodFantasyData.getSize(),foodFantasyData.getImage(),foodFantasyData.getDescription(),1);
                        firebaseDatabase.getReference("Users").child(uid).child("Cart").child(foodFantasyData.getProduct_id()).setValue(cartData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(holder.itemView.getContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                            }
                        });
                         }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        });
    }

    @Override
    public int getItemCount() {

        return fantasyDataList.size();
    }
}
