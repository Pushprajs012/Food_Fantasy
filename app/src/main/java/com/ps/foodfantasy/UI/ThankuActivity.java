package com.ps.foodfantasy.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.ps.foodfantasy.R;
import com.bumptech.glide.Glide;
import com.ps.foodfantasy.databinding.ActivityThankuBinding;

public class ThankuActivity extends AppCompatActivity {
    private ActivityThankuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityThankuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbtn.setOnClickListener(v -> {
            startActivity(new Intent(this,ProductActivity.class));
            finish();
        });


        Glide.with(this).load(R.drawable.thanku).into(binding.thanku);


    }
}