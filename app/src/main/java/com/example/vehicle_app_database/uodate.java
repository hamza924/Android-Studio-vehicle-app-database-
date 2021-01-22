package com.example.vehicle_app_database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class uodate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uodate);

        // getting intent from previous pages
        final Bundle extras = getIntent().getExtras();

        /*
          new vehicle object V like details activity
          getting information from details page using the key'vehicle'
         */
        final vehicle V = (vehicle) extras.get("vehicle");

        // update button to update vehicle
        Button update = findViewById(R.id.updatecar);

        /*
         Edit texts to allow the user to edit and update
         any of the details about a car
         */
        final EditText updateID = findViewById(R.id.update_id);
        final EditText updateMake = findViewById(R.id.update_make);
        final EditText updateModel = findViewById(R.id.update_model);
        final EditText updateYear = findViewById(R.id.update_year);
        final EditText updatePrice = findViewById(R.id.update_price);
        final EditText updateLicenseNumber = findViewById(R.id.update_license);
        final EditText updateColour = findViewById(R.id.update_colour);
        final EditText updateNumberDoors = findViewById(R.id.update_doors);
        final EditText updateTransmission = findViewById(R.id.update_transmission);
        final EditText updateMileage = findViewById(R.id.update_mileage);
        final EditText updateFuelType = findViewById(R.id.update_fuel);
        final EditText updateEngineSize = findViewById(R.id.update_engine);
        final EditText updateBodyStyle = findViewById(R.id.update_body);
        final EditText updateCondition = findViewById(R.id.update_condition);
        final EditText updateNotes = findViewById(R.id.update_notes);

        /*
         Text views to show which edit text field is where
         */
        final TextView uid = findViewById(R.id.updateTEXTID1);
        final TextView maketxt = findViewById(R.id.updateTEXTMAKE2);
        final TextView modeltxt = findViewById(R.id.updateTEXTMODEL);
        final TextView yeartxt = findViewById(R.id.updateTEXTYEAR);
        final TextView pricetxt = findViewById(R.id.updateTEXTPRICE);
        final TextView licensetxt = findViewById(R.id.updateTEXTLICENSE);
        final TextView colourtxt = findViewById(R.id.updateTEXTCOLOUR);
        final TextView doorttxt = findViewById(R.id.updateTEXTDOORS);
        final TextView mileagetxt = findViewById(R.id.updateTEXTMILEAGE);
        final TextView transtxt = findViewById(R.id.updateTEXTTRA);

        final TextView fueltxt = findViewById(R.id.updateTEXTFUEL);
        final TextView EngineSizetxt = findViewById(R.id.updateTEXTENG);
        final TextView bodytxt = findViewById(R.id.updateTEXTBODY);
        final TextView condtxt = findViewById(R.id.updateTEXTCOND);
        final TextView notestxt = findViewById(R.id.updateTEXTNOTE);
        /*
         Setting the edit texts to be filled with information from
         the details page so the user can quickly edit any information
         */
        updateID.setText(String.valueOf(V.getVehicle_id()));
        updateMake.setText(V.getMake());
        updateModel.setText(V.getModel());
        updateYear.setText(String.valueOf(V.getYear()));
        updatePrice.setText(String.valueOf(V.getPrice()));
        updateLicenseNumber.setText(V.getLicense_number());
        updateColour.setText(V.getColour());
        updateNumberDoors.setText(String.valueOf(V.getNumber_doors()));
        updateTransmission.setText(V.getTransmission());
        updateMileage.setText(String.valueOf(V.getMileage()));
        updateFuelType.setText(V.getFuel_type());
        updateEngineSize.setText(String.valueOf(V.getEngine_size()));
        updateBodyStyle.setText(V.getBody_style());
        updateCondition.setText(V.getCondition());
        updateNotes.setText(V.getNotes());
        /*
         Setting the textviews to display meaningful information beside
         the edit texts fields
         */
        uid.setText("ID:");
        modeltxt.setText("Make:");
        maketxt.setText("Model:");
        yeartxt.setText("Year:");
        pricetxt.setText("Change Price:");
        licensetxt.setText("License:");
        colourtxt.setText("Colour:");
        doorttxt.setText("Doors:");
        mileagetxt.setText("Mileage:");
        transtxt.setText("Transmission:");
        fueltxt.setText("Fuel Type:");
        EngineSizetxt.setText("Engine Size:");
        bodytxt.setText("Body Type:");
        condtxt.setText("Condition:");
        notestxt.setText("Notes:");

        //hash map called values to update vehicle
        final HashMap<String, String> values = new HashMap<>();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // using gson to get json objects
                Gson gson = new Gson();
                int vehicle_id = Integer.valueOf(updateID.getText().toString());
                String make = updateMake.getText().toString();
                String model = updateModel.getText().toString();
                int year = Integer.valueOf(updateYear.getText().toString());
                int price = Integer.valueOf(updatePrice.getText().toString());
                String license_number = updateLicenseNumber.getText().toString();
                String colour = updateColour.getText().toString();
                int number_doors = Integer.valueOf(updateNumberDoors.getText().toString());
                String transmission = updateTransmission.getText().toString();
                int mileage = Integer.valueOf(updateMileage.getText().toString());
                String fuel_type = updateFuelType.getText().toString();
                int engine_size = Integer.valueOf(updateEngineSize.getText().toString());
                String body_style = updateBodyStyle.getText().toString();
                String condition = updateCondition.getText().toString();
                String notes = updateNotes.getText().toString();

                // creating vehicle object and passing over the variables
                vehicle vehicle = new vehicle(vehicle_id, make, model, year, price, license_number, colour, number_doors, transmission, mileage, fuel_type, engine_size, body_style, condition, notes);

                String Json = gson.toJson(vehicle);
                // sending json object to the server
                values.put("json",Json);
                // the url we are connecting to to update
                String url = "http://10.0.2.2:8000/update_vehicle";
                performPutCall(url, values);
                // performing put call and sending values hashmap to the url


            }
        });


    }
    // putcall method
    public String performPutCall(String requestURL, HashMap<String, String> putDataParams) {

        URL url;
        // url object
        String response = "";
        try {
            url = new URL(requestURL);
            //create the conn object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // setting the connection object with request method to put for update

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            //sendind data to the server url encodeded

            writer.write(getPutDataString(putDataParams));

            //clear the writer
            writer.flush();
            writer.close();
            //close the stream
            os.close();

            //get the server response code to see whether success or error

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode" + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // if code is 200 show ok message if not error message
                Toast.makeText(this, "Vehicle UPDATED Sucessfully ", Toast.LENGTH_LONG).show();
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                Toast.makeText(this, "Error- failed to Update vehicle  ", Toast.LENGTH_LONG).show();
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("response = " + response);
        return response;


    }

    // get putdata method
    private String getPutDataString(HashMap<String, String> values) throws UnsupportedEncodingException {

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
