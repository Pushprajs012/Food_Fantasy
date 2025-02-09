package com.ps.foodfantasy.AdapterAndHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ps.foodfantasy.databinding.RowLayoutCartBinding;

public class CartHolder extends RecyclerView.ViewHolder {

    RowLayoutCartBinding binding;

    public CartHolder(@NonNull RowLayoutCartBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
