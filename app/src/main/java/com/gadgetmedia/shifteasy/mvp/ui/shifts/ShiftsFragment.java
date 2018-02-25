package com.gadgetmedia.shifteasy.mvp.ui.shifts;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gadgetmedia.shifteasy.mvp.R;
import com.gadgetmedia.shifteasy.mvp.data.Business;
import com.gadgetmedia.shifteasy.mvp.data.Shift;
import com.gadgetmedia.shifteasy.mvp.di.ActivityScoped;
import com.gadgetmedia.shifteasy.mvp.util.EmptyStateRecyclerView;
import com.gadgetmedia.shifteasy.mvp.util.ScrollChildSwipeRefreshLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Main UI for the Shifts List Screen.
 */
@ActivityScoped
public class ShiftsFragment extends DaggerFragment implements ShiftsContract.View {

    @Inject
    ShiftsContract.Presenter mPresenter;

    private OnBusinessChangedListener mCallback;
    private OnListFragmentInteractionListener mListener;

    @Inject
    public ShiftsFragment() {
        // The required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shift, container, false);

        // Set the adapter
        Context context = view.getContext();
        final EmptyStateRecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setEmptyView(view.findViewById(android.R.id.empty));
        recyclerView.setHasFixedSize(true);
//        mAdapter = new NewsItemRecyclerViewAdapter(Picasso.with(context),new ArrayList<News>(), mListener);
//        recyclerView.setAdapter(mAdapter);

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.colorAccent),
                ContextCompat.getColor(context, R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadShifts(true);
            }
        });

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        final Activity activity = (Activity) context;

        try {
            mCallback = (OnBusinessChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

        try {
            mListener = (OnListFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        //prevent leaking activity in case presenter is orchestrating a long running task
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showShifts(final List<Shift> tasks) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showBusinessInfo(List<Business> businessInfo) {
        Log.d("showBusinessInfo","showBusinessInfo");
        mCallback.onShowBusinessInfo(businessInfo.get(0));
    }

    @Override
    public void showNoBusinessInfo() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
        mListener = null;
    }

    // Shifts Activity must implement this interface
    public interface OnBusinessChangedListener {
        public void onShowBusinessInfo(final Business business);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(final Shift news);
    }

}
