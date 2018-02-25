package com.gadgetmedia.shifteasy.mvp.data.source;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.gadgetmedia.shifteasy.mvp.data.source.local.BusinessDao;
import com.gadgetmedia.shifteasy.mvp.data.source.local.ShiftsDao;
import com.gadgetmedia.shifteasy.mvp.data.source.local.ShiftsDatabase;
import com.gadgetmedia.shifteasy.mvp.data.source.local.ShiftsLocalDataSource;
import com.gadgetmedia.shifteasy.mvp.data.source.remote.ShiftsRemoteDataSource;
import com.gadgetmedia.shifteasy.mvp.util.AppExecutors;
import com.gadgetmedia.shifteasy.mvp.util.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * This is used by Dagger to inject the required arguments into the {@link ShiftsRepository}.
 */
@Module
abstract public class ShiftsRepositoryModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Provides
    static ShiftsDatabase provideDb(final Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), ShiftsDatabase.class, "Shifts.db")
                .build();
    }

    @Singleton
    @Provides
    static ShiftsDao provideShiftsDao(final ShiftsDatabase db) {
        return db.shiftsDao();
    }

    @Singleton
    @Provides
    static BusinessDao provideBusinessDao(final ShiftsDatabase db) {
        return db.businessDao();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }

    @Singleton
    @Binds
    @Local
    abstract ShiftsDataSource provideShiftsLocalDataSource(final ShiftsLocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract ShiftsDataSource provideShiftsRemoteDataSource(final ShiftsRemoteDataSource dataSource);
}
