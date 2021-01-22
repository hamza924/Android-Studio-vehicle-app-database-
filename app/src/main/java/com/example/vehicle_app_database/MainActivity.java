package com.example.vehicle_app_database;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //run network on main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        /*
         Button for adding a new car
         list view variable
         String array called names to add all cars to
         the array to display on main activity
         */
        Button addVehicle = findViewById(R.id.AddNew);
        final ListView ListView1 = findViewById(R.id.listview);
        String[] Names;


        /*
        final arraylist called vehicle list to add all information from
        the json to the listview
        */
        final ArrayList<vehicle> vehicleList = new ArrayList<>();

        //Making a http call
        HttpURLConnection urlConnection;
        InputStream in = null;
        try {
            // the url we wish to connect to which localhost 8000/api
            URL url = new URL("http://10.0.2.2:8000/api");
            // open the connection to the specified URL
            urlConnection = (HttpURLConnection) url.openConnection();
            // get the response from the server in an input stream
            in = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // covert the input stream to a string
        String response = convertStreamToString(in);
        // print the response to android monitor/log cat
        System.out.println("Server response = " + response);
        try {
            // declare a new json array and pass it the string response from the
            //server
            // this will convert the string into a JSON array which we can then
            //iterate
            // over using a loop
            JSONArray jsonArray = new JSONArray(response);
            // instantiate the Names array and set the size
            // to the amount of vehicle object returned by the server
            Names = new String[jsonArray.length()];

            // use a for loop to iterate over the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                /*
                  The following code within the for loop will get all the json objects and store
                  them in their own named variables so we can later add them to the vehicle array
                  list
                 */
                int vehicle_id = Integer.parseInt(jsonArray.getJSONObject(i).get("vehicle_id").toString());
                String make = jsonArray.getJSONObject(i).get("make").toString();
                String model = jsonArray.getJSONObject(i).get("model").toString();
                int year = Integer.parseInt(jsonArray.getJSONObject(i).get("year").toString());
                int price = Integer.parseInt(jsonArray.getJSONObject(i).get("price").toString());
                String license_number = jsonArray.getJSONObject(i).get("license_number").toString();
                String colour = jsonArray.getJSONObject(i).get("colour").toString();
                int number_doors = Integer.parseInt(jsonArray.getJSONObject(i).get("number_doors").toString());
                String transmission = jsonArray.getJSONObject(i).get("transmission").toString();
                int mileage = Integer.parseInt(jsonArray.getJSONObject(i).get("mileage").toString());
                String fuel_type = jsonArray.getJSONObject(i).get("fuel_type").toString();
                int engine_size = Integer.parseInt(jsonArray.getJSONObject(i).get("engine_size").toString());
                String body_style = jsonArray.getJSONObject(i).get("body_style").toString();
                String condition = jsonArray.getJSONObject(i).get("condition").toString();
                String notes = jsonArray.getJSONObject(i).get("notes").toString();


                // print the name to log cat
                System.out.println("make of the vehicle is  = " + make);
                // add the name of the current vehicle to the Names array

                Names[i] = make + " "+ model + " " + year + "\n" + license_number+"\n";
                // Displaying only make model, year and license on main activity

                // creating a new vehicle object from the vehicle class
                vehicle vehicle = new vehicle(vehicle_id, make, model, year, price, license_number, colour, number_doors, transmission, mileage, fuel_type, engine_size, body_style, condition, notes);
                vehicleList.add(vehicle);
                // adding the vehicle object to the arraylist

                // setting adapter to display all vehicles in listview
                ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Names);
                ListView1.setAdapter(arrayAdapter);
                }
            } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
          On the click of any item the user will see a short message
          to show which item they have clicked on
          and will be directed to a new page that will show all the information
          about the specific vehicle
         */
        ListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Details of  " +
                        vehicleList.get(i).getMake(),Toast.LENGTH_SHORT).show();
                /*
                 declare a new intent and give it the context
                 and where the next activity show take the user
                 in this case it is the details activity
                 */

                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("vehicle",vehicleList.get(i));
                /*
                  Createing a key called 'vehicle' and passing over the vehicle
                  object from the arraylist by getting the index of the specific
                  vehicle using the variable 'i'
                 */

                            startActivity(intent);
                            // start the new activity and pass over intent object
                        }
                    });

        /*
          When user clicks on add button they will
          be taken to new activity called add which will allow them to
          add a new vehicle
         */
        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Add.class);
                // start add activity
                startActivity(intent);
            }

        });


    }

    // convert json data to string
    public String convertStreamToString (InputStream is){

        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";

    }
}
