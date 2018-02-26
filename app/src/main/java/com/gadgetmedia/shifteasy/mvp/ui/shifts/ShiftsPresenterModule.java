package com.gadgetmedia.shifteasy.mvp.ui.shifts;

import com.gadgetmedia.shifteasy.mvp.di.ActivityScoped;
import com.gadgetmedia.shifteasy.mvp.di.FragmentScoped;
import com.gadgetmedia.shifteasy.mvp.ui.shiftdetails.ShiftDetailFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link ShiftsPresenter}.
 */
@Module
public abstract class ShiftsPresenterModule {
    //NOTE:  IF you want to have something be only in the Fragment scope but not activity mark a
    //@provides or @Binds method as @FragmentScoped.  Use case is when there are multiple fragments
    //in an activity but you do not want them to share all the same objects.

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ShiftDetailFragment shiftDetailFragment();

    @ActivityScoped
    @Binds
    abstract ShiftsContract.Presenter shiftsPresenter(final ShiftsPresenter presenter);
}
