package com.tracker;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class fetchDataFirst extends AsyncTask<Void, Void, Void> {
    String data = "";
    String firstAnimalLat = "13.31461";
    String firstAnimalLon = "77.12327";
    String firstAnimalTem = "0";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://majortest.data.thethingsnetwork.org/api/v2/query?last=1d");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(false);

            httpURLConnection.setRequestMethod("GET");

//            String userpass = "user" + ":" + "pass";
            String basicAuth = "key " + "ttn-account-v2.Fw7tyY9RkRY9G56WbBA6-JxSJolcFjICLOCOCP3S7wo";
            httpURLConnection.setRequestProperty("Authorization", basicAuth);

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            try {
                JSONArray jsonArray = new JSONArray(data);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject tempObj = jsonArray.getJSONObject(i);
                    String device = tempObj.getString("device_id");
                    Log.i("OX_HORUS1", "for");
                    if(device.equals("device1")){       // onDevice1
                        Log.i("OX_HORUS1", "first if");
                        if(!tempObj.getString("raw").substring(0, 2).equals("AA")) {     // ifPayload
                            Log.i("OX_HORUS1", "second if");
                            firstAnimalLat = jsonArray.getJSONObject(i).getString("myTestValue").split(",")[1];
                            firstAnimalLon = jsonArray.getJSONObject(i).getString("myTestValue").split(",")[0];
                            firstAnimalTem = jsonArray.getJSONObject(i).getString("myTestValue").split(",")[2];
                            firstAnimalTem = firstAnimalTem.substring(0, firstAnimalTem.indexOf(".") + 2);
                        }
                    }
                 }

//                firstAnimalTem = jsonArray.getJSONObject(0).getString("tem");
                Log.i("OX_HORUS1", "ran");
                Log.i("OX_HORUS1", firstAnimalLat + " | " + firstAnimalLon + " | " + firstAnimalTem);

            } catch (Throwable t) {
                Log.i("OX_HORUS", "Could not parse malformed JSON: '" + data + "'");
            }
            Log.i("OX_HORUS", data);

        } catch (MalformedURLException e) {
            Log.i("OX_HORUS", String.valueOf(e));
        } catch (IOException e) {
            Log.i("OX_HORUS", String.valueOf(e));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        // arr[1] is another temperature sensor on the battery
        firstAnimal.latitude = Double.parseDouble(firstAnimalLat);
        firstAnimal.longitude = Double.parseDouble(firstAnimalLon);
        firstAnimal.firstAnimalTemp_TV.setText(firstAnimalTem + "°C");
    }
}
