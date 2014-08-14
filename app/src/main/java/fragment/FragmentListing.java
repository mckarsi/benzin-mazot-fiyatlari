package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.inndeks.benzinmazotfiyatlari.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapter.FuelListAdapter;
import model.FuelModel;


/**
 * Created by mert.karsi on 14.08.2014.
 */
public class FragmentListing  extends Fragment{

    private List<FuelModel> fuelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_listing, container, false);

        fuelList = downloadFuelPrices();
        ListView fuelListObject = (ListView) layout.findViewById(R.id.fuel_list);
        FuelListAdapter listAdapter = new FuelListAdapter(getActivity(), R.layout.fuel_list_row, fuelList);
        fuelListObject.setAdapter(listAdapter);

        return layout;
    }

    private List<FuelModel> downloadFuelPrices(){
        fuelList = new ArrayList<FuelModel>();

        try {
            URL url = new URL("http://api.piyasa.com/jsonp/?callback=OLUSTURULACAK_CALLBACK_DEGISKENI_RANDOM&kaynak=akaryakit_guncel_Adıyaman");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            File SDCardRoot = Environment.getExternalStorageDirectory();
            File file = new File(SDCardRoot,"akaryakit.xml");
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0; //used to store a temporary size of the buffer
            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                //add the data in the buffer to the file in the file output stream (the file on the sd card
                fileOutput.write(buffer, 0, bufferLength);
                //add up the size so we know how much is downloaded
                downloadedSize += bufferLength;

            }
            fileOutput.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FuelModel fuelItem;

        for(int i = 0 ; i<5; i++){
            fuelItem =  new FuelModel();
            fuelItem.setFuelName("benzin");
            fuelItem.setFuelPrice("hgghh");
            fuelList.add(fuelItem);
        }

        return fuelList;
    }

}
