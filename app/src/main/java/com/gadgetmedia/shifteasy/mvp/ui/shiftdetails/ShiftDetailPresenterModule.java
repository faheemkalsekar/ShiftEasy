package com.gadgetmedia.shifteasy.mvp.ui.shiftdetails;

import com.gadgetmedia.shifteasy.mvp.di.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ShiftDetailPresenterModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract ShiftDetailFragment shiftDetailFragment();
}
