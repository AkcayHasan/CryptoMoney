package com.hasanakcay.cryptomoney.adapter;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hasanakcay.cryptomoney.R;
import com.hasanakcay.cryptomoney.model.CryptoModel;

import java.util.ArrayList;

public class CryptoRecyclerAdapter extends RecyclerView.Adapter<CryptoRecyclerAdapter.CryptoViewHolder> {

    private ArrayList<CryptoModel> cryptoList;
    private String[] colors = {"#E74C3C","#3498DB","#1ABC9C","#F39C12","#95A5A6","#AF7AC5","#D2B4DE"};

    public CryptoRecyclerAdapter(ArrayList<CryptoModel> cryptoList) {
        this.cryptoList = cryptoList;
    }

    public class CryptoViewHolder extends RecyclerView.ViewHolder {
        TextView recycler_text_name;
        TextView recycler_text_price;

        public CryptoViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(CryptoModel cryptoModel, String[] colors, Integer position){
            itemView.setBackgroundColor(Color.parseColor(colors[position % 7]));
            recycler_text_name = itemView.findViewById(R.id.recycler_text_name);
            recycler_text_price = itemView.findViewById(R.id.recycler_text_price);
            recycler_text_price.setText(cryptoModel.price);
            recycler_text_name.setText(cryptoModel.currency);
        }
    }

    @NonNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_row,parent,false);
        return new CryptoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoViewHolder holder, int position) {
        holder.bind(cryptoList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }


}
