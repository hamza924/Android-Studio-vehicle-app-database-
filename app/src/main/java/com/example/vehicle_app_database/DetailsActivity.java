package com.example.vehicle_app_database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



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

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // get the intent
        final Bundle extras = getIntent().getExtras();
        /*
         Get the intent from the previous activity from the key
         called 'vehicle'
         create a new object using this information called'v'
         */
        final vehicle V = (vehicle) extras.get("vehicle");
        // update and delete button to allow user to delete and update
        Button update = findViewById(R.id.update);
        Button delete = findViewById(R.id.delete);

        /*
          Setting all the textviews that will be display all the information
          about the vehicles on the details activity
         */
        final TextView idText = findViewById(R.id.vehicle_id);
        final TextView idTEXTIN = findViewById(R.id.vehicleTEXT);
        final TextView makeText = findViewById(R.id.Make);
        final TextView makeTEXTIN = findViewById(R.id.makeTEXT);
        final TextView modelText = findViewById(R.id.Model);
        final TextView modelTEXTIN = findViewById(R.id.modelTEXT);
        final TextView yearText = findViewById(R.id.year);
        final TextView yearTEXTIN = findViewById(R.id.yearTEXT);
        final TextView priceText = findViewById(R.id.price);
        final TextView priceTEXTIN = findViewById(R.id.priceTEXT);

        final TextView licenseNumberText = findViewById(R.id.License);
        final TextView licenseTEXTIN = findViewById(R.id.LicenseTEXT);
        final TextView colourText = findViewById(R.id.Colour);
        final TextView colourTEXTIN = findViewById(R.id.ColourTEXT);
        final TextView numberDoorsText = findViewById(R.id.Door);
        final TextView numberDoorsTEXTIN = findViewById(R.id.doorTEXT);
        final TextView transmissionText = findViewById(R.id.Transmission);
        final TextView transmissionTEXTIN = findViewById(R.id.transTEXT);
        final TextView mileageText = findViewById(R.id.Mileage);
        final TextView mileageTEXTIN = findViewById(R.id.mileageTEXT);

        final TextView fuelTypeText = findViewById(R.id.Fuel);
        final TextView fuelTEXTIN = findViewById(R.id.fuelTEXT);
        final TextView engineSizeText = findViewById(R.id.Engine);
        final TextView engineTEXTIN = findViewById(R.id.engineTEXT);
        final TextView bodyStyleText = findViewById(R.id.Body);
        final TextView bodyTEXTIN = findViewById(R.id.bodyTEXT);
        final TextView conditionText = findViewById(R.id.Condition);
        final TextView conditionTEXTIN = findViewById(R.id.conditionTEXT);
        final TextView notesText = findViewById(R.id.Notes);
        final TextView notesTEXTIN = findViewById(R.id.notesTEXT);

        /*
          Displaying all the information in the textviews
          so that user will be either able to update or delete if
          the click on any button
         */
        idText.setText(String.valueOf(V.getVehicle_id()));
        idTEXTIN.setText("ID:");
        makeText.setText(V.getMake());
        makeTEXTIN.setText("MAKE:");
        modelText.setText(V.getModel());
        modelTEXTIN.setText("MODEL:");
        yearText.setText(String.valueOf(V.getYear()));
        yearTEXTIN.setText("YEAR:");
        priceText.setText(String.valueOf(V.getPrice()));
        priceTEXTIN.setText("PRICE:");
        licenseNumberText.setText(V.getLicense_number());
        licenseTEXTIN.setText("LICENSE:");
        colourText.setText(V.getColour());
        colourTEXTIN.setText("COLOUR:");
        numberDoorsText.setText(String.valueOf(V.getNumber_doors()));
        numberDoorsTEXTIN.setText("Doors:");
        transmissionText.setText(V.getTransmission());
        transmissionTEXTIN.setText("GEAR-BOX");
        mileageText.setText(String.valueOf(V.getMileage()));
        mileageTEXTIN.setText("MILEAGE:");

        fuelTypeText.setText(V.getFuel_type());
        fuelTEXTIN.setText("FUEL TYPE:");
        engineSizeText.setText(String.valueOf(V.getEngine_size()));
        engineTEXTIN.setText("ENGINE(CC):");
        bodyStyleText.setText(V.getBody_style());
        bodyTEXTIN.setText("BODY-TYPE:");
        conditionText.setText(V.getCondition());
        conditionTEXTIN.setText("CONDITION:");
        notesText.setText(V.getNotes());
        notesTEXTIN.setText("NOTES:");

        // update button which will send the user to a new intent to update vehicle
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), uodate.class);
                intent.putExtra("vehicle", V);
                startActivity(intent);
                /*
                 Creating a new intent and again passing key'vehicle' along with the
                 vehicle object called'V' to the update class
                 */
            }

        });

        // creating a hashmap called values to delete a vehicle
        final HashMap<String, String> values = new HashMap<>();

        /*
          Delete button allows user to delete
          the current vehicle
         */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                 local variable getting
                 the text from the textviews and turning to string to delete
                 */
                int vehicle_id = Integer.valueOf(idText.getText().toString());
                String make = makeText.getText().toString();
                String model = modelText.getText().toString();
                int year = Integer.valueOf(yearText.getText().toString());
                int price = Integer.valueOf(priceText.getText().toString());
                String license_number =licenseNumberText.getText().toString();
                String colour = colourText.getText().toString();
                int number_doors = Integer.valueOf(numberDoorsText.getText().toString());
                String transmission = transmissionText.getText().toString();
                int mileage = Integer.valueOf(mileageText.getText().toString());
                String fuel_type = fuelTypeText.getText().toString();
                int engine_size = Integer.valueOf(engineSizeText.getText().toString());
                String body_style = bodyStyleText.getText().toString();
                String condition = conditionText.getText().toString();
                String notes = notesText.getText().toString();

                values.put("vehicle_id", String.valueOf(vehicle_id));
                values.put("make", make);
                values.put("model", model);
                values.put("year", String.valueOf(year));
                values.put("price", String.valueOf(price));
                values.put("license_number", license_number);
                values.put("colour", colour);
                values.put("number_doors", String.valueOf(number_doors));
                values.put("transmission", transmission);
                values.put("mileage", String.valueOf(mileage));
                values.put("fuel_type", fuel_type);
                values.put("engine_size", String.valueOf(engine_size));
                values.put("body_style", body_style);
                values.put("condition", condition);
                values.put("notes", notes);

                /*
                Putting all the variable into hashmap so delete can
                happen
                 */
                // url we connect to
                String url = "http://10.0.2.2:8000/delete_vehicle";
                performDeleteCall(url, values);
                // calling delete method

            }
        });

    }

    public String performDeleteCall(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            //create the conn object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod("DELETE");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            /*
             Setting the connection object to delete
             and setting timeout to 15 seconds
             */

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            // Send data to server url encoded

            writer.write(getDeleteDataString(postDataParams));

            //clear the writer
            writer.flush();
            writer.close();
            //close the stream
            os.close();

            //get the server response code see whether success or error

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode" + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                Toast.makeText(this, "Vehicle DELETED Sucessfully ", Toast.LENGTH_LONG).show();
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                Toast.makeText(this, "Error failed to delete vehicle  ", Toast.LENGTH_LONG).show();
                response = "";

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("response = "+response  );
        return response;
    }
    // delete method
    private String getDeleteDataString(HashMap<String, String> values) throws UnsupportedEncodingException {

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
