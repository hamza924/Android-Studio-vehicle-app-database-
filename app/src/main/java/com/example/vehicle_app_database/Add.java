package com.example.vehicle_app_database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        // button called save to allow the user to save vehicle
        Button save = findViewById(R.id.save);
        /*
          All the edit texts and their variable names that will allow
          the user to to create a new vehicle and save it to the
          database
         */
        final EditText Id = findViewById(R.id.Edit_id);
        final EditText Make = findViewById(R.id.Edit_make);
        final EditText Model = findViewById(R.id.Edit_model);
        final EditText Year = findViewById(R.id.Edit_year);
        final EditText Price = findViewById(R.id.Edit_price);
        final EditText licenseNumber = findViewById(R.id.Edit_license);
        final EditText Colour = findViewById(R.id.Edit_colour);
        final EditText NumberOfDoors = findViewById(R.id.Edit_door);
        final EditText Transmission = findViewById(R.id.Edit_transmission);
        final EditText Mileage = findViewById(R.id.Edit_mileage);
        final EditText FuelType = findViewById(R.id.Edit_fuel);
        final EditText EngineSize = findViewById(R.id.Edit_engine);
        final EditText BodyStyle = findViewById(R.id.Edit_Body);
        final EditText Condition = findViewById(R.id.EdIt_condition);
        final EditText Notes = findViewById(R.id.Edit_note);

        // hash called values which will be used to add a new vehicle
        final HashMap<String, String> values = new HashMap<>();
        // when the user clicks on save the following code will execute
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // using GSON to turn string to json object
                Gson gson = new Gson();
                /*
                  Creating new local variables
                  to receive all the information inputted by the user
                  turn the text into a string
                 */
                int vehicle_id =Integer.valueOf(Id.getText().toString());
                String make = Make.getText().toString();
                String model = Model.getText().toString();
                int year = Integer.valueOf(Year.getText().toString());
                int price = Integer.valueOf(Price.getText().toString());
                String license_number = licenseNumber.getText().toString();
                String colour = Colour.getText().toString();
                int number_doors = Integer.valueOf(NumberOfDoors.getText().toString());
                String transmission = Transmission.getText().toString();
                int mileage = Integer.valueOf(Mileage.getText().toString());
                String fuel_type = FuelType.getText().toString();
                int engine_size = Integer.valueOf(EngineSize.getText().toString());
                String body_style = BodyStyle.getText().toString();
                String condition = Condition.getText().toString();
                String notes = Notes.getText().toString();

                // creating a new vehicle object and passing all the variables
                vehicle Vehicle = new vehicle(vehicle_id, make, model, year, price, license_number, colour, number_doors, transmission, mileage, fuel_type, engine_size, body_style, condition, notes);

                /*
                  Creating new string json and passing
                  the vehicle object to turn the object into a
                  json object
                 */

                String Json = gson.toJson(Vehicle);
                values.put("json",Json);
                String url = "http://10.0.2.2:8000/add_vehicle";
                /*
                  Setting the url where all the data will be sent to
                  and putton the json string
                  to perform the postcall with the url and rhe values

                 */
                performPostCall(url, values);
            }
        });

    }

    // method that will send the new vehicle to the server
    public String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        // new url object
        String response = "";
        // response set to empty
        try {
            url = new URL(requestURL);
            //create the conn object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            /*
              Setting the request method to post
              as we are adding a new car
              and setting the timeout to 15 seconds
             */

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            //post data to server url encoded

            writer.write(getPostDataString(postDataParams));

            //clear the writer
            writer.flush();
            writer.close();
            //close the stream
            os.close();

            //get the server response code to see weather it has been succes or error
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode" + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                Toast.makeText(this, "Vehicle inserted Sucessfully", Toast.LENGTH_LONG).show();
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                Toast.makeText(this, "Error failed to insert vehicle", Toast.LENGTH_LONG).show();
                response = "";

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("response = "+response  );
        return response;
    }

    // get post data method
    private String getPostDataString(HashMap<String, String> values) throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String,String> entry: values.entrySet())
        {
            if(first)
                first=false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));

        }
        return result.toString();
    }
}
