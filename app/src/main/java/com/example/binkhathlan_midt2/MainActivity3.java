package com.example.binkhathlan_midt2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Handler;

import es.dmoral.toasty.Toasty;

public class MainActivity3 extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        DatabaseHelper myDB = new DatabaseHelper(this);

        Button viewTable = (Button) findViewById(R.id.btnViewT);
        TextView tab = (TextView) findViewById(R.id.tView);

        //Print All Table

        viewTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur = myDB.ViewTable();
                StringBuffer buffer = new StringBuffer();
                while (cur.moveToNext()){
                    buffer.append ("id: " + cur.getString(0)+ " ");
                    buffer.append("Name: " + cur.getString( 1)+ " ");
                    buffer.append("Email: " + cur.getString( 2)+ " ");
                    buffer.append("Phone: " + cur.getString( 3)+ " ");
                    buffer.append("PID: "+ cur.getString( 4)+ "\n");
                }
                tab.setText(buffer.toString());

                Toast.makeText(MainActivity3.this, "Successful View", Toast.LENGTH_LONG).show();

            }
        });


        /*
        viewTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur = myDB.ViewTable();
                StringBuffer buffer = new StringBuffer();
                while (cur.moveToNext()){
                    buffer.append ("id: " + cur.getString(0)+ " ");
                    buffer.append("Name: " + cur.getString( 1)+ " ");
                    buffer.append("Email: " + cur.getString( 2)+ " ");
                    buffer.append("Phone: " + cur.getString( 3)+ " ");
                    buffer.append("PID: "+ cur.getString( 4)+ "\n");
                }
                tab.setText(buffer.toString());

                Toast.makeText(MainActivity3.this, "Successful View", Toast.LENGTH_LONG).show();

                String st = new String(str);
                Log.e("Main",st); String[] rows = st.split("_"); TableLayout tableLayout = (TableLayout)findViewById(R.id.tab); tableLayout.removeAllViews();

                for(int i=0;i<rows.length;i++){>
                    String row  = rows[i];
                    TableRow tableRow = new TableRow(getApplicationContext());
                    final String[] cols = row.split(";");

                    Handler handler = null;

                    for (int j = 0; j < cols.length; j++) {

                        final String col = cols[j];
                        final TextView columsView = new TextView(getApplicationContext());
                        columsView.setText(String.format("%7s", col));
                        tableRow.addView(columsView);

            }
        });
            }
                                     }
                                     */



        EditText input = (EditText)findViewById(R.id.input);
        Button del = (Button) findViewById(R.id.btnDel);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String in = input.getText().toString();
                if(myDB.deleteField(in)) {
                    Toasty.info(getBaseContext(), del+" Deleted.", Toast.LENGTH_SHORT, true).show();
                    Log.d("Naif",del+" Deleted.");
                }
                else {
                    Toasty.info(getBaseContext(), "ID Not In Database.", Toast.LENGTH_SHORT, true).show();
                    Log.d("Naif","ID Not In Database.");
                }
            }

        });

        Button goTo2 = findViewById(R.id.btnFrom3GoTo2);
        goTo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity3.this, MainActivity2.class));
            }
        });
    }
}