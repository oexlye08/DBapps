package com.example.dbapps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView list_item;
    DatabaseHelper databaseHelper;
    ArrayList<Map<String, Object>>arrayList;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHelper = new DatabaseHelper(this);
        list_item= findViewById(R.id.list_item);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
            }
        });

        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int id=(Integer)arrayList.get(i).get("id");
                final String nama=(String) arrayList.get(i).get("nama");
                final String alamat=(String) arrayList.get(i).get("alamat");

                Intent intent = new Intent(
                        MainActivity.this,
                        InputActivity.class);

                intent.putExtra("id",id);
                intent.putExtra("nama",nama);
                intent.putExtra("alamat",alamat);

                startActivity(intent);
            }
        });

        list_item.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int id=(Integer)arrayList.get(i).get("id");
                tampilkanDialogKonfirmasiHapusCatatan(id);

                return true;
            }
        });
    }

    void tampilkanDialogKonfirmasiHapusCatatan(
            final int id) {
        new AlertDialog.Builder(this)
                .setTitle("Hapus Data ini?")
                .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Hapus",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                hapusFile(id);
                            }
                        })
                .setNegativeButton("Jangan", null)
                .show();
    }

    void hapusFile(int id) {
        databaseHelper.delete(id);
        arrayList.clear();
        mengambilData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mengambilData();
    }
    void mengambilData(){
        arrayList=databaseHelper.getAllStudentsList();
        if(arrayList.size()>0) {
            SimpleAdapter simpleAdapter =
                    new SimpleAdapter(this, arrayList,
                            android.R.layout.simple_list_item_2,
                            new String[]{"nama", "alamat"},
                            new int[]{android.R.id.text1,
                                    android.R.id.text2});

            list_item.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
        }
    }

}
