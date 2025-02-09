package com.ps.foodfantasy.AdapterAndHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ps.foodfantasy.databinding.RowLayoutProdectBinding;

public class HomeHolder extends RecyclerView.ViewHolder {

    RowLayoutProdectBinding binding;

    public HomeHolder(@NonNull RowLayoutProdectBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
