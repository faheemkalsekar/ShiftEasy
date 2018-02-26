package com.gadgetmedia.shifteasy.mvp.ui.shiftdetails;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gadgetmedia.shifteasy.mvp.R;
import com.gadgetmedia.shifteasy.mvp.data.Shift;
import com.gadgetmedia.shifteasy.mvp.util.ISO8601;
import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ShiftDetailActivity}
 * in two-pane mode (on tablets) or a {@link ShiftDetailActivity}
 * on handsets.
 */
public class ShiftDetailFragment extends DaggerFragment {


    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private Shift mShift;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    @Inject
    public ShiftDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            final String shiftObject = getArguments().getString(ARG_ITEM_ID);
            mShift = new Gson().fromJson(shiftObject, Shift.class);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Shift " + mShift.getId());
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (mShift != null) {
            try {

                final String startDate = ISO8601.convertISOToDate(mShift.getStartTime());
                final String endDate = ISO8601.convertISOToDate(mShift.getStartTime());
                final StringBuilder sb = new StringBuilder();
                sb.append("Shift Started at:")
                        .append('\n')
                        .append(startDate)
                        .append('\n')
                        .append('\n')
                        .append("Shift Ended at:")
                        .append('\n')
                        .append(endDate);

                ((TextView) rootView.findViewById(R.id.item_detail)).setText(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return rootView;
    }
}
