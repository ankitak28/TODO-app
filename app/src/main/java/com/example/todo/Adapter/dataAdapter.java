package com.example.todo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.MainActivity;
import com.example.todo.Model.DataModel;
import com.example.todo.R;
import com.example.todo.utils.DataBaseHelper;

import java.util.List;

public class dataAdapter extends RecyclerView.Adapter<dataAdapter.MyViewHolder> {

    private DataBaseHelper db;
    private MainActivity activity;
    private List<DataModel> dataModelList;
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    dataAdapter(DataBaseHelper dataBaseHelper, MainActivity activity){
        this.db = dataBaseHelper;
        this.activity = activity;
    }

    public Context getContext(){
        return activity;
    }
    public void setTasks(List<DataModel> list){
        this.dataModelList = list;
        notifyDataSetChanged();
    }
    public void deleteTasks(int position){
        DataModel model = dataModelList.get(position);
        db.deleteTask(model.getId());
        dataModelList.remove(position);
        notifyDataSetChanged();
    }
    public void editTask(int position){
        
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataModel dataModel = dataModelList.get(position);
        holder.checkBox.setText(dataModel.getTask());
        holder.checkBox.setChecked(checkboxStatus(dataModel.getStatus()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(dataModel.getId(),1);
                }
                else{
                    db.updateStatus(dataModel.getId(),0);
                }
            }
        });
    }

    private boolean checkboxStatus(int num){
        return num!=0?false:true;
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

}
