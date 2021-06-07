package com.example.psasa.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.psasa.Model.BetHistory;
import com.example.psasa.R;

import java.util.List;

public class BetTransactionAdapter extends ArrayAdapter<BetHistory> {
    public BetTransactionAdapter(Context context, int resource, List<BetHistory> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item_bet_history, parent, false);
        }

        TextView mGameTv = (TextView) convertView.findViewById(R.id.game_tv);
        TextView mTimeTv = (TextView) convertView.findViewById(R.id.time_tv);
        TextView mStatusTv = (TextView) convertView.findViewById(R.id.status_tv);
        TextView mBoxPlacedTV = (TextView) convertView.findViewById(R.id.box_placed_tv);
        TextView mBoxWonTv = (TextView) convertView.findViewById(R.id.box_won_tv);
        TextView mAmountPlacedTv = (TextView) convertView.findViewById(R.id.amount_placed_tv);
        TextView mWinningsTv = (TextView) convertView.findViewById(R.id.winnings_tv);

        BetHistory betHistory = getItem(position);

//        boolean isPhoto = message.getPhotoUrl() != null;
//        if (isPhoto) {
//            messageTextView.setVisibility(View.GONE);
//            photoImageView.setVisibility(View.VISIBLE);
//            Glide.with(photoImageView.getContext())
//                    .load(message.getPhotoUrl())
//                    .into(photoImageView);
//        } else {
//            messageTextView.setVisibility(View.VISIBLE);
//            photoImageView.setVisibility(View.GONE);
//            messageTextView.setText(message.getText());
//        }
        mGameTv.setText("Game:\t"+betHistory.getGame());
        mTimeTv.setText("Time:\t"+betHistory.getTime());
        mStatusTv.setText("Status:\t"+betHistory.getStatus());
        mBoxPlacedTV.setText("Box Placed:\t"+betHistory.getBoxPlaced());
        mBoxWonTv.setText("Box Won:\t"+betHistory.getBoxWon());
        mAmountPlacedTv.setText("AMOUNT PLACED :"+String.valueOf(betHistory.getAmount()));
        mWinningsTv.setText("Winnings:\t"+betHistory.getWinnings());

        return convertView;
    }
}
