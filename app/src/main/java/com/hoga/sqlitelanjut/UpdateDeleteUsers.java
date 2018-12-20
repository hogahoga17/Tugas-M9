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

public class UpdateDeleteUsers extends AppCompatActivity {

    EditText etname, ethobby, etcity;
    Spinner spinner;
    Button btnupdate, btndelete;
    DatabaseHelper databaseHelper;
    public static final String EXTRA_USER = "extra_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_users);

        Intent gIntent = getIntent();
        final UserModel userModel = (UserModel) gIntent.getSerializableExtra(EXTRA_USER);
        databaseHelper = new DatabaseHelper(this);

        etname = (EditText) findViewById(R.id.etname);
        ethobby = (EditText) findViewById(R.id.ethobby);
        etcity = (EditText) findViewById(R.id.etcity);
        spinner = (Spinner) findViewById(R.id.spin);

        etname.setText(userModel.getNama());
        ethobby.setText(userModel.getHobby());
        etcity.setText(userModel.getCity());

        ArrayList<String> arr = databaseHelper.getAllDepartment();
        String[] arraySpinner = new String[arr.size()];
        for (int i = 0 ; i<arr.size();i++){
            arraySpinner[i] = arr.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        spinner.setAdapter(adapter);
        spinner.setSelection(gIntent.getIntExtra("spin",0));

        btnupdate = (Button) findViewById(R.id.btnupdate);
        btndelete = (Button) findViewById(R.id.btndelete);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.updateUser(userModel.getId_users(),etname.getText().toString(),ethobby.getText().toString(),etcity.getText().toString());
                Toast.makeText(UpdateDeleteUsers.this, "Updated Successfully!",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(UpdateDeleteUsers.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteUSer(userModel.getId_users());
                Toast.makeText(UpdateDeleteUsers.this, "Deleted Successfully!",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(UpdateDeleteUsers.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}

