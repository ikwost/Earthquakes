package ru.inno.earthquakes.presentation.earthquakeslist;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.inno.earthquakes.entities.EarthquakeWithDist;

/**
 * @author Artur Badretdinov (Gaket)
 *         01.08.17
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface EarthquakesListView extends MvpView {

    void showEarthquakes(List<EarthquakeWithDist> earthquakeWithDists);

    // This method better should be in a router. It is here for simplicity now.
    void navigateToEarthquakesList();

    void showNetworkError(boolean show);

    void showLoading(boolean show);
}