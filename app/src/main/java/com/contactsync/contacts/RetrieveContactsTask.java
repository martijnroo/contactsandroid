package com.contactsync.contacts;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Martijn on 15/11/2014.
 */
public class RetrieveContactsTask extends AsyncTask<String, Void, String> {

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @Override
    protected String doInBackground(String... urls) {
        String url = urls[0];
        try {
            InputStream responseStream = new URL(url).openStream();
            String response = convertStreamToString(responseStream);
            return response;
        } catch (MalformedURLException urle) {
            System.err.println(urle.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return "";
    }

    protected void onPostExecute(String result) {
        try {
            JSONArray contacts = new JSONArray(result);
            for (int i=0; i < contacts.length(); i++)
            {
                try {
                    JSONObject oneObject = contacts.getJSONObject(i);
                    // Pulling items from the array
                    String name = oneObject.getString("name");
                    String email = oneObject.getString("email");
                    String phone = oneObject.getString("phone");
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                }
            }

        } catch (JSONException e) {
            System.err.println(e.getMessage());
        }
    }
}
