package com.uguryasar.weatherforecast.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.uguryasar.weatherforecast.R;
import com.uguryasar.weatherforecast.model.CityList;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.MyViewHolder> {
    private List<CityList> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView tv_plaka;
        TextView tv_name;
        TextView tv_temp;

        MyViewHolder(View v) {
            super(v);
            tv_plaka = v.findViewById(R.id.tv_itemCity_plaka);
            tv_name = v.findViewById(R.id.tv_itemCity_name);
            tv_temp = v.findViewById(R.id.tv_itemCity_temp);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    CityAdapter(List<CityList> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city_list, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tv_plaka.setText(mDataset.get(position).getPlaka().toString());
        holder.tv_name.setText(mDataset.get(position).getIl());
        holder.tv_temp.setText(mDataset.get(position).getTemp());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}