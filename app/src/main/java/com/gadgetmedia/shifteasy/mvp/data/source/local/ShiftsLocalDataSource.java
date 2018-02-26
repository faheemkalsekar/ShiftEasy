package com.gadgetmedia.shifteasy.mvp.data.source.local;

import android.support.annotation.NonNull;

import com.gadgetmedia.shifteasy.mvp.api.ShiftRequestData;
import com.gadgetmedia.shifteasy.mvp.data.Business;
import com.gadgetmedia.shifteasy.mvp.data.Shift;
import com.gadgetmedia.shifteasy.mvp.data.source.ShiftsDataSource;
import com.gadgetmedia.shifteasy.mvp.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a Shifts Data source as a db.
 */
@Singleton
public class ShiftsLocalDataSource implements ShiftsDataSource {

    private final ShiftsDao mShiftsDao;
    private final BusinessDao mBizDao;
    private final AppExecutors mAppExecutors;

    @Inject
    public ShiftsLocalDataSource(@NonNull final AppExecutors executors,
                                 @NonNull final ShiftsDao shiftsDao,
                                 @NonNull final BusinessDao businessDao) {
        mAppExecutors = executors;
        mShiftsDao = shiftsDao;
        mBizDao = businessDao;
    }
    /**
     * Note: {@link LoadBusinessInfoCallback#onDataNotAvailable(String)} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getBusinessInfo(@NonNull final LoadBusinessInfoCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Business> business = mBizDao.getBusiness();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (business.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable("Not in DB, load from server");
                        } else {
                            callback.onBusinessInfoLoaded(business);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getShifts(@NonNull final LoadShiftsCallback callback) {

    }

    @Override
    public void getShift(@NonNull final String shiftId, @NonNull final GetShiftCallback callback) {

    }

    @Override
    public void startShift(@NonNull ShiftRequestData shift) {

    }

    @Override
    public void endShift(@NonNull ShiftRequestData shift) {

    }

    @Override
    public void refreshShifts() {

    }

    @Override
    public void refreshBusinessInfo() {

    }

    public void deleteAllShifts() {

        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mShiftsDao.deleteShifts();
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    public void deleteAllBiz() {

        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mBizDao.deleteBusiness();
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);

    }

    public void saveShiftsList(final List<Shift> shiftList) {
        checkNotNull(shiftList);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mShiftsDao.insertShift(shiftList);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    public void saveBusinessList(final List<Business> businessList) {
        checkNotNull(businessList);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mBizDao.insertBusiness(businessList);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }
}
