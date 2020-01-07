package com.example.test1;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PeoplesAdapter extends RecyclerView.Adapter<PeoplesAdapter.ViewHolder> {
    private Context mContext;

    private List<Peoples> mPeoplesList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView peoplesImage;
        TextView peoplesName;

        public  ViewHolder(View view){
            super(view);
            cardView=(CardView) view;
            peoplesImage=(ImageView) view.findViewById(R.id.peoples_image);
            peoplesName=(TextView) view.findViewById(R.id.peoples_name);
        }
    }

    public PeoplesAdapter(List<Peoples> peoplesList){
        mPeoplesList = peoplesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.peoples_item,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Peoples peoples = mPeoplesList.get(position);
        holder.peoplesName.setText(peoples.getName());
        Glide.with(mContext).load(peoples.getImageId()).into(holder.peoplesImage);
    }

    @Override
    public int getItemCount() {
        return mPeoplesList.size();
    }
}
