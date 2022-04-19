package com.example.binkhathlan_midt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final DatabaseHelper myDB = new DatabaseHelper(this);

        EditText inputID = (EditText) findViewById(R.id.inputID);
        EditText inputName = (EditText) findViewById(R.id.inputName);
        EditText inputSurname = (EditText) findViewById(R.id.inputSurname);
        EditText inputNID = (EditText) findViewById(R.id.inputNID);

        Button insert = (Button) findViewById(R.id.btnInsert);

        myDB.addData("200031","Naif", "Binkhathlan",  "111");
        myDB.addData("200032","Nawaf", "Binkhathlan",  "222");

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myDB.addData(inputID.getText().toString(), inputName.getText().toString(),  inputSurname.getText().toString(), inputNID.getText().toString()))
                    Toasty.error(getBaseContext(), "Failed to add", Toast.LENGTH_SHORT, true).show();
                else Toasty.info(getBaseContext(), "Added "+inputName.getText().toString()+" Successfully", Toast.LENGTH_SHORT, true).show();
                Log.d("Naif","Added "+inputName.getText().toString()+" Successfully");
            }
        });

        Button goTo1 = (Button) findViewById(R.id.btnFrom2GoTo1);
        Button goTo3 = (Button) findViewById(R.id.btnFrom2GoTo3);

        goTo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, MainActivity.class));
            }
        });
        goTo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this,MainActivity3.class));
            }
        });
    }
}