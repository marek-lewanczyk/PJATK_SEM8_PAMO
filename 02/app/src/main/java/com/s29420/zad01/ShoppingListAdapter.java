package com.s29420.zad01;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private final List<ShoppingItem> items;

    public ShoppingListAdapter(List<ShoppingItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shopping, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingItem item = items.get(position);

        holder.tvName.setText(item.getName());
        holder.tvQuantity.setText(item.getQuantity());

        // Clear listener before updating state to avoid spurious callbacks on recycled views
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(item.isPurchased());
        applyStrikethrough(holder, item.isPurchased());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setPurchased(isChecked);
            applyStrikethrough(holder, isChecked);
        });
    }

    private void applyStrikethrough(ViewHolder holder, boolean strike) {
        int flags = holder.tvName.getPaintFlags();
        if (strike) {
            holder.tvName.setPaintFlags(flags | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvQuantity.setPaintFlags(holder.tvQuantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvName.setAlpha(0.4f);
            holder.tvQuantity.setAlpha(0.4f);
        } else {
            holder.tvName.setPaintFlags(flags & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvQuantity.setPaintFlags(holder.tvQuantity.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvName.setAlpha(1f);
            holder.tvQuantity.setAlpha(1f);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final CheckBox checkBox;
        final TextView tvName;
        final TextView tvQuantity;

        ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvQuantity = itemView.findViewById(R.id.tvItemQuantity);
        }
    }
}
