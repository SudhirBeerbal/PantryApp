package com.example.pantryapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pantryapp.R;
import com.example.pantryapp.database.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    private List<PantryItem> items = new ArrayList<>();

    public PantryAdapter(OnItemClickListener listener, List<PantryItem> items) {
        this.listener = listener;
        this.items = items;
    }

    public interface OnItemClickListener {
        void onItemClick(PantryItem item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PantryItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<PantryItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvDetails;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvDetails = itemView.findViewById(R.id.tvItemDetails);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(items.get(position));
                }
            });
        }

        void bind(PantryItem item) {
            tvName.setText(item.ingredientName);
            StringBuilder details = new StringBuilder(item.quantity);
            if (item.unit != null && !item.unit.isEmpty()) {
                details.append(" ").append(item.unit);
            }
            if (item.expiryDate != null && !item.expiryDate.isEmpty()) {
                details.append(" | Expires: ").append(item.expiryDate);
            }
            tvDetails.setText(details.toString());
        }
    }
}
