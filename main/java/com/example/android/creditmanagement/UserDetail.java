package com.example.android.creditmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserDetail extends AppCompatActivity {

    TextView nameText , emailText , creditsText;
    Button transfer;
    EditText creditsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);

        final Intent intent = getIntent();
        final String name1 = intent.getStringExtra("name");
        String email1 = intent.getStringExtra("email");
        final int credits1 = intent.getIntExtra("credits",50);

        nameText =  findViewById(R.id.name1);
        emailText =  findViewById(R.id.email1);
        creditsText =  findViewById(R.id.credits1);
        creditsEditText =  findViewById(R.id.noOfCredits);

        nameText.setText(name1);
        emailText.setText(email1);
        creditsText.setText(Integer.toString(credits1));

        transfer = findViewById(R.id.transferCredits);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(creditsEditText.getText().toString().equals(""))
                {
                    Toast.makeText(UserDetail.this,"Enter no. of credits",Toast.LENGTH_LONG).show();
                }
                else {
                    int noOfCredits = Integer.parseInt(creditsEditText.getText().toString());
                    if (noOfCredits < 0 || noOfCredits > credits1) {
                        Toast.makeText(UserDetail.this, "Invalid no. of credits", Toast.LENGTH_LONG).show();
                    } else {
                        Intent open = new Intent(UserDetail.this, SelectUser.class);
                        open.putExtra("noOfCredits", noOfCredits);
                        open.putExtra("sender", name1);
                        open.putExtra("senderCredits", credits1);
                        startActivity(open);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(UserDetail.this,UserActivity.class);
        startActivity(goBack);
    }
}
