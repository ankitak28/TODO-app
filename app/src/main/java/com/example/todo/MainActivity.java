package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.todo.Adapter.DataAdapter;
import com.example.todo.Model.DataModel;
import com.example.todo.utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onDialogCloseListener{

    RecyclerView recyclerView;
    DataBaseHelper dataBaseHelper;
    FloatingActionButton floatingActionButton;
    DataAdapter adapter;
    List<DataModel> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = findViewById(R.id.floating);
        recyclerView = findViewById(R.id.recyclerView);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        adapter = new DataAdapter(dataBaseHelper,MainActivity.this);
        modelList = new ArrayList<>();

        Log.d("123", "onCreate: "+modelList.size());
        modelList = dataBaseHelper.getAllTasks();
        Collections.reverse(modelList);
        adapter.setTasks(modelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTasks.newInstance().show(getSupportFragmentManager(),AddTasks.TAG);
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewItemTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {

        modelList = dataBaseHelper.getAllTasks();
        Collections.reverse(modelList);
        adapter.setTasks(modelList);
        Log.d("123", "onDialogClose: "+modelList.size());
        adapter.notifyDataSetChanged();
    }

}