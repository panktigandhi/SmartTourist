package majorproject.amity.smarttourist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import majorproject.amity.smarttourist.utils.MyTextView;

public class TextInfoActivity extends AppCompatActivity {

    int type, value; MyTextView text, header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        type = getIntent().getIntExtra("type",0);
        value = getIntent().getIntExtra("value",0);

        text = (MyTextView) findViewById(R.id.txtView);
        header = (MyTextView) findViewById(R.id.txtViewHeader);
        switch(type){
            case 1: showNews(); header.setVisibility(View.VISIBLE);break;
            case 2: showGeneral(); header.setVisibility(View.INVISIBLE); break;
            case 3: showPractical(); header.setVisibility(View.INVISIBLE); break;
        }
    }

    public void showNews(){
        switch(value){
            case 1:
                header.setText(getResources().getString(R.string.news_1_heading));
                text.setText(getResources().getString(R.string.news_1)); break;
            case 2:
                header.setText(getResources().getString(R.string.news_2_heading));
                text.setText(getResources().getString(R.string.news_2)); break;
            case 3:
                header.setText(getResources().getString(R.string.news_3_heading));
                text.setText(getResources().getString(R.string.news_3)); break;
            case 4:
                header.setText(getResources().getString(R.string.news_4_heading));
                text.setText(getResources().getString(R.string.news_4)); break;
            case 5:
                header.setText(getResources().getString(R.string.news_5_heading));
                text.setText(getResources().getString(R.string.news_5)); break;
        }
    }

    public void showGeneral(){
        switch(value){
            case 0: text.setText(getResources().getString(R.string.general_info_intro)); break;
            case 1: text.setText(getResources().getString(R.string.general_info_etymology)); break;
            case 2: text.setText(getResources().getString(R.string.general_info_geography)); break;
            case 3: text.setText(getResources().getString(R.string.general_info_climate)); break;
            case 4: text.setText(getResources().getString(R.string.general_info_population)); break;
            case 5: text.setText(getResources().getString(R.string.general_info_culture)); break;
        }
    }

    public void showPractical(){
        switch(value){
            case 0: text.setText(getResources().getString(R.string.practical_info_getting_around)); break;
            case 1: text.setText(getResources().getString(R.string.practical_info_dress_code)); break;
            case 2: text.setText(getResources().getString(R.string.practical_info_good_to_know)); break;
        }
    }
}
