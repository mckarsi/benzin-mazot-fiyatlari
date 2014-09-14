package fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.inndeks.benzinmazotfiyatlari.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentSelectCity extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_select_city, container, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Resources res = getResources();
        String[] cities = res.getStringArray(R.array.cities_array);
        List<String> citiesList = new ArrayList<String>(Arrays.asList(cities));

        final Spinner citiesSpinner = (Spinner) layout.findViewById(R.id.spinner_select_city);
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, citiesList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citiesSpinner.setAdapter(spinnerAdapter);
        citiesSpinner.setSelection(preferences.getInt("selectedCityIndex", 33)); // default istanbul index:33 sıra:34

        final Button selectCityButton = (Button) layout.findViewById(R.id.button_select_city);
        selectCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedCity = citiesSpinner.getSelectedItem().toString();
                int selectedCityIndex = citiesSpinner.getSelectedItemPosition();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("selectedCity", selectedCity);
                editor.putInt("selectedCityIndex", selectedCityIndex);
                editor.commit();

                FragmentManager fm = getActivity().getFragmentManager();
                fm.popBackStack();
            }
        });

        return layout;
    }

}
