package majorproject.amity.smarttourist.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.SmartTouristApp;
import majorproject.amity.smarttourist.models.RestaurantDetails;
import majorproject.amity.smarttourist.utils.FontCache;
import majorproject.amity.smarttourist.utils.MyTextView;
import majorproject.amity.smarttourist.utils.TheSmartTourist;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder> {
    private static MyClickListener myClickListener;
    private List<RestaurantDetails> mDataset;


    public RestaurantAdapter(List<RestaurantDetails> myDataset) {
        mDataset = myDataset;

    }

    @Override
    public RestaurantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_general, parent, false);
        RestaurantHolder dataObjectHolder = new RestaurantHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantHolder holder, int position) {
        holder.name.setTypeface(FontCache.get(SmartTouristApp.getContext().getAssets(), SmartTouristApp.getContext().getResources().getString(R.string.caps_font)));
        holder.name.setText(mDataset.get(position).getName().toUpperCase());
        String imgName = "r_"+mDataset.get(position).id;
        Resources resources = SmartTouristApp.getContext().getResources();
        int resourceId = SmartTouristApp.getContext().getResources().getIdentifier(imgName, "drawable", SmartTouristApp.getContext().getPackageName());
        holder.imageView.setImageBitmap(TheSmartTourist.decodeSampledBitmapFromResource(resources, resourceId, 100, 100));

//        holder.description.setText(mDataset.get(position).getCuisine());
//        holder.review.setText(mDataset.get(position).getLocation().address);
//        //mDataset.get(position).getLocation().latitude + mDataset.get(position).getLocation().longitude
        if(mDataset.get(position).getZomatoRating().aggregate_rating != null) {
             holder.no_of_stars.setRating(Float.parseFloat(mDataset.get(position).getZomatoRating().aggregate_rating));}
        else {
             holder.no_of_stars.setRating(0.0f); }
//        holder.name.setText(mDataset.get(position).getName());
//        holder.description.setText(mDataset.get(position).getAddress());
//        holder.review.setText(mDataset.get(position).getDetails());
//        holder.no_of_stars.setRating(mDataset.get(position).getPolarity() % 5);
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

    public void addItem(RestaurantDetails dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public static class RestaurantHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        MyTextView name;
//        TextView description;
//        TextView review;
        RatingBar no_of_stars;

        public RestaurantHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgBackground);
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