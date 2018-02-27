package com.gadgetmedia.shifteasy.mvp.ui.shifts;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import com.gadgetmedia.shifteasy.mvp.R;
import com.gadgetmedia.shifteasy.mvp.ShiftEasy;
import com.gadgetmedia.shifteasy.mvp.data.Shift;
import com.gadgetmedia.shifteasy.mvp.di.AppComponent;
import com.gadgetmedia.shifteasy.mvp.di.DaggerAppComponent;
import com.gadgetmedia.shifteasy.mvp.ui.shiftdetails.ShiftDetailActivity;
import com.gadgetmedia.shifteasy.mvp.util.EspressoIdlingResource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.gadgetmedia.shifteasy.mvp.R.id.listtoolbar;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ShiftsListActivityTest {

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
    public ActivityTestRule<ShiftsListActivity> mShiftListActivityTestRule =
            new ActivityTestRule<>(ShiftsListActivity.class, false /* Initial touch mode  */,
                    false /* Lazily launch activity */);
    private AppComponent mAppComponent;

    /**
     * Matches the toolbar title with a specific string resource.
     *
     * @param resourceId the ID of the string resource to match
     */
    public static Matcher<View> withToolbarTitle(final int resourceId) {
        return new BoundedMatcher<View, Toolbar>(Toolbar.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title from resource id: ");
                description.appendValue(resourceId);
            }

            @Override
            protected boolean matchesSafely(Toolbar toolbar) {
                CharSequence expectedText = "";
                try {
                    expectedText = toolbar.getResources().getString(resourceId);
                } catch (Resources.NotFoundException ignored) {
                    /* view could be from a context unaware of the resource id. */
                }
                CharSequence actualText = toolbar.getTitle();
                return expectedText.equals(actualText);
            }
        };
    }

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
        ShiftEasy application = (ShiftEasy) getTargetContext().getApplicationContext();
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

    @Test
    public void shiftsListActivityTest() {
//        new FakeRemoteDataSource().addShifts(ACTIVE_SHIFT);

        loadEmptyShiftList();

        // Check that the toolbar shows the correct title
        onView(withId(listtoolbar)).check(matches(withToolbarTitle(R.string.title_activity_shift)));
        onView(withId(R.id.noShiftMain)).check(matches(withText("No Shifts To Display")));
        onView(withId(R.id.noShiftsAdd)).check(matches(withText("Looking for a Shift. Deputy has an opening")));


    }

    private void loadEmptyShiftList() {
        startActivityWithEmptyShiftList();
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

    private void startActivityWithEmptyShiftList() {

        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent startIntent = new Intent();
        mShiftListActivityTestRule.launchActivity(startIntent);
    }
}
