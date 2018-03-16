package deraco.assignment.cities;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

import deraco.assignment.cities.model.City;
import deraco.assignment.cities.util.Utilities;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    City[] cities;
    Context mContext;

    ProgressBar mProgressbar;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        this.setRetainInstance(true);

        //In order to provide a smooth experience for the user, it's wise to load the json file in a background thread
        ReadCitiesAsyncTask readCitiesAsyncTask = new ReadCitiesAsyncTask(this);
        readCitiesAsyncTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_main, container, false);

        mProgressbar = mainView.findViewById(R.id.progressBar);

        return mainView;
    }

    private void onObtainedCities(City[] cities) {
        this.cities = cities;
        mProgressbar.setVisibility(View.GONE);
    }

    //

    static private class ReadCitiesAsyncTask extends AsyncTask<Void, Void, City[]> {

        WeakReference<MainActivityFragment> activityWReference;

        ReadCitiesAsyncTask(MainActivityFragment activity) {
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
}
