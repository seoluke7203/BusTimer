package com.example.bustimer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {


    private ArrayList<String> stringArray;

    public MainAdapter(ArrayList<String> stringArray) {
        this.stringArray = stringArray;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.mFullName.setText(stringArray.get(position));
    }

    @Override
    public int getItemCount() {
        return stringArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mFullName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFullName = (TextView) itemView.findViewById(R.id.full_name);
        }
    }
}
