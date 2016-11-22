package majorproject.amity.smarttourist;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.security.MessageDigest;
import java.security.Signature;

import majorproject.amity.smarttourist.R;
import majorproject.amity.smarttourist.utils.FontCache;
import majorproject.amity.smarttourist.utils.MyTextView;
import majorproject.amity.smarttourist.utils.TheSmartTourist;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtUsername, txtPassword;
    Button btnLogin, btnSignUp;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    SharedPreferences mPrefs;
    ProgressBar prgBar;
    MyTextView guestLogin;
    private AccessTokenTracker tokenTracker;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = getApplicationContext().getSharedPreferences("majorproject.amity.smarttourist",MODE_PRIVATE);
        if(mPrefs.getBoolean("isLoggedIn",false)){
            startActivity(new Intent(getApplicationContext(),SplashScreen.class));
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);

        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);


        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnLogin.setOnClickListener(this);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        prgBar = (ProgressBar) findViewById(R.id.prg);
        prgBar.setVisibility(View.INVISIBLE);
        guestLogin = (MyTextView) findViewById(R.id.guest_enter);

        tokenTracker =new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        profileTracker=new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };
        tokenTracker.startTracking();
        profileTracker.startTracking();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });


        guestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TheSmartTourist.isLoggedIn = false;
                startActivity(new Intent(getApplicationContext(), SplashScreen.class));
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("login", "successful");

                AccessToken accessToken=loginResult.getAccessToken();
                Profile profile=Profile.getCurrentProfile();
                if(profile!=null){
                   TheSmartTourist.fbProfileName = profile.getName();
                }

                TheSmartTourist.isLoggedIn = true;
                mPrefs.edit().putBoolean("isLoggedIn",true);
                mPrefs.edit().putString("username",profile.getName());
                startActivity(new Intent(getApplicationContext(), SplashScreen.class));
            }

            @Override
            public void onCancel() {
                Log.d("login", "cancelled");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("login", "error");
                Toast.makeText(getApplicationContext(), "There was an error connecting to Facebook. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

        if (txtUsername.getText().toString().trim().length() > 0 && txtPassword.getText().toString().trim().length() > 0) {
            prgBar.setVisibility(View.VISIBLE);
            mPrefs.edit().putBoolean("isLoggedIn", true);
            mPrefs.edit().putString("username",txtUsername.getText().toString().trim());
            startActivity(new Intent(getApplicationContext(), SplashScreen.class));
//            String u = txtUsername.getText().toString();
//            String p = txtPassword.getText().toString();
//            JsonObject params = new JsonObject();
//            params.addProperty("Username",u);
//            params.addProperty("Password", p);
//            prgBar.setVisibility(View.VISIBLE);
//            Ion.getDefault(getApplicationContext()).configure().setLogging("MyLogs", Log.DEBUG);
//            Ion.with(this)
//                    .load("http://192.168.250.50/TheSmartTourist/Service1.svc/Login")
//                    .setJsonObjectBody(params)
//                    .asJsonObject()
//                    .setCallback(new FutureCallback<JsonObject>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonObject result) {
//                            prgBar.setVisibility(View.INVISIBLE);
//                            if (e != null) {
//                                Log.d("connection error", e.toString());
//                                Toast.makeText(getApplicationContext(), "Connection error! Check internet connection and try again.", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                            if (result.get("LoginStatus").getAsInt() == 1) {
//                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getApplicationContext(), DetailsActivity.class));
//                            } else if (result.get("LoginStatus").getAsInt() == 0) {
//                                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
        }

        else {
            Toast.makeText(LoginActivity.this,"Please enter username and password",Toast.LENGTH_SHORT).show();
        }

    }

    protected void signUp(){
        final Dialog dialog = new Dialog(this);
        //to customize the size of dialog window.. need to remove title bar
        Window window = dialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.sign_up_layout);
        final EditText email = (EditText) dialog.findViewById(R.id.signUpEmail);
        final EditText username = (EditText) dialog.findViewById(R.id.signUpUsername);
        final EditText password = (EditText) dialog.findViewById(R.id.signUpPassword);
        final EditText confirm = (EditText) dialog.findViewById(R.id.signUpConfirmPassword);
        email.setTypeface(FontCache.get(this.getAssets(), getResources().getString(R.string.app_font)));
        username.setTypeface(FontCache.get(this.getAssets(), getResources().getString(R.string.app_font)));

        Button btn1 = (Button) dialog.findViewById(R.id.signUpButton);
        btn1.setTypeface(FontCache.get(getApplicationContext().getAssets(), getResources().getString(R.string.app_font_bold)));
        dialog.show();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().trim().length() > 0 && password.getText().toString().trim().length() > 0 && confirm.getText().toString().trim().length() > 0) {
                    if(password.getText().toString().equals(confirm.getText().toString())){
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Signed Up Successfully",Toast.LENGTH_SHORT).show();
                        TheSmartTourist.fbProfileName = username.getText().toString();
                        startActivity(new Intent(getApplicationContext(), SplashScreen.class));
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Passwords don't match",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter details",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        tokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

}
