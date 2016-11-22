package majorproject.amity.smarttourist.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import majorproject.amity.smarttourist.SmartTouristApp;
import majorproject.amity.smarttourist.utils.TheSmartTourist;

/**
 * Created by vijim_000 on 6/24/2016.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    ArrayList<String> itemList = new ArrayList<String>();

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public void add(String path){
        itemList.add(path);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(220, 220));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap bm = decodeSampledBitmapFromUri(itemList.get(position), 220, 220);

        imageView.setImageBitmap(bm);
        return imageView;
    }

    public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {

        Resources resources = SmartTouristApp.getContext().getResources();
        //holder.imgHotel.setImageResource(resources.getIdentifier(imgName, "drawable", SmartTouristApp.getContext().getPackageName()));
        int resourceId = SmartTouristApp.getContext().getResources().getIdentifier(path, "drawable", SmartTouristApp.getContext().getPackageName());
        return TheSmartTourist.decodeSampledBitmapFromResource(resources, resourceId, reqWidth, reqHeight);
    }

}