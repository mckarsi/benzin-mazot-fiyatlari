package fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.inndeks.benzinmazotfiyatlari.R;
import com.inndeks.benzinmazotfiyatlari.activity.MainActivity;

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

import adapter.FuelListAdapter;
import model.FuelModel;
import util.DatabaseHelper;

/**
 * Created by mert.karsi on 14.08.2014.
 */
public class FragmentListing  extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_listing, container, false);

        new LoadFuelPrices().execute();

        return layout;
    }

    public class LoadFuelPrices extends AsyncTask<Void, Void, Void> {

        private DatabaseHelper dbHelper = new DatabaseHelper(getActivity());

        ProgressDialog progressDialog = null;
        InputStream inputStream = null;
        String jsonData = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
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
                    fuelModel.setCity("Ankara");
                    fuelList.add(fuelModel);
                    //dbHelper.updateFuelPrices(fuelModel);
                }
                ListView fuelListObject = (ListView) getActivity().findViewById(R.id.fuel_list);
                FuelListAdapter listAdapter = new FuelListAdapter(getActivity(), R.layout.fuel_list_row, fuelList);
                fuelListObject.setAdapter(listAdapter);
                this.progressDialog.dismiss();
                //dbHelper.deleteAllRows();
                List<FuelModel> dbFuelList = dbHelper.getAllRows();
                FuelModel dbFuelModel = null;
                for(int i=0; i<dbFuelList.size(); i++){
                    dbFuelModel = dbFuelList.get(i);
                    Log.d("dbKayitVar", dbFuelModel.toString());
                }
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
        }

    }


}
