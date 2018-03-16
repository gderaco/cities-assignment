package deraco.assignment.cities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import deraco.assignment.cities.model.City;
import deraco.assignment.cities.util.CitiesLoader;
import deraco.assignment.cities.util.ReadCitiesAsyncTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<City[]>, CityRecyclerViewAdapter.OnCityClickListener {

    private City[] cities;
    private Context mContext;

    private ProgressBar mProgressbar;

    private RecyclerView mRecyclerView;
    private CityRecyclerViewAdapter mAdapter;

    private String filter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        this.setRetainInstance(true);

        setHasOptionsMenu(true);

        //In order to provide a smooth experience for the user, it's wise to load the json file in a background thread
        ReadCitiesAsyncTask readCitiesAsyncTask = new ReadCitiesAsyncTask(this);
        readCitiesAsyncTask.execute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_main, container, false);

        mProgressbar = mainView.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) mainView.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CityRecyclerViewAdapter(cities, this);
        mRecyclerView.setAdapter(mAdapter);

        if (cities != null && cities.length > 0) //configuration change
        {
            mProgressbar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        return mainView;
    }

    public void onObtainedCities(City[] cities) {
        this.cities = cities;
        mProgressbar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mAdapter.setCities(cities);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        filterCities(query);
        return true;
    }

    private void filterCities(String query) {
        filter = query;
        getLoaderManager().restartLoader(0, null, this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<City[]> onCreateLoader(int id, @Nullable Bundle args) {
        return new CitiesLoader(getActivity(), cities, filter);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<City[]> loader, City[] filteredData) {
        mAdapter.setCities(filteredData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<City[]> loader) {
    }

    @Override
    public void onCityClick(City city) {

        MapFragment mapFragment = new MapFragment();
        mapFragment.setCity(city);
        getFragmentManager().beginTransaction().add(R.id.fragment_frame_layout, mapFragment).addToBackStack(null).commit();

    }
}
