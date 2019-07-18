package com.example.dbapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME="db_database";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_DATA="data";
    private static final String KEY_ID="id";
    private static final String KEY_NAMA="name";
    private static final String KEY_ALAMAT="alamat";
    private static final String CREATE_TABLE_DATA=
            "CREATE TABLE "
                    +TABLE_DATA
                    +"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +KEY_NAMA+" TEXT,"
                    +KEY_ALAMAT+" TEXT)"
            ;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" +TABLE_DATA+ "'");
        onCreate(sqLiteDatabase);
    }

    public long addDataDetail(String nama, String alamat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAMA, nama);
        values.put(KEY_ALAMAT, alamat);
        long insert = db.insert(TABLE_DATA, null, values);
        return insert;
    }

    public ArrayList<Map<String, Object>> getAllStudentsList(){
        ArrayList<Map<String, Object>> studentArrayList
                =new ArrayList<>();
        String nama="";
        String alamat="";
        int id=0;
        String selectQuery="SELECT * FROM "+TABLE_DATA;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor c=db.rawQuery(selectQuery,null);
        if(c.moveToFirst()){
            do{
                id=c.getInt(c.getColumnIndex(
                        KEY_ID));
                nama=c.getString(c.getColumnIndex(
                        KEY_NAMA));
                alamat=c.getString(c.getColumnIndex(
                        KEY_ALAMAT));
                Map<String, Object> listItemMap = new HashMap<>();
                listItemMap.put("id", id);
                listItemMap.put("nama", nama);
                listItemMap.put("alamat", alamat);
                studentArrayList.add(listItemMap);
            }while (c.moveToNext());
        }
        return studentArrayList;
    }

    public void update(int id, String nama,String alamat){
        SQLiteDatabase db =this.getWritableDatabase();
        String updateQuery="UPDATE "+TABLE_DATA+" SET "
                +KEY_NAMA+ "='"+nama+"',"
                +KEY_ALAMAT+"='"+alamat+"' WHERE "
                +KEY_ID+ "='"+id+"'";
        db.execSQL(updateQuery);
        db.close();
    }
    public void delete(int id){
        SQLiteDatabase db =this.getWritableDatabase();
        String updateQuery="DELETE FROM "+TABLE_DATA
                +" WHERE " +KEY_ID+ "='"+id+"'";
        db.execSQL(updateQuery);
        db.close();
    }
}
