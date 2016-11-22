package majorproject.amity.smarttourist.adapter;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.SmartTouristApp;
import majorproject.amity.smarttourist.models.Poi;
import majorproject.amity.smarttourist.utils.MyTextView;
import majorproject.amity.smarttourist.utils.TheSmartTourist;

/**
 * Created by vijim_000 on 6/10/2016.
 */
public class ItineraryAdapter extends RecyclerView.Adapter<ItineraryAdapter.ItineraryHolder> implements ItemTouchHelperAdapter {

    private static MyClickListener myClickListener;
    private List<Poi> mDataset;

    public interface OnDragStartListener {
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
    }

    private final OnDragStartListener mDragStartListener;

    public ItineraryAdapter(OnDragStartListener dragStartListener, List<Poi> myDataset) {
        mDragStartListener = dragStartListener;
        mDataset = myDataset;
    }


    public ItineraryAdapter(List<Poi> myDataset) {
        mDataset = myDataset;
        mDragStartListener = null;
    }

    @Override
    public ItineraryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_itinerary, parent, false);
        ItineraryHolder dataObjectHolder = new ItineraryHolder(view);
        return dataObjectHolder;
    }
    @Override
    public void onBindViewHolder(final ItineraryHolder holder, int position) {
        holder.name.setText(mDataset.get(position).name.toUpperCase());
        String imgName = "p_"+mDataset.get(position).idpoi;
        Resources resources = SmartTouristApp.getContext().getResources();
        //holder.imgHotel.setImageResource(resources.getIdentifier(imgName, "drawable", SmartTouristApp.getContext().getPackageName()));
        int resourceId = SmartTouristApp.getContext().getResources().getIdentifier(imgName, "drawable", SmartTouristApp.getContext().getPackageName());
        holder.imgData.setImageBitmap(TheSmartTourist.decodeSampledBitmapFromResource(resources, resourceId, 25, 100));
        holder.timing.setText(mDataset.get(position).approx_time_req);
        holder.cost.setText(mDataset.get(position).cost);
        holder.handle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onDragStarted(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mDataset, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mDataset, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);

    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void addItem(Poi dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public static class ItineraryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgData;
        MyTextView name;
        MyTextView timing;
        MyTextView cost;
        ImageView handle;

        public ItineraryHolder(View itemView) {
            super(itemView);
            imgData = (ImageView) itemView.findViewById(R.id.imgBackground);
            name = (MyTextView) itemView.findViewById(R.id.name);
            timing = (MyTextView) itemView.findViewById(R.id.timing);
            cost = (MyTextView) itemView.findViewById(R.id.cost);
            handle = (ImageView) itemView.findViewById(R.id.handle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
