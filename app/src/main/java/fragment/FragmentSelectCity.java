package fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.inndeks.benzinmazotfiyatlari.R;


/**
 * Created by mert.karsi on 14.08.2014.
 */
public class FragmentSelectCity extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_select_city, container, false);

        final Button selectCityButton = (Button) layout.findViewById(R.id.button_select_city);
        selectCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                fm.popBackStack();
            }
        });

        return layout;
    }

}
