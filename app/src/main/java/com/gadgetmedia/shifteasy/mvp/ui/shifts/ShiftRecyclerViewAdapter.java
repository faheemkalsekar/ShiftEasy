package com.gadgetmedia.shifteasy.mvp.ui.shifts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gadgetmedia.shifteasy.mvp.R;
import com.gadgetmedia.shifteasy.mvp.data.Shift;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Shift} and makes a call to the
 * specified {@link View.OnClickListener}.
 */
public class ShiftRecyclerViewAdapter extends RecyclerView.Adapter<ShiftRecyclerViewAdapter.ViewHolder> {

    private final ShiftsListActivity mParentActivity;
    private final boolean mTwoPane;
    private final Picasso mPicasso;
    /*
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(ShiftDetailFragment.ARG_ITEM_ID, item.id);
                ShiftDetailFragment fragment = new ShiftDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, ShiftDetailActivity.class);
                intent.putExtra(ShiftDetailFragment.ARG_ITEM_ID, item.id);

                context.startActivity(intent);
            }
        }
    };
    */
    private List<Shift> mValues;


    ShiftRecyclerViewAdapter(ShiftsListActivity parent,
                             final Picasso picasso,
                             final List<Shift> items,
                             boolean twoPane) {
        mValues = items;
        mPicasso = picasso;
        mTwoPane = twoPane;
        mParentActivity = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shift_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(String.valueOf(holder.mItem.getId()));
        holder.mDescView.setText(holder.mItem.getStartTime());

        // Resize to the width specified maintaining aspect ratio
        mPicasso.load(holder.mItem.getImage())
                .resize(240, 0).
                into(holder.mImageView);

        holder.itemView.setTag(mValues.get(position));
//        holder.itemView.setOnClickListener(mOnClickListener);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void replaceData(final List<Shift> shiftList) {
        setList(shiftList);
        notifyDataSetChanged();
    }

    private void setList(final List<Shift> newsList) {
        mValues = checkNotNull(newsList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mDescView;
        public final ImageView mImageView;
        public Shift mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = view.findViewById(R.id.title);
            mDescView = view.findViewById(R.id.desc);
            mImageView = view.findViewById(R.id.imageView);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
