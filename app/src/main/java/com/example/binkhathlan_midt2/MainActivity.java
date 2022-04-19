package com.example.binkhathlan_midt2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView datePicked;
    String weatherWebserviceURL;
    TextView temperature, description ,humidity;
    JSONObject jsonObj;

    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goTo2 = (Button) findViewById(R.id.goFrom1To2);
        goTo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });

        TextView datePicked = (TextView) findViewById(R.id.datePicked);
        Button btnDate = (Button) findViewById(R.id.btnDate);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, d,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        temperature = (TextView) findViewById(R.id.temperature);
        description = (TextView) findViewById(R.id.description);
        humidity = (TextView) findViewById(R.id.humidity);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String city = parentView.getItemAtPosition(position).toString();
                Log.d("Naif",city);
                weatherWebserviceURL =
                        "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=8de97d4e16d0d57d860cdb3a0b7a93d3&units=metric";
                weather(weatherWebserviceURL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }
        Calendar c = Calendar.getInstance();
        DateFormat fmtDate = DateFormat.getDateInstance();

        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                datePicked.setText("Your Date is " + fmtDate.format(c.getTime()));
            }
        };

    public void weather(String url) {
        JsonObjectRequest jsonObj =
                new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Naif","Response received");
                                Log.d("Naif",response.toString());
                                try {
                                    String town=response.getString("name");
                                    Log.d("Naif-town",town);
                                    // nested object
                                    JSONObject jsonMain = response.getJSONObject("main");
                                    //
                                    double temp = jsonMain.getDouble("temp");
                                    DecimalFormat df = new DecimalFormat("##");
                                    Log.d("Naif-temp",String.valueOf(df.format(temp)+" °C"));
                                    temperature.setText(String.valueOf(df.format(temp)+" °C"));

                                    String humid = jsonMain.getString("humidity");
                                    Log.d("Naif-humidity",String.valueOf(humid));
                                    humidity.setText("Humidity: "+String.valueOf(humid)+"%");
                                    //String townResponse = jsonMain.getString("name");
                                    description.setText(town);
                                } catch (JSONException e){
                                    e.printStackTrace();
                                    Log.d("Receive Error",e.toString());
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Naif", "Error retrieving URL"+error.toString());
                        error.toString();
                    }
                }
                );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);

    }

}