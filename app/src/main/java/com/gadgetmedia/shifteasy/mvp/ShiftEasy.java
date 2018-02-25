package com.gadgetmedia.shifteasy.mvp;

import android.support.annotation.VisibleForTesting;

import com.gadgetmedia.shifteasy.mvp.data.source.ShiftsRepository;
import com.gadgetmedia.shifteasy.mvp.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class ShiftEasy extends DaggerApplication {

    @Inject
    ShiftsRepository shiftsRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    /**
     * Our Espresso tests need to be able to get an instance of the {@link ShiftsRepository}
     * so that we can delete all tasks before running each test
     */
    @VisibleForTesting
    public ShiftsRepository getShiftsRepository() {
        return shiftsRepository;
    }
}
