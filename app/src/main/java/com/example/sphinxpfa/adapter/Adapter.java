package com.example.sphinxpfa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sphinxpfa.R;
import com.example.sphinxpfa.beans.Pothole;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Pothole> userList;
    public Adapter(List<Pothole>userList){
        this.userList=userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resource= userList.get(position).getImageview1();
        int resource1 = userList.get(position).getImageview2();
        String name= userList.get(position).getTextview1();
        String width= userList.get(position).getTextview2();
        String height= userList.get(position).getTextview3();
        String time= userList.get(position).getTextview4();
        holder.setData(resource,resource1,name,width,height,time);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgPo;
        private ImageView imagemap;
        private TextView name;
        private TextView width;
        private TextView height;
        private TextView Date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPo=itemView.findViewById(R.id.imageview1);
            imagemap=itemView.findViewById(R.id.imageview2);
            name=itemView.findViewById(R.id.textview1);
            width=itemView.findViewById(R.id.textview2);
            height=itemView.findViewById(R.id.textview3);
            Date=itemView.findViewById(R.id.textview4);
        }
        public void setData(int resource,int resource1, String n, String w, String h, String time) {
            imgPo.setImageResource(resource);
            imagemap.setImageResource(resource1);
            name.setText(n);
            width.setText(w);
            height.setText(h);
            Date.setText(time);
        }
    }
}
