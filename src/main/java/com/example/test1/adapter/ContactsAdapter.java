package com.example.test1.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test1.Notes;
import com.example.test1.R;
import com.example.test1.call.bean.Calllog;
import com.example.test1.call.bean.Contact;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private Context mContext;
    private List<Contact> mContactList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView contactIcon;
        TextView contactName;
        TextView contactOutCall;
        TextView contactInCall;
        TextView contactMissCall;
        TextView contactNotify;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            contactIcon=view.findViewById(R.id.contact_image);
            contactName=view.findViewById(R.id.contact_name);
            contactOutCall=view.findViewById(R.id.con_out_call);
            contactInCall=view.findViewById(R.id.con_int_call);
            contactMissCall=view.findViewById(R.id.con_miss_call);
            contactNotify=view.findViewById(R.id.contact_notify);
        }
    }

    public ContactsAdapter(List<Contact> mlist){
        this.mContactList=mlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if(mContext == null){
            mContext=parent.getContext();
        }
        View view_notes= LayoutInflater.from(mContext).inflate(R.layout.contacts_item,
                parent,false);
        return new ViewHolder(view_notes);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Contact contact = mContactList.get(position);
        if (contact.mCalllogs.size()>0){
            Calllog log = contact.mCalllogs.get(0);
            holder.contactOutCall.setText(log.mDate);
        }
        holder.contactName.setText(contact.mName);
//        holder.contactOutCall.setText("去电"+contact.mOutGoingNum+"次");
//        holder.contactInCall.setText("来电"+contact.mIncomingNum+"次");
//        holder.contactMissCall.setText("未接听"+contact.mMissNum+"次");

        if (contact.isNotify){
            String notify = "好久没联系了，快联系我吧！";
            holder.contactNotify.setText(notify);
            holder.contactNotify.setTextColor(Color.RED);
        }else{
            String notify = getNotify(contact);
            holder.contactNotify.setText(notify);
            holder.contactNotify.setTextColor(Color.BLACK);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "内部点击", Toast.LENGTH_SHORT).show();
                call(contact.mNumber);
            }
        });

    }
    public void call(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        mContext.startActivity(intent);
      }
    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public String getNotify(Contact contact){
        String notify = "最近没有联系哦";
        switch(contact.mLevel){
            case 0:
                notify = "最近没有联系哦";
            break;
            case 1:
                notify = "这两天有联系";
                break;
            case 2:
                notify = "最近交流的很密切哦";
                break;
            default:
            break;
        }
        return notify;
    }
}
