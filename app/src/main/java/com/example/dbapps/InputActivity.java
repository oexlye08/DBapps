package com.example.dbapps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {
    EditText edit_nama, edit_alamat;
    DatabaseHelper databaseHelper;
    int id;
    String nama, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edit_nama=findViewById(R.id.edit_nama);
        edit_alamat=findViewById(R.id.edit_alamat);
        databaseHelper= new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        id=-1;

        if (extras != null) {
            getSupportActionBar().setTitle("Ubah Data");
            id=extras.getInt("id");
            nama=extras.getString("nama");
            alamat=extras.getString("alamat");
            edit_nama.setText(nama);
            edit_alamat.setText(alamat);

        } else {
            getSupportActionBar().setTitle("Tambah Data");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }


    public void btnSubmit(View view) {
        if(id==-1) {
            databaseHelper.addDataDetail(
                    edit_nama.getText().toString()
                    , edit_alamat.getText().toString());
        }else {
            databaseHelper.update(id,edit_nama.getText().toString(),
                    edit_alamat.getText().toString());
        }
        onBackPressed();
    }

    public void cancel(View view) {
        onBackPressed();
    }
}
