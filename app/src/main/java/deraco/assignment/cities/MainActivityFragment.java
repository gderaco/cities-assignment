package deraco.assignment.cities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import deraco.assignment.cities.util.ReadCitiesAsyncTask;
import deraco.assignment.cities.util.Utilities;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements SearchView.OnQueryTextListener {

    City[] cities;
    Context mContext;

    ProgressBar mProgressbar;

    private RecyclerView mRecyclerView;
    private CityRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        //mProgressbar = mainView.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) mainView.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return mainView;
    }

    public void onObtainedCities(City[] cities) {
        this.cities = cities;
        //mProgressbar.setVisibility(View.GONE);
        mAdapter = new CityRecyclerViewAdapter(cities);
        mRecyclerView.setAdapter(mAdapter);
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
        filterCities(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        filterCities(query);
        return false;
    }

    private void filterCities(String query) {
        City[] filterCities = Utilities.filterCitiesByPrefix(this.cities, query);
        mAdapter.setCities(filterCities);
        mAdapter.notifyDataSetChanged();
    }

}
