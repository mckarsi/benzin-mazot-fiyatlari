package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.inndeks.benzinmazotfiyatlari.R;


/**
 * Created by mert.karsi on 14.08.2014.
 */
public class FragmentListing  extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_listing, container, false);

        return layout;
    }

}
