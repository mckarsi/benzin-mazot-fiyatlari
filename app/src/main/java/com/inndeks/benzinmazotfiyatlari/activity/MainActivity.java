package com.inndeks.benzinmazotfiyatlari.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.inndeks.benzinmazotfiyatlari.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import adapter.FuelListAdapter;
import fragment.FragmentListing;
import fragment.FragmentNoConnection;
import model.FuelModel;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            if(checkInternetAvailability(context)){
                getFragmentManager().beginTransaction().add(R.id.container, new FragmentListing()).commit();
                new LoadFuelPrices().execute((Void) null);
            }else{
                getFragmentManager().beginTransaction().add(R.id.container, new FragmentNoConnection()).commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean checkInternetAvailability(Context ctx){
        ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
    }

    private class LoadFuelPrices extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog = null;
        InputStream inputStream = null;
        String jsonData = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Lütfen Bekleyiniz");
            progressDialog.setMessage("Piyasa verilieri güncelleniyor...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Long callbackName = (long)(Math.random()*9999999);
                String url = "http://api.piyasa.com/jsonp/?callback=myCallBack" + callbackName.toString() + "&kaynak=akaryakit_guncel_Adıyaman";
                Log.d("url", url);
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                //httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                String line;
                while ((line = bReader.readLine()) != null) {
                    jsonData += line + "\n";
                }
                jsonData = jsonData.substring(jsonData.indexOf("(") + 1, jsonData.lastIndexOf(")")); // burada "jsonp to json" yaptım yani başındaki callback ifadesini sildim
                Log.d("jsonData", jsonData);
                inputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            //parse JSON data
            try {
                List<FuelModel> fuelList = new ArrayList<FuelModel>();
                FuelModel fuelModel;
                JSONArray jArray = new JSONArray(jsonData);
                for(int i=0; i < jArray.length(); i++) {
                    fuelModel = new FuelModel();
                    JSONObject jObject = jArray.getJSONObject(i);
                    fuelModel.setFuelName(jObject.getString("source") + ":" + jObject.getString("fuel"));
                    fuelModel.setFuelPrice(jObject.getString("price") + " TL");
                    fuelList.add(fuelModel);
                }
                ListView fuelListObject = (ListView) findViewById(R.id.fuel_list);
                FuelListAdapter listAdapter = new FuelListAdapter(MainActivity.this, R.layout.fuel_list_row, fuelList);
                fuelListObject.setAdapter(listAdapter);
                this.progressDialog.dismiss();
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
        }

    }

}