package com.eseo.fr.tracknimal;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;

public class ApiWiki extends AsyncTask<Void, Void, Void> {

    String data = "";
    String idIndWiki;

    public ApiWiki(String idIndWiki) {
        this.idIndWiki = idIndWiki;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            URL url = new URL("http://192.168.137.1/traknimalweb/wikiwebservice.php?idPoisson=" + idIndWiki);
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
        MainActivity.getWikiData(this.data);

    }
}