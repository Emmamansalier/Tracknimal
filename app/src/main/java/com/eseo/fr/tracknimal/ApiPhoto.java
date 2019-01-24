package com.eseo.fr.tracknimal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiPhoto extends AsyncTask<Void, Void, Void> {

    String data = "";
    String encodedImage = "";
    Bitmap image = null;
    String idIndPhoto;

    public ApiPhoto(String idIndPhoto) {
        this.idIndPhoto = idIndPhoto;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            URL url = new URL("http://192.168.137.1/traknimalweb/photowebservice.php?idPoisson=" + idIndPhoto);
            HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            if (data.length()>29) {
                String splitData = data.substring(29);
                encodedImage = splitData.substring(0, splitData.length() - 6);
                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
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
        MainActivity.getPhotoData(this.data, this.image);

    }
}