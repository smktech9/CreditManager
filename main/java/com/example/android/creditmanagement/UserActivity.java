package com.example.android.creditmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        myDB = new DatabaseHelper(this);

        Cursor res = myDB.getAllData();
        if(myDB.isEmpty())
        {
        myDB.insertData("Ajinkya","ajinkya1@gmail.com",50);
        myDB.insertData("Arijit","arijit10@gmail.com",50);
        myDB.insertData("Chirag","chirag12@gmail.com",50);
        myDB.insertData("Diljeet","diljeet15@gmail.com",50);
        myDB.insertData("Komal","komal30@gmail.com",50);
        myDB.insertData("Monisha","monisha56@gmail.com",50);
        myDB.insertData("Priyanka","priyanaka23@gmail.com",50);
        myDB.insertData("Raman","raman88@gmail.com",50);
        myDB.insertData("Shivansh","shivansh19@gmail.com",50);
        myDB.insertData("Mandar","mandar14@gmail.com",50);
        }

            final String name[] = new String[10];
            final String email[] = new String[10];
            final int credits[] = new int[10];

            res.moveToFirst();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UserActivity.this,UserDetail.class);
                intent.putExtra("name",name[position]);
                intent.putExtra("email",email[position]);
                intent.putExtra("credits",credits[position]);
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String>{

        Context context;
        String rName[];
        String rEmail[];
        int rCredits[];

        public MyAdapter(Context context,String name[],String email[],int credits[]) {
            super(context, R.layout.list_item, R.id.name, name);
            this.context = context;
            this.rName = name;
            this.rEmail = email;
            this.rCredits = credits;
        }
            @NonNull
            @Override
            public View getView(int position,@Nullable View convertView,@NonNull ViewGroup parent) {
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

    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(UserActivity.this,MainActivity.class);
        startActivity(goBack);
    }
}
