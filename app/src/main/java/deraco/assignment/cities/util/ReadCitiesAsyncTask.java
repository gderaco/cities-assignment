package deraco.assignment.cities.util;

import android.content.res.AssetManager;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import deraco.assignment.cities.MainActivityFragment;
import deraco.assignment.cities.model.City;

/**
 * Created by giuseppederaco on 16/03/2018.
 */

public class ReadCitiesAsyncTask extends AsyncTask<Void, Void, City[]> {

    private WeakReference<MainActivityFragment> activityWReference;

    public ReadCitiesAsyncTask(MainActivityFragment activity) {
        activityWReference = new WeakReference<>(activity);
    }

    @Override
    protected City[] doInBackground(Void... voids) {
        AssetManager assets = activityWReference.get().getActivity().getAssets();
        return Utilities.readCities(assets);
    }

    @Override
    protected void onPostExecute(City[] cities) {
        super.onPostExecute(cities);
        activityWReference.get().onObtainedCities(cities);
    }
}