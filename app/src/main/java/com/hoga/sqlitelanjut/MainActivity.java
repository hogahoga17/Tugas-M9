package com.hoga.sqlitelanjut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnstore,btnGetall,btnderp;
    private EditText etname, ethobby, etcity;
    private Spinner spinner;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        btnGetall = (Button) findViewById(R.id.btnget);
        btnstore = (Button) findViewById(R.id.btnstore);
        btnderp = (Button) findViewById(R.id.btnderp);
        etname = (EditText)findViewById(R.id.etname);
        ethobby = (EditText)findViewById(R.id.ethobby);
        etcity = (EditText)findViewById(R.id.etcity);
        spinner = (Spinner) findViewById(R.id.spin);

        refreshListDepartment();
        btnderp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(MainActivity.this,Tambah_department.class);
                startActivity(m);
            }
        });

        btnstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.addUser(etname.getText().toString(),ethobby.getText().toString(),etcity.getText().toString(),spinner.getSelectedItem().toString());
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
        btnGetall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetAllUsersActivity.class);
                startActivity(intent);
            }
        });
    }
    public void refreshListDepartment(){
        ArrayList<String> arr = databaseHelper.getAllDepartment();
        String[] arraySpinner = new String[arr.size()];
        for (int i = 0 ; i<arr.size();i++){
            arraySpinner[i] = arr.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        spinner.setAdapter(adapter);
    }
}
