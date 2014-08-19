package com.inndeks.benzinmazotfiyatlari.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.inndeks.benzinmazotfiyatlari.R;

import fragment.FragmentListing;
import fragment.FragmentNoConnection;
import fragment.FragmentSelectCity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            if(checkInternetAvailability(context)){
                getFragmentManager().beginTransaction().add(R.id.container, new FragmentListing()).commit();
            }else{
                getFragmentManager().beginTransaction().add(R.id.container, new FragmentNoConnection()).commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_select_city){
            Fragment fragmentSelectCity = new FragmentSelectCity();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragmentSelectCity);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }else if (id == R.id.menu_about) {
            Toast.makeText(MainActivity.this, "hakkımızda şöyle böyle", Toast.LENGTH_LONG).show();
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







}