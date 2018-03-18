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

    private WeakReference<MainActivityFragment> mActivityWReference;

    public ReadCitiesAsyncTask(MainActivityFragment activity) {
        mActivityWReference = new WeakReference<>(activity);
    }

    @Override
    protected City[] doInBackground(Void... voids) {
        MainActivityFragment mainActivityFragment = mActivityWReference.get();
        if (mainActivityFragment != null) {

            AssetManager assets = mainActivityFragment.getActivity().getAssets();
            return Utilities.readCities(assets);
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(City[] cities) {
        super.onPostExecute(cities);
        MainActivityFragment mainActivityFragment = mActivityWReference.get();
        if (mainActivityFragment != null)
            mainActivityFragment.onObtainedCities(cities);
    }
}