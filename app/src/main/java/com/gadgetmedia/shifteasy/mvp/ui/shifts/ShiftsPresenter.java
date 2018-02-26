package com.gadgetmedia.shifteasy.mvp.ui.shifts;

import com.gadgetmedia.shifteasy.mvp.data.Business;
import com.gadgetmedia.shifteasy.mvp.data.Shift;
import com.gadgetmedia.shifteasy.mvp.data.source.ShiftsDataSource;
import com.gadgetmedia.shifteasy.mvp.data.source.ShiftsRepository;
import com.gadgetmedia.shifteasy.mvp.util.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Listens to user actions from the UI ({@link ShiftsListActivity}), retrieves the data and updates the
 * UI as required.
 * <p/>
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the {@link ShiftsPresenter} (if it fails, it emits a compiler error).  It uses
 * {@link ShiftsPresenterModule} to do so.
 * <p/>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 **/

final class ShiftsPresenter implements ShiftsContract.Presenter {

    private final ShiftsRepository mShiftsRepository;

    @Nullable
    private ShiftsContract.View mShiftsView;

    private boolean mFirstLoad = true;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    ShiftsPresenter(final ShiftsRepository shiftsRepository) {
        mShiftsRepository = shiftsRepository;
    }

    @Override
    public void loadBusinessInfo(boolean forceUpdate) {
        // A network reload will be forced on first load.
        loadBusinessInfo(forceUpdate || mFirstLoad, false);
    }

    @Override
    public void loadShifts(final boolean forceUpdate) {
        // A network reload will be forced on first load.
        loadShifts(forceUpdate || mFirstLoad, true);
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link ShiftsDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadBusinessInfo(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            if (mShiftsView != null) {
                mShiftsView.setLoadingIndicator(true);
            }
        }
        if (forceUpdate) {
            mShiftsRepository.refreshBusinessInfo();
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        // App is busy until further notice
        EspressoIdlingResource.increment();

        mShiftsRepository.getBusinessInfo(new ShiftsDataSource.LoadBusinessInfoCallback() {
            @Override
            public void onBusinessInfoLoaded(final List<Business> businessInfo) {
                final List<Business> businessToShow = new ArrayList<>();

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                if (mShiftsView == null || !mShiftsView.isActive()) {
                    return;
                }

                if (showLoadingUI) {
                    mShiftsView.setLoadingIndicator(false);
                }

                businessToShow.addAll(businessInfo);

                processBusinessInfo(businessToShow);
            }

            @Override
            public void onDataNotAvailable(String message) {

            }
        });
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link ShiftsDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadShifts(final boolean forceUpdate, final boolean showLoadingUI) {

        if (showLoadingUI) {
            if (mShiftsView != null) {
                mShiftsView.setLoadingIndicator(true);
            }
        }

        if (forceUpdate) {
            mShiftsRepository.refreshShifts();
        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        // App is busy until further notice
        EspressoIdlingResource.increment();

        mShiftsRepository.getShifts(new ShiftsDataSource.LoadShiftsCallback() {
            @Override
            public void onShiftsLoaded(final List<Shift> shifts) {
                final List<Shift> shiftsToShow = new ArrayList<>();

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.
                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                if (mShiftsView == null || !mShiftsView.isActive()) {
                    return;
                }

                if (showLoadingUI) {
                    mShiftsView.setLoadingIndicator(false);
                }

                shiftsToShow.addAll(shifts);
                processShiftInfo(shiftsToShow);
            }

            @Override
            public void onDataNotAvailable(final String errorMessage) {
                // The view may not be able to handle UI updates anymore
                if (!mShiftsView.isActive()) {
                    return;
                }

                if (showLoadingUI) {
                    mShiftsView.setLoadingIndicator(false);
                }

                mShiftsView.showLoadingShiftsError(errorMessage);
            }
        });



    }

    private void processShiftInfo(final List<Shift> shiftsToShow) {
        if (shiftsToShow.isEmpty()) {
            // Show a message indicating there are no business info for now.
            if (mShiftsView != null) {
                mShiftsView.showNoShift();
            }
        } else {
            // Show business info
            if (mShiftsView != null) {
                mShiftsView.showShifts(shiftsToShow);
            }
        }
    }

    private void processBusinessInfo(final List<Business> business) {
        if (business.isEmpty()) {
            // Show a message indicating there are no business info for now.
            if (mShiftsView != null) {
                mShiftsView.showNoBusinessInfo();
            }
        } else {
            // Show business info
            if (mShiftsView != null) {
                mShiftsView.showBusinessInfo(business);
            }
        }
    }




    @Override
    public void takeView(final ShiftsContract.View view) {
        mShiftsView = view;
        loadBusinessInfo(false);
        loadShifts(false);
        mFirstLoad = false;
    }

    @Override
    public void dropView() {
        mShiftsView = null;

    }
}
