package deraco.assignment.cities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import deraco.assignment.cities.model.City;

/**
 * Created by giuseppederaco on 16/03/2018.
 */

public class CityRecyclerViewAdapter extends RecyclerView.Adapter<CityRecyclerViewAdapter.ViewHolder> {
    void setCities(City[] cities) {
        this.cities = cities;
    }

    private City[] cities;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        ViewHolder(View v) {
            super(v);
            mTextView = itemView.findViewById(R.id.tv);
        }
    }

    CityRecyclerViewAdapter(City[] cities) {
        this.cities = cities;
    }

    @NonNull
    @Override
    public CityRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextView.setText(String.format("%s, %s", cities[position].getName(), cities[position].getCountry()));
    }

    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.length;
    }
}
