package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inndeks.benzinmazotfiyatlari.R;

import java.util.List;

import model.FuelModel;

/**
 * Created by mertcan on 13.08.2014.
 */
public class FuelListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<FuelModel> fuelList;

    public FuelListAdapter(Context context, List<FuelModel> fuelList) {
        super(context, R.layout.fuel_list_row, fuelList.size());
        this.context = context;
        this.fuelList = fuelList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.fuel_list_row, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.row_title);
        TextView cat = (TextView) convertView.findViewById(R.id.row_price);

        final FuelModel item = fuelList.get(position);

        title.setText(item.getFuelName().toString());
        cat.setText(item.getFuelPrice().toString());

        return convertView;
    }


}