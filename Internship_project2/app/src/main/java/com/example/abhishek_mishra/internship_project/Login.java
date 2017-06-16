package com.example.abhishek_mishra.internship_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity {

    private CallbackManager mCallBackManager;
    LoginButton loginbutton;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        textView = (TextView)findViewById(R.id.edit_text);
        textView.setVisibility(View.INVISIBLE);
        loginbutton = (LoginButton)findViewById(R.id.fb_login);
        Profile profile = Profile.getCurrentProfile();
        if(profile!=null){
           String full_name=profile.getName();
            Intent i=new Intent(Login.this, CatalogActivity.class);
            i.putExtra("full_name",full_name);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
        mCallBackManager = CallbackManager.Factory.create();
        loginbutton.registerCallback(mCallBackManager,new FacebookCallback<LoginResult>(){

            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(Login.this,"Wait...",Toast.LENGTH_SHORT).show();
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Profile profile = Profile.getCurrentProfile();
                                    String full_name = null;
                                    if (profile != null) {

                                        full_name=profile.getName();
                                        System.out.println("NAME IS PRINTED");
                                    }

                                    long fb_id=object.getLong("id"); //use this for logout
                                    //Start new activity or use this info in your project.
                                    Intent i=new Intent(Login.this, CatalogActivity.class);
                                    i.putExtra("full_name",full_name);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    finish();
                                } catch (JSONException e) {
                                    //  e.printStackTrace();
                                }

                            }

                        });

                request.executeAsync();
            }

            @Override
            public void onCancel() {
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(FacebookException error) {
                textView.setVisibility(View.VISIBLE);
            }
        });

   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallBackManager.onActivityResult(requestCode,resultCode,data);
    }
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
}