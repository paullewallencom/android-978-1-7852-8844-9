package com.example.kyle.ancientbritain;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private ArrayList<MainDataDef> mainData;

    public MainAdapter(ArrayList<MainDataDef> a) {
        this.mainData = a;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_main, parent, false);

        v.setOnClickListener(MainActivity.mainOnClickListener);

        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, final int position) {

        ImageView imageIcon = holder.imageIcon;
        TextView textName = holder.textName;
        TextView textInfo = holder.textInfo;

        imageIcon.setImageResource(mainData.get(position).getImage());
        textName.setText(mainData.get(position).getName());
        textInfo.setText(mainData.get(position).getInfo());
    }

    @Override
    public int getItemCount() {
        return mainData.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        ImageView imageIcon;
        TextView textName;
        TextView textInfo;

        public MainViewHolder(View v) {
            super(v);
            this.imageIcon = (ImageView) v.findViewById(R.id.card_image);
            this.textName = (TextView) v.findViewById(R.id.card_name);
            this.textInfo = (TextView) v.findViewById(R.id.card_info);
        }
    }
}
