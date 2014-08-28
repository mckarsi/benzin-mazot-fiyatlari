package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inndeks.benzinmazotfiyatlari.R;

import java.util.List;

import model.FuelModel;

/**
 * Created by mertcan on 13.08.2014.
 */
public class FuelListAdapter extends ArrayAdapter<FuelModel> {

    public FuelListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public FuelListAdapter(Context context, int resource, List<FuelModel> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.fuel_list_row, null);
        }
        FuelModel item = getItem(position);

        if (item != null) {
            ImageView ivRowCompanyLogo = (ImageView) v.findViewById(R.id.company_logo);
            TextView tvRowTitle = (TextView) v.findViewById(R.id.row_title);
            TextView tvRowPrice = (TextView) v.findViewById(R.id.row_price);
            if(ivRowCompanyLogo != null){
                if(item.getCompany().equals("opet"))
                    ivRowCompanyLogo.setImageResource(R.drawable.opet_logo);
                if(item.getCompany().equals("poas"))
                    ivRowCompanyLogo.setImageResource(R.drawable.petrol_ofisi_logo);
            }
            if (tvRowTitle != null) {
                tvRowTitle.setText(item.getFuelName());
            }
            if (tvRowPrice != null) {
                tvRowPrice.setText(item.getFuelPrice());
            }
        }

        return v;
    }


}