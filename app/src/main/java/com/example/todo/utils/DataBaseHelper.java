package com.example.todo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todo.Model.DataModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String DB_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "STATUS";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,TASK TEXT,STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void insert(DataModel data){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2,data.getStatus());
        values.put(COL_3,data.getTask());
        db.insert(TABLE_NAME,null,values);
    }
    public void deleteTask(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(id)});
    }
    public void updateTask(int id,String task){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,task);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{String.valueOf(id)});
    }
    public void updateStatus(int id,int status){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3,status);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{String.valueOf(id)});
    }
    public List<DataModel> getAllTasks(){
        db = this.getWritableDatabase();
        List<DataModel> dataModelList = new ArrayList<>();
        Cursor cursor =null;
        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME,null,null,null,null,null,null);
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do {
                        DataModel dataModel = new DataModel();
                        dataModel.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        dataModel.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                        dataModel.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
                        dataModelList.add(dataModel);
                    }while (cursor.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            cursor.close();
        }
        return dataModelList;
    }
}

