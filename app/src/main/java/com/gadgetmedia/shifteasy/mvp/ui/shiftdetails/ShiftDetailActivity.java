package com.gadgetmedia.shifteasy.mvp.ui.shiftdetails;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gadgetmedia.shifteasy.mvp.R;
import com.gadgetmedia.shifteasy.mvp.util.ActivityUtils;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ShiftDetailActivity}.
 */
public class ShiftDetailActivity extends DaggerAppCompatActivity {

    @Inject
    Lazy<ShiftDetailFragment> shiftDetailFragmentLazy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_details);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ShiftDetailFragment fragment =
                (ShiftDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (fragment == null) {
            // Get the fragment from dagger
            Bundle arguments = new Bundle();
            arguments.putString(ShiftDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ShiftDetailFragment.ARG_ITEM_ID));

            fragment = shiftDetailFragmentLazy.get();
            fragment.setArguments(arguments);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.item_detail_container);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
//            NavUtils.navigateUpTo(this, new Intent(this, ShiftsListActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
