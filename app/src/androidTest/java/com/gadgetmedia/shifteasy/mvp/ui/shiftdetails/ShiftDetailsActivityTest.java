package com.gadgetmedia.shifteasy.mvp.ui.shiftdetails;


import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.gadgetmedia.shifteasy.mvp.R;
import com.gadgetmedia.shifteasy.mvp.ShiftEasy;
import com.gadgetmedia.shifteasy.mvp.data.Shift;
import com.gadgetmedia.shifteasy.mvp.di.AppComponent;
import com.gadgetmedia.shifteasy.mvp.di.DaggerAppComponent;
import com.gadgetmedia.shifteasy.mvp.util.EspressoIdlingResource;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ShiftDetailsActivityTest {

    private static int SHIFT_ID = 1;
    private static String SHIFT_START = "2018-02-25T22:21:31+11:00";
    private static String SHIFT_END = "2018-02-25T22:21:31+11:00";
    private static String SHIFT_START_LAT = "33.8688";
    private static String SHIFT_START_LON = "151.2093";
    private static String SHIFT_END_LAT = "33.8688";
    private static String SHIFT_END_LON = "151.2093";
    private static String SHIFT_IMAGE = "https://unsplash.it/500/500?random";

    /**
     * {@link Shift} stub that is added to the fake service API layer.
     */
    private static Shift ACTIVE_SHIFT = new Shift(SHIFT_ID, SHIFT_START, SHIFT_END, SHIFT_START_LAT, SHIFT_START_LON, SHIFT_END_LAT, SHIFT_END_LON, SHIFT_IMAGE);

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     * <p>
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     * <p>
     * <p>
     * Sometimes an {@link Activity} requires a custom start {@link Intent} to receive data
     * from the source Activity. ActivityTestRule has a feature which let's you lazily start the
     * Activity under test, so you can control the Intent that is used to start the target Activity.
     */
    @Rule
    public ActivityTestRule<ShiftDetailActivity> mShiftDetailsActivityTestRule =
            new ActivityTestRule<>(ShiftDetailActivity.class, true /* Initial touch mode  */,
                    false /* Lazily launch activity */);
    private AppComponent mAppComponent;

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests
     * significantly more reliable.
     */
    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Before
    public void resetRepository() {
        ShiftEasy application = (ShiftEasy) InstrumentationRegistry.getTargetContext().getApplicationContext();
        mAppComponent = DaggerAppComponent.builder().application(application).build();
        mAppComponent.inject(application);
        mAppComponent.getShiftsRepository().deleteAllShifts();
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    private void loadShift() {
        startActivityWithStubbedShift(ACTIVE_SHIFT);
    }

    /**
     * Setup your test fixture with a fake task id. The {@link ShiftDetailActivity} is started with
     * a particular task id, which is then loaded from the service API.
     * <p>
     * <p>
     * Note that this test runs hermetically and is fully isolated using a fake implementation of
     * the service API. This is a great way to make your tests more reliable and faster at the same
     * time, since they are isolated from any outside dependencies.
     */

    private void startActivityWithStubbedShift(final Shift activeShift) {

        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent startIntent = new Intent();
        startIntent.putExtra(ShiftDetailFragment.ARG_ITEM_ID, new Gson().toJson(activeShift));
        mShiftDetailsActivityTestRule.launchActivity(startIntent);
    }

    @Test
    public void activeShiftDetails_DisplayedInUi() throws Exception {
        loadShift();

        // Check that the Shift title and description are displayed
        onView(withId(R.id.item_detail)).check(matches(withText("Shift Started at: \nSun 25/02/18 11:21 AM\n\nShift Ended at: \nSun 25/02/18 11:21 AM\n\nStart Location: \nLat: 33.8688 Lon: 151.2093\n\nEnd Location: \nLat: 33.8688 Lon: 151.2093")));

    }

}
