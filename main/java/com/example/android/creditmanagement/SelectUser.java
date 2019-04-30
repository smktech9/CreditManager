package com.example.android.creditmanagement;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectUser extends AppCompatActivity {

    DatabaseHelper myDB;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_user);

        myDB = new DatabaseHelper(this);
        Cursor res = myDB.getAllData();
        res.moveToFirst();
        final String name[] = new String[10];
        final String email[] = new String[10];
        final int credits[] = new int[10];

        int i = 0;
        while (i<10) {
            name[i] = res.getString(res.getColumnIndex("NAME"));
            email[i] = res.getString(res.getColumnIndex("EMAIL"));
            credits[i] = res.getInt(res.getColumnIndex("CURRENTCREDIT"));
            res.moveToNext();
            i++;
        }

        res.close();
        listView = findViewById(R.id.listView);

        MyAdapter adapter = new MyAdapter(this,name,email,credits);
        listView.setAdapter(adapter);

        Intent get = getIntent();
        final int creditCount = get.getIntExtra("noOfCredits",0);
        final String sender = get.getStringExtra("sender");
        final int senderCredits = get.getIntExtra("senderCredits",50);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myDB.insertTransferData(sender,name[position],creditCount);
                myDB.updateData(sender,senderCredits-creditCount);
                myDB.updateData(name[position],credits[position]+creditCount);
                Toast.makeText(SelectUser.this,"Credits transferred", Toast.LENGTH_LONG).show();
                Intent goToAllUsers = new Intent(SelectUser.this,UserActivity.class);
                startActivity(goToAllUsers);
            }
    });
}
    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rName[];
        String rEmail[];
        int rCredits[];

        public MyAdapter(Context context, String name[], String email[], int credits[]) {
            super(context, R.layout.list_item, R.id.name, name);
            this.context = context;
            this.rName = name;
            this.rEmail = email;
            this.rCredits = credits;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.list_item,parent,false);
            TextView myName = view.findViewById(R.id.name);
            TextView myEmail = view.findViewById(R.id.email);
            TextView myCredits = view.findViewById(R.id.credit);

            myName.setText(rName[position]);
            myEmail.setText(rEmail[position]);
            myCredits.setText(Integer.toString(rCredits[position]));

            return view;
        }
    }
}
