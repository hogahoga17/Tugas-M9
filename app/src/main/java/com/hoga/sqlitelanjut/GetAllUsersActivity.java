package com.hoga.sqlitelanjut;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class GetAllUsersActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.LayoutManager lm;
    CustomAdapter ca;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_users);

        rv = findViewById(R.id.rv);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);


        databaseHelper = new DatabaseHelper(this);

        final ArrayList<UserModel> dataset;

        dataset = databaseHelper.getAllUsers();

        ca = new CustomAdapter(getApplicationContext(), dataset);
        rv.setAdapter(ca);

        rv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                UserModel t = dataset.get(position);
                Intent intent = new Intent(GetAllUsersActivity.this, UpdateDeleteUsers.class);
                intent.putExtra(UpdateDeleteUsers.EXTRA_USER,t);
                intent.putExtra("name",dataset.get(position).getNama().toString());
                intent.putExtra("hobby",dataset.get(position).getHobby().toString());
                intent.putExtra("city",dataset.get(position).getCity().toString());
                intent.putExtra("department",dataset.get(position).getDepartment().toString());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}