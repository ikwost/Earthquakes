package ru.inno.earthquakes.presentation.earthquakeslist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import ru.inno.earthquakes.EarthquakesApp;
import ru.inno.earthquakes.R;
import ru.inno.earthquakes.business.earthquakes.EarthquakesInteractor;
import ru.inno.earthquakes.business.location.LocationInteractor;
import ru.inno.earthquakes.models.entities.EarthquakeWithDist;
import ru.inno.earthquakes.presentation.common.SchedulersProvider;
import ru.inno.earthquakes.presentation.common.SmartDividerItemDecoration;
import ru.inno.earthquakes.presentation.common.views.EmptyRecyclerView;

public class EarthquakesListActivity extends MvpAppCompatActivity
    implements EarthquakesListView {

  @InjectPresenter
  EarthquakesListPresenter presenter;
  @Inject
  EarthquakesInteractor earthquakesInteractor;
  @Inject
  LocationInteractor locationInteractor;
  @Inject
  SchedulersProvider schedulersProvider;

  private SwipeRefreshLayout swipeRefreshLayout;
  private EarthquakesListAdapter earthquakesListAdapter;
  private Snackbar snackbar;

  @ProvidePresenter
  EarthquakesListPresenter providePresenter() {
    EarthquakesApp.getComponentsManager().getEarthquakesComponent().inject(this);
    return new EarthquakesListPresenter(earthquakesInteractor, locationInteractor, schedulersProvider);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_earth_quakes_list);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    swipeRefreshLayout = findViewById(R.id.earthquakes_swipe_refresh);
    swipeRefreshLayout.setOnRefreshListener(() -> presenter.onRefreshAction());
    initRecyclerView();
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void initRecyclerView() {
    EmptyRecyclerView recyclerView = findViewById(R.id.earthquakes_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setEmptyViewLayout(R.layout.empty_earthquakes);
    SmartDividerItemDecoration itemDecoration = new SmartDividerItemDecoration.Builder(this)
        .setMarginProvider((position, parent) -> 56)
        .build();
    recyclerView.addItemDecoration(itemDecoration);
    earthquakesListAdapter = new EarthquakesListAdapter(
        earthquakeWithDist -> presenter.onEarthquakeClick(earthquakeWithDist));
    recyclerView.setAdapter(earthquakesListAdapter);
  }


  public static Intent getStartIntent(Context callingContext) {
    return new Intent(callingContext, EarthquakesListActivity.class);
  }

  @Override
  public void showNetworkError(boolean show) {
    if (show) {
      snackbar = Snackbar.make(swipeRefreshLayout, R.string.error_connection, Snackbar.LENGTH_INDEFINITE)
          .setAction(R.string.action_ok, d -> snackbar.dismiss());
      snackbar.show();
    } else if (snackbar != null) {
      snackbar.dismiss();
    }
  }

  @Override
  public void showLoading(boolean show) {
    swipeRefreshLayout.setRefreshing(show);
  }

  @Override
  public void navigateToEarthquakeDetails(EarthquakeWithDist earthquakeWithDist) {
    Uri webpage = Uri.parse(earthquakeWithDist.getDetailsUrl());
    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
    if (intent.resolveActivity(this.getPackageManager()) != null) {
      startActivity(intent);
    }
  }

  @Override
  public void showEarthquakes(List<EarthquakeWithDist> earthquakeWithDists) {
    earthquakesListAdapter.setItems(earthquakeWithDists);
  }
}
