package com.eseo.fr.tracknimal;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiParam extends AsyncTask<Void, Void, Void> {

    String data = "";
    String idIndParam;

    public ApiParam(String idIndParam) {
        this.idIndParam = idIndParam;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            URL url = new URL("http://192.168.137.1/traknimalweb/paramwebservice.php?idPoisson=" + idIndParam);
            HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        MainActivity.getParamData(this.data);

    }
}