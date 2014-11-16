package com.contactsync.contacts;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ListContacts extends ListActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_contacts);
//
////        ListView lv = (ListView) findViewById(R.id.listView);
////
////        String[] from = new String[] {"name"};
////        int[] to = new int[] { R.id.item1 };
////
////        // prepare the list of all records
////        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
////        for(int i = 0; i < 10; i++){
////            HashMap<String, String> map = new HashMap<String, String>();
////            map.put("name", "" + i);
////            fillMaps.add(map);
////        }
////
////        // fill in the grid_item layout
////        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.grid_item, from, to);
////        lv.setAdapter(adapter);
//
//        ArrayAdapter<String> contactsAdapter = new ArrayAdapter<String>(this, R.layout.grid_item, R.id.textView);
//        contactsAdapter.add("Test");
//        contactsAdapter.add("Contact2");
//        contactsAdapter.notifyDataSetChanged();
//        System.out.println(contactsAdapter.getCount());
//
//        String url = "http://cloudguest114.niksula.hut.fi:8080/contacts";
//        new RetrieveContactsTask().execute(url);
//    }
    ArrayAdapter<String> arrayAdapter;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);

        ArrayList<String> items = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                items);
        setListAdapter(arrayAdapter);

        String url = "http://cloudguest114.niksula.hut.fi:8080/contacts";
        new RetrieveContactsTask().execute(url);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        System.out.println(arrayAdapter.getItem(position));
    }

    private class RetrieveContactsTask extends AsyncTask<String, Void, String> {

        String convertStreamToString(java.io.InputStream is) {
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
                        arrayAdapter.add(name);
                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                    }
                }

            } catch (JSONException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
