package com.example.sphinxpfa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;

import com.example.sphinxpfa.adapter.Adapter;
import com.example.sphinxpfa.beans.Pothole;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    java.util.List<Pothole> userList;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFC300\">" + getString(R.string.app_name) + "</font>"));

        initData();
        initRecycleView();

    }

    private void initData() {
        userList= new ArrayList<>();
        userList.add(new Pothole(R.drawable.po1,R.drawable.posit,"Pothole A","2.5 Wt","14.3 Ht","10:43 PM"));
        userList.add(new Pothole(R.drawable.po6,R.drawable.posit,"Pothole B","3.17 Wt","10 Ht","9:22 PM"));
        userList.add(new Pothole(R.drawable.po9,R.drawable.posit,"Pothole C","4 Wt","11 Ht","7:07 PM"));
        userList.add(new Pothole(R.drawable.po7,R.drawable.posit,"Pothole D","12 Wt","2.5 Ht","6:15 PM"));
        userList.add(new Pothole(R.drawable.po4,R.drawable.posit,"Pothole E","15 Wt","14.3 Ht","4:17 PM"));
        userList.add(new Pothole(R.drawable.po5,R.drawable.posit,"Pothole F","1.7 Wt","22 Ht","1:00 PM"));
        userList.add(new Pothole(R.drawable.po6,R.drawable.posit,"Pothole G","5.2 Wt","1.7 Ht","3:02 PM"));
        userList.add(new Pothole(R.drawable.po1,R.drawable.posit,"Pothole H","4.7 Wt","32 Ht","1:00 PM"));
        userList.add(new Pothole(R.drawable.po7,R.drawable.posit,"Pothole I","0.2 Wt","1.9 Ht","7:02 PM"));

    }

    private void initRecycleView() {
        recyclerView=findViewById(R.id.recycleview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new Adapter(userList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}