package deraco.assignment.cities.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import deraco.assignment.cities.model.City;

/**
 * Created by giuseppederaco on 16/03/2018.
 */

public class CitiesLoader extends AsyncTaskLoader<City[]> {
    private City[] cities;
    private String filter;

    public CitiesLoader(Context context, City[] cities, String filter) {
        super(context);
        this.cities = cities;
        this.filter = filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Nullable
    @Override
    public City[] loadInBackground() {
        return Utilities.filterCitiesByPrefix(cities, filter);
    }
}
