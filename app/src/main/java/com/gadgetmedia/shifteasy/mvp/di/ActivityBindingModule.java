package com.gadgetmedia.shifteasy.mvp.di;

import com.gadgetmedia.shifteasy.mvp.ui.shiftdetails.ShiftDetailActivity;
import com.gadgetmedia.shifteasy.mvp.ui.shiftdetails.ShiftDetailPresenterModule;
import com.gadgetmedia.shifteasy.mvp.ui.shifts.ShiftsListActivity;
import com.gadgetmedia.shifteasy.mvp.ui.shifts.ShiftsPresenterModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 * When Dagger.Android annotation processor runs it will create 4 subcomponents for us.
 */
@Module
public abstract class ActivityBindingModule {
    //The general flow:
    // Activity creates a fragment,
    // fragment registers with dagger,
    // dagger injects the presenter directly into the fragment.

    @ActivityScoped
    @ContributesAndroidInjector(modules = ShiftsPresenterModule.class)
    abstract ShiftsListActivity shiftsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = ShiftDetailPresenterModule.class)
    abstract ShiftDetailActivity shiftDetailActivity();
}
