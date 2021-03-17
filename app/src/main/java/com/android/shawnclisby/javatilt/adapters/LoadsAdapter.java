package com.android.shawnclisby.javatilt.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.android.shawnclisby.javatilt.LoadViewHolder;
import com.android.shawnclisby.javatilt.databinding.ViewHolderLoadBinding;
import com.android.shawnclisby.javatilt.models.Load;

public class LoadsAdapter extends ListAdapter<Load, LoadViewHolder> {

    public LoadsAdapter() {
        super(new DiffUtil.ItemCallback<Load>() {
            @Override
            public boolean areItemsTheSame(@NonNull Load oldItem, @NonNull Load newItem) {
                return oldItem.ord_hdrnumber == newItem.ord_hdrnumber;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Load oldItem, @NonNull Load newItem) {
                return oldItem.ord_hdrnumber == newItem.ord_hdrnumber;
            }
        });
    }

    @NonNull
    @Override
    public LoadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LoadViewHolder(ViewHolderLoadBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LoadViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }
}
