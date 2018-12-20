package com.hoga.sqlitelanjut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Tambah_department extends AppCompatActivity {

    Button btnstore;
    EditText edderp;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_department);

        databaseHelper = new DatabaseHelper(this);
        btnstore = (Button) findViewById(R.id.btnstore);
        edderp = (EditText) findViewById(R.id.etderp);
        btnstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.addDepartment(edderp.getText().toString());
                edderp.setText("");
                Toast.makeText(Tambah_department.this,"Stored Succesfully !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
