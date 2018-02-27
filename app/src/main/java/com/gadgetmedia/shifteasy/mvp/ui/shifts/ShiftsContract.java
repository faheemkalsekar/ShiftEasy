package com.gadgetmedia.shifteasy.mvp.ui.shifts;

import com.gadgetmedia.shifteasy.mvp.BasePresenter;
import com.gadgetmedia.shifteasy.mvp.BaseView;
import com.gadgetmedia.shifteasy.mvp.api.ShiftRequestData;
import com.gadgetmedia.shifteasy.mvp.data.Business;
import com.gadgetmedia.shifteasy.mvp.data.Shift;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface ShiftsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(final boolean active);

        void showShifts(final List<Shift> tasks);

        boolean isActive();

        void showBusinessInfo(final List<Business> businessInfo);

        void showNoBusinessInfo();

        void showServerMessage(String message);

        void showNoShift();

        void reloadShifts();
    }

    interface Presenter extends BasePresenter<View> {

        void loadBusinessInfo(final boolean forceUpdate);

        void loadShifts(final boolean forceUpdate);

        void startShift(final ShiftRequestData shiftRequestData, final boolean showLoadingUI);

        void endShift(final ShiftRequestData shiftRequestData, final boolean showLoadingUI);

        void takeView(final ShiftsContract.View view);

        void dropView();
    }
}

