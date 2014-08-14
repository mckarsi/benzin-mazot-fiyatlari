package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.inndeks.benzinmazotfiyatlari.R;
import com.inndeks.benzinmazotfiyatlari.activity.MainActivity;

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

        FuelModel fuelItem;
        // burası şimdilik böyle
        for(int i = 0 ; i<5; i++){
            fuelItem =  new FuelModel();
            fuelItem.setFuelName("benzin");
            fuelItem.setFuelPrice("hgghh");
            fuelList.add(fuelItem);
        }

        return fuelList;
    }

}
