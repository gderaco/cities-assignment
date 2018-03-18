package deraco.assignment.cities.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import deraco.assignment.cities.model.City;

/**
 * Created by giuseppederaco on 16/03/2018.
 */

public class CitiesLoader extends AsyncTaskLoader<City[]> {
    private City[] mCities;
    private String mFilter;

    public CitiesLoader(Context context, City[] cities, String filter) {
        super(context);
        this.mCities = cities;
        this.mFilter = filter;
    }

    @Nullable
    @Override
    public City[] loadInBackground() {
        return Utilities.filterCitiesByPrefix(mCities, mFilter);
    }
}
