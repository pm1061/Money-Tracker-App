package com.pedromalavet.pedrosmoneytracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;



public class recyclerApdater extends RecyclerView.Adapter<recyclerApdater.MyViewHolder>
{

    private ArrayList<MoneySource> sList;
    private OnSourceListener mOnSourceListener;

    public recyclerApdater(ArrayList<MoneySource> sList,OnSourceListener onSourceListener)
    {
        this.sList = sList;
        this.mOnSourceListener = onSourceListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,View.OnLongClickListener
    {
        private TextView nameTxt;
        private TextView moneyTxt;
        OnSourceListener onSourceListener;

        public MyViewHolder(View view,OnSourceListener onSourceListener)
        {
            super(view);
            nameTxt = view.findViewById(R.id.NameTextView);
            moneyTxt = view.findViewById(R.id.moneyTextView);
            this.onSourceListener = onSourceListener;


            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }


        @Override
        public void onClick(View view)
        {
            onSourceListener.onSourceClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view)
        {
            onSourceListener.onSourceLongClick(getAdapterPosition());
            return true;
        }

    }

    @NonNull
    @Override
    public recyclerApdater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        return new MyViewHolder(itemView,mOnSourceListener);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerApdater.MyViewHolder holder, int i)
    {

        String name = sList.get(i).getName();
        double money = sList.get(i).getMoney();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(money);


        holder.nameTxt.setText(name);
        holder.moneyTxt.setText(moneyString);
    }

    @Override
    public int getItemCount()
    {
        return sList.size();
    }

    public interface OnSourceListener
    {
        void onSourceClick(int pos);
        boolean onSourceLongClick(int pos);
    }
}
