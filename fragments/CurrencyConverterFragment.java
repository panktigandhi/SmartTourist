package majorproject.amity.smarttourist.fragments;


import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import majorproject.amity.smarttourist.SmartTouristApp;
import majorproject.amity.smarttourist.models.Currency;
import majorproject.amity.smarttourist.utils.FontCache;
import majorproject.amity.smarttourist.utils.MyTextView;

import majorproject.amity.smarttourist.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyConverterFragment extends Fragment {

    MyTextView from,to;MyTextView fromName, toName;
    Spinner fromSpinner, toSpinner;
    HashMap<String,String> currencyTypes = new HashMap<>();
    List<String> categories = new ArrayList<String>();
    Button one,two,three,four,five,six,seven,eight,nine,zero,dot;
    ImageButton backspace, convert, flip;
    String alertResponse;
    String fromCurrency = "INR";
    String toCurrency = "AED";
    ImageButton btnFromFlag, btnToFlag;

    public CurrencyConverterFragment() {
        // Required empty public constructor
    }

    public EventsFragment getInstance() {
        return new EventsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_currency_converter, container, false);

        from = (MyTextView) v.findViewById(R.id.labelFromValue);
        to = (MyTextView) v.findViewById(R.id.labelToValue);

        fromName = (MyTextView) v.findViewById(R.id.textFrom);
        toName = (MyTextView) v.findViewById(R.id.textTo);
        one = (Button) v.findViewById(R.id.one);
        two = (Button) v.findViewById(R.id.two);
        three = (Button) v.findViewById(R.id.three);
        four = (Button) v.findViewById(R.id.four);
        five = (Button) v.findViewById(R.id.five);
        six = (Button) v.findViewById(R.id.six);
        seven = (Button) v.findViewById(R.id.seven);
        eight = (Button) v.findViewById(R.id.eight);
        nine = (Button) v.findViewById(R.id.nine);
        zero = (Button) v.findViewById(R.id.zero);
        dot = (Button) v.findViewById(R.id.dot);
        backspace = (ImageButton) v.findViewById(R.id.backspace);
        convert = (ImageButton) v.findViewById(R.id.convert);
        flip = (ImageButton) v.findViewById(R.id.flip);
        btnFromFlag = (ImageButton) v.findViewById(R.id.btnFromFlag);
        btnToFlag = (ImageButton) v.findViewById(R.id.btnToFlag);
        createList();
        setListeners();
        setFont();
        return v;
    }

    void setListeners(){
        btnFromFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCurrency(view);
            }
        });
        btnToFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCurrency(view);
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberPressed(view);
            }
        });
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCurrencies();
            }
        });
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backspacePressed(view);
            }
        });
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convert();
            }
        });
    }

    void createList(){
        // Spinner Drop down elements
        categories.add("UAE Dirham"); currencyTypes.put("UAE Dirham","AED");
        categories.add("Australian Dollar"); currencyTypes.put("Australian Dollar","AUD");
        categories.add("Euro"); currencyTypes.put("Euro","EUR");
        categories.add("Indian Rupee"); currencyTypes.put("Indian Rupee","INR");
        categories.add("Omani Rial"); currencyTypes.put("Omani Rial","OMR");
        categories.add("Philippine Peso"); currencyTypes.put("Philippine Peso","PHP");
        categories.add("Pakistani Rupee"); currencyTypes.put("Pakistani Rupee","PKR");
        categories.add("Saudi Riyal"); currencyTypes.put("Saudi Riyal","SAR");
        categories.add("Singapore Dollar"); currencyTypes.put("Singapore Dollar","SGD");
        categories.add("United States Dollar"); currencyTypes.put("United States Dollar","USD");
    }

    void setFont(){
        one.setTypeface(FontCache.get(getActivity().getAssets(), getResources().getString(R.string.app_font)));
        two.setTypeface(FontCache.get(getActivity().getAssets(), getResources().getString(R.string.app_font)));
        three.setTypeface(FontCache.get(getActivity().getAssets(), getResources().getString(R.string.app_font)));
        four.setTypeface(FontCache.get(getActivity().getAssets(), getResources().getString(R.string.app_font)));
        five.setTypeface(FontCache.get(getActivity().getAssets(), getResources().getString(R.string.app_font)));
        six.setTypeface(FontCache.get(getActivity().getAssets(), getResources().getString(R.string.app_font)));
        seven.setTypeface(FontCache.get(getActivity().getAssets(), getResources().getString(R.string.app_font)));
        eight.setTypeface(FontCache.get(getActivity().getAssets(), getResources().getString(R.string.app_font)));
        nine.setTypeface(FontCache.get(getActivity().getAssets(), getResources().getString(R.string.app_font)));
        zero.setTypeface(FontCache.get(getActivity().getAssets(), getResources().getString(R.string.app_font)));
        dot.setTypeface(FontCache.get(getActivity().getAssets(), getResources().getString(R.string.app_font)));
    }

    public void changeCurrency(View v){
        ImageButton b = (ImageButton) v;
        ShowAlertDialogWithListview(b);

    }

    public void convert() {
        if (from.getText().toString().trim().length()>0){
//            final String i1 = currencyTypes.get(fromSpinner.getSelectedItem());
//            final String i2 = currencyTypes.get(toSpinner.getSelectedItem());
            Log.d("convert","http://www.apilayer.net/api/live?access_key=9d389620d77e4060ef4e14ac5fcdee12&currencies="+fromCurrency+","+toCurrency);
            Ion.with(this).load("http://www.apilayer.net/api/live?access_key=9d389620d77e4060ef4e14ac5fcdee12&currencies="+fromCurrency+","+toCurrency)
                    .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {
                    if(e != null){
                        Log.d("currency","Error occurred"+e.toString());
                    }
                    else {
                        Log.d("currency",result.toString());
                        Currency c = (Currency) new Gson().fromJson(result.toString(), new TypeToken<Currency>() {}.getType());
                        String value = fromCurrency+toCurrency;
                        Log.d("currency",getExchangeRate(c,"USD"+fromCurrency)+","+getExchangeRate(c,"USD"+toCurrency));
                        float rate = getExchangeRate(c,"USD"+toCurrency) / getExchangeRate(c,"USD"+fromCurrency) ;
                        float currency = Float.parseFloat(from.getText().toString());
                        to.setText(""+rate*currency);
                    }
                }
            });
        }
    }

    public void numberPressed(View v) {
        Button b = (Button)v;
        from.append(b.getText().toString());
    }

    public void backspacePressed(View v) {
        String newText = removeLastChar(from.getText().toString());
        from.setText(newText);
    }

    public void flipCurrencies(){
        String temp = fromName.getText().toString();
        String temp1 = toName.getText().toString();
        fromName.setText(temp1); toName.setText(temp);
        fromCurrency = temp1; toCurrency = temp;

        temp = from.getText().toString();
        temp1 = to.getText().toString();
        from.setText(temp1); to.setText(temp);

        int resourceId = SmartTouristApp.getContext().getResources().getIdentifier(currencyTypes.get(fromCurrency).toLowerCase(), "drawable", SmartTouristApp.getContext().getPackageName());
        btnFromFlag.setImageResource(resourceId);

        resourceId = SmartTouristApp.getContext().getResources().getIdentifier(currencyTypes.get(toCurrency).toLowerCase(), "drawable", SmartTouristApp.getContext().getPackageName());
        btnToFlag.setImageResource(resourceId);

//        Drawable i = btnFromFlag.getBackground();
//        btnFromFlag.setBackground(btnToFlag.getDrawable());
//        btnToFlag.setBackground(i);
        //ShowAlertDialogWithListview();
    }

    float getExchangeRate(Currency c, String value){
        switch(value) {
            case "USDUSD": return c.quotes.USDUSD;
            case "USDAED": return c.quotes.USDAED;
            case "USDAUD": return c.quotes.USDAUD;
            case "USDSAR": return c.quotes.USDSAR;
            case "USDINR": return c.quotes.USDINR;
            case "USDPHP": return c.quotes.USDPHP;
            case "USDPKR": return c.quotes.USDPKR;
            case "USDEUR": return c.quotes.USDEUR;
            case "USDOMR": return c.quotes.USDOMR;
            case "USDSGD": return c.quotes.USDSGD;
            default: return -1;
        }
    }

    public String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1);
    }

    public void ShowAlertDialogWithListview(final ImageButton b)
    {
        //Create sequence of items
        final CharSequence[] mCurrency = categories.toArray(new String[categories.size()]);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Currencies");
        dialogBuilder.setItems(mCurrency, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String a = mCurrency[item].toString();  //Selected item in listview
                alertResponse = a;
                Log.d("convert",currencyTypes.get(a));
                int r1 = SmartTouristApp.getContext().getResources().getIdentifier(currencyTypes.get(a).toLowerCase(), "drawable", SmartTouristApp.getContext().getPackageName());
                Log.d("convert",a);
                if(b.getTag().equals("1")){
                    Log.d("convert","from flag pressed");
                    fromName.setText(a);
                    btnFromFlag.setImageResource(r1);
                    fromCurrency = currencyTypes.get(a);
                }
                else if (b.getTag().equals("2")){
                    Log.d("convert","to flag pressed");
                    toName.setText(a);
                    btnToFlag.setImageResource(r1);
                    toCurrency = currencyTypes.get(a);
                }
                convert();
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
        //return alertResponse;
    }

}
