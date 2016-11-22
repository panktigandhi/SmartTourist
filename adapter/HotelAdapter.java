package majorproject.amity.smarttourist.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.List;
import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.models.HotelInfo;
import majorproject.amity.smarttourist.SmartTouristApp;
import majorproject.amity.smarttourist.utils.FontCache;
import majorproject.amity.smarttourist.utils.MyTextView;
import majorproject.amity.smarttourist.utils.TheSmartTourist;


public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelHolder> {
    private static MyClickListener myClickListener;
    private List<HotelInfo> mDataset;


    public HotelAdapter(List<HotelInfo> myDataset) {
        mDataset = myDataset;

    }

    @Override
    public HotelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_general, parent, false);
        HotelHolder dataObjectHolder = new HotelHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(HotelHolder holder, int position) {
        holder.name.setTypeface(FontCache.get(SmartTouristApp.getContext().getAssets(), SmartTouristApp.getContext().getResources().getString(R.string.caps_font)));
        holder.name.setText(mDataset.get(position).Name.toUpperCase());
        String imgName = "h_"+mDataset.get(position).HotelID;
        Resources resources = SmartTouristApp.getContext().getResources();
        //holder.imgHotel.setImageResource(resources.getIdentifier(imgName, "drawable", SmartTouristApp.getContext().getPackageName()));
        int resourceId = SmartTouristApp.getContext().getResources().getIdentifier(imgName, "drawable", SmartTouristApp.getContext().getPackageName());
        holder.imgHotel.setImageBitmap(TheSmartTourist.decodeSampledBitmapFromResource(resources, resourceId, 100, 100));
//        holder.imgHotel.setImageResource(R.drawable.h_20321);
//        holder.description.setText(mDataset.get(position).Description);
//        holder.review.setText(mDataset.get(position).GuestRating);
        holder.no_of_stars.setRating(Float.parseFloat(mDataset.get(position).StarRating));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void addItem(HotelInfo dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public static Drawable getDrawable(String name) {
        Context context = SmartTouristApp.getContext();
        int resourceId = context.getResources().getIdentifier(name, "drawable", SmartTouristApp.getContext().getPackageName());
        return context.getResources().getDrawable(resourceId);
    }

    public static class HotelHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgHotel;
        MyTextView name;
//        TextView description;
//        TextView review;
        RatingBar no_of_stars;

        public HotelHolder(View itemView) {
            super(itemView);
            imgHotel = (ImageView) itemView.findViewById(R.id.imgBackground);
            name = (MyTextView) itemView.findViewById(R.id.name);
//            description = (TextView) itemView.findViewById(R.id.description);
//            review = (TextView) itemView.findViewById(R.id.review);
            no_of_stars=(RatingBar) itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}