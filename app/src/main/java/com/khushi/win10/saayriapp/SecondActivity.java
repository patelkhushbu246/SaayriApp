package com.khushi.win10.saayriapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

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
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView listView;
    Quotes q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);


           int gotid = getIntent().getIntExtra("pos", 1);

            new MySecondClass().execute("http://rapidans.esy.es/test/getquotes.php?cat_id="+gotid);



    }

    class MySecondClass extends AsyncTask<String,Void,String> {

        private ProgressDialog dialog;
        ArrayList<Quotes> quotesArrayList = new ArrayList<>();
        CustomAdapterList adapterL;

        Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SecondActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection;
            try {
                URL url = new URL(params[0]);
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    String bufferString = buffer.toString();
                    return bufferString;


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            quotesArrayList = new ArrayList<>();

            try {

                JSONObject jsonObject1 = new JSONObject(s);

                JSONArray jsonArray = jsonObject1.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                    q = new Quotes();
                    q.setId(jsonObject.getInt("id"));
                    q.setCat_id(jsonObject.getInt("cat_id"));
                    q.setQuotes(jsonObject.getString("quotes"));

                    quotesArrayList.add(q);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapterL = new CustomAdapterList(SecondActivity.this,quotesArrayList);

            listView = (ListView) findViewById(R.id.listview1);

            listView.setAdapter(adapterL);

            String passedVar=null;



        }
    }

}
