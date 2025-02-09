package com.ps.foodfantasy.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ps.foodfantasy.AdapterAndHolder.HomeRecycleViewAdapter;
import com.ps.foodfantasy.Application;

import com.ps.foodfantasy.Data.FoodFantasyData;
import com.ps.foodfantasy.R;
import com.ps.foodfantasy.databinding.ActivityProductBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityProductBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;
    private HomeRecycleViewAdapter adapter;
    private List<FoodFantasyData> foodFantasyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchDataFromFirebase();
       // binding.toolbar.setTitle(auth.getCurrentUser().getDisplayName());
       // setname();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        foodFantasyData =new ArrayList<>();
        adapter=new HomeRecycleViewAdapter(foodFantasyData,firebaseDatabase,auth.getCurrentUser().getUid());
        binding.recyclerView.setAdapter(adapter);
    }


    private void init(){
        firebaseDatabase= ((Application) getApplicationContext()).getFirebaseDatabase();
        auth=((Application) getApplicationContext()).getFirebaseAuth();
        binding.menucart.setOnClickListener(this);
      //  binding.logout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.menucart){
            startActivity(new Intent(ProductActivity.this, CartActivity.class));
        }

//        if (v.getId()==R.id.logout){
//            auth.signOut();
//            startActivity(new Intent(ProductActivity.this, LoginActivity.class));
//            finish();
//        }

    }

    private void fetchDataFromFirebase(){
        firebaseDatabase.getReference("food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodFantasyData.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    FoodFantasyData fantasyData = dataSnapshot.getValue(FoodFantasyData.class);
                    foodFantasyData.add(fantasyData);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    private void setname(){
        firebaseDatabase.getReference("Users").child(auth.getCurrentUser().getUid()).child("User").child("fullName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //binding.name.setText(snapshot.getValue(String.class));
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}