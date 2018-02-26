package com.gadgetmedia.shifteasy.mvp.data.source;

import android.support.annotation.NonNull;

import com.gadgetmedia.shifteasy.mvp.api.ShiftRequestData;
import com.gadgetmedia.shifteasy.mvp.data.Business;
import com.gadgetmedia.shifteasy.mvp.data.Shift;
import com.gadgetmedia.shifteasy.mvp.data.source.local.ShiftsLocalDataSource;
import com.gadgetmedia.shifteasy.mvp.di.AppComponent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load shifts from the data sources into a cache.
 * <p>
 * This implements a synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 * <p/>
 * By marking the constructor with {@code @Inject} and the class with {@code @Singleton}, Dagger
 * injects the dependencies required to create an instance of the TasksRespository (if it fails, it
 * emits a compiler error). It uses {@link ShiftsRepositoryModule} to do so, and the constructed
 * instance is available in {@link AppComponent}.
 * <p/>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 */
@Singleton
public class ShiftsRepository implements ShiftsDataSource {

    private final ShiftsDataSource mRemoteShiftsDataSource;

    private final ShiftsDataSource mLocalShiftsDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<Integer, Business> mCachedBusiness;
    Map<Integer, Shift> mCachedShift;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mBusinessCacheIsDirty = false;
    boolean mShiftsCacheIsDirty = false;

    /**
     * By marking the constructor with {@code @Inject}, Dagger will try to inject the dependencies
     * required to create an instance of the TasksRepository. Because {@link ShiftsDataSource} is an
     * interface, we must provide to Dagger a way to build those arguments, this is done in
     * {@link ShiftsRepositoryModule}.
     * <p>
     * When two arguments or more have the same type, we must provide to Dagger a way to
     * differentiate them. This is done using a qualifier.
     * <p>
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */

    @Inject
    ShiftsRepository(@Remote final ShiftsDataSource remoteShiftsDataSource,
                     @Local final ShiftsDataSource localShiftsDataSource) {

        mRemoteShiftsDataSource = remoteShiftsDataSource;
        mLocalShiftsDataSource = localShiftsDataSource;
    }

    /**
     * Gets Business Info from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadBusinessInfoCallback#onDataNotAvailable(String)} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getBusinessInfo(@NonNull final LoadBusinessInfoCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedBusiness != null && !mBusinessCacheIsDirty) {
            callback.onBusinessInfoLoaded(new ArrayList<>(mCachedBusiness.values()));
        }

        if (mBusinessCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getBizInfoFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mLocalShiftsDataSource.getBusinessInfo(new LoadBusinessInfoCallback() {
                @Override
                public void onBusinessInfoLoaded(final List<Business> businessInfo) {
                    refreshBizCache(businessInfo);
                    callback.onBusinessInfoLoaded(businessInfo);
                }

                @Override
                public void onDataNotAvailable(String message) {
                    getBizInfoFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void getShifts(@NonNull final LoadShiftsCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedShift != null && !mShiftsCacheIsDirty) {
            callback.onShiftsLoaded(new ArrayList<>(mCachedShift.values()));
        }

        if (mShiftsCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getShiftFromServer(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mLocalShiftsDataSource.getShifts(new LoadShiftsCallback() {
                @Override
                public void onShiftsLoaded(final List<Shift> shifts) {
                    refreshShiftCache(shifts);
                    callback.onShiftsLoaded(shifts);
                }

                @Override
                public void onDataNotAvailable(final String errorMessage) {
                    getShiftFromServer(callback);
                }
            });
        }
    }


    private void getShiftFromServer(final LoadShiftsCallback callback) {

        mRemoteShiftsDataSource.getShifts(new LoadShiftsCallback() {
            @Override
            public void onShiftsLoaded(final List<Shift> shifts) {
                refreshShiftCache(shifts);
                refreshLocalShift(shifts);
                callback.onShiftsLoaded(shifts);
            }

            @Override
            public void onDataNotAvailable(final String errorMessage) {
                callback.onDataNotAvailable(errorMessage);
            }
        });
    }

    private void refreshShiftCache(final List<Shift> shiftList) {
        if (mCachedShift == null) {
            mCachedShift = new LinkedHashMap<>();
        }
        mCachedShift.clear();
        for (final Shift shift : shiftList) {
            mCachedShift.put(shift.getId(), shift);
        }
        mShiftsCacheIsDirty = false;
    }

    private void refreshLocalShift(final List<Shift> shiftList) {
        ShiftsLocalDataSource localDataSource = (ShiftsLocalDataSource) mLocalShiftsDataSource;
        localDataSource.deleteAllShifts();
        localDataSource.saveShiftsList(shiftList);
    }

    private void refreshBizCache(final List<Business> businesses) {
        if (mCachedBusiness == null) {
            mCachedBusiness = new LinkedHashMap<>();
        }
        mCachedBusiness.clear();
        for (final Business business : businesses) {
            mCachedBusiness.put(business.getId(), business);
        }
        mBusinessCacheIsDirty = false;
    }

    private void refreshLocalBizDataSource(final List<Business> businessInfo) {
        ShiftsLocalDataSource localDataSource = (ShiftsLocalDataSource) mLocalShiftsDataSource;
        localDataSource.deleteAllBiz();
        localDataSource.saveBusinessList(businessInfo);
    }


    private void getBizInfoFromRemoteDataSource(final LoadBusinessInfoCallback callback) {

        mRemoteShiftsDataSource.getBusinessInfo(new LoadBusinessInfoCallback() {
            @Override
            public void onBusinessInfoLoaded(final List<Business> businessInfo) {
                refreshBizCache(businessInfo);
                refreshLocalBizDataSource(businessInfo);
                callback.onBusinessInfoLoaded(new ArrayList<>(mCachedBusiness.values()));
            }

            @Override
            public void onDataNotAvailable(final String message) {
                callback.onDataNotAvailable(message);
            }
        });
    }




    @Override
    public void getShift(@NonNull final String shiftId, @NonNull final GetShiftCallback callback) {

    }

    @Override
    public void startShift(@NonNull final ShiftRequestData shift) {

    }

    @Override
    public void endShift(@NonNull final ShiftRequestData shift) {

    }

    @Override
    public void refreshShifts() {
        mShiftsCacheIsDirty = true;
    }


    @Override
    public void refreshBusinessInfo() {
        mBusinessCacheIsDirty = true;
    }


}
