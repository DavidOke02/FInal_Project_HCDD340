package edu.psu.ist.hcdd340.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// CardAdapter.java
public class GlanceCardAdapter extends RecyclerView.Adapter<GlanceCardAdapter.CardViewHolder> {

    private LayoutInflater mInflater;
    private GlanceCardData[] cardData;

    public GlanceCardAdapter(Context context, GlanceCardData[] cardData) {
        mInflater = LayoutInflater.from(context);
        this.cardData = cardData;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.glance_card_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        String cardTitle = cardData[position].getCardTitle();
        holder.cardTitle.setText(cardTitle);
        String cardText = cardData[position].getCardText();
        holder.cardText.setText(cardText);
        holder.cardNumber.setText(position + 1 + "/" + getItemCount()); //Setting Card Num Display
    }

    @Override
    public int getItemCount() {
        return cardData.length;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView cardTitle;
        TextView cardText;
        TextView cardNumber;

        public CardViewHolder(View itemView) {
            super(itemView);
            cardTitle = itemView.findViewById(R.id.glance_panel_subtitle);
            cardText = itemView.findViewById(R.id.glance_panel_text);
            cardNumber = itemView.findViewById(R.id.glance_panel_num_display);
        }
    }
}

