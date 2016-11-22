package majorproject.amity.smarttourist.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import majorproject.amity.smarttourist.R;

public class MyTextView extends TextView {

    public MyTextView(Context context, String font) {
        super(context);

        if (font != null) {
            setTypeface(FontCache.get(context.getAssets(), font));
        }
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        String fontName = styledAttrs.getString(R.styleable.MyTextView_typeface);
        styledAttrs.recycle();
        if (fontName != null) {
            setTypeface(FontCache.get(context.getAssets(), fontName));
        }
    }

    public void setText(String text) {
        super.setText(text);
    }

}