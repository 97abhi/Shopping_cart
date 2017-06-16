package com.example.abhishek_mishra.internship_project;

/**
 * Created by abhishek_mishra on 6/3/2017.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import java.util.List;
import java.util.logging.Handler;

import static android.content.ContentValues.TAG;

public class CatalogActivity extends Activity {

    private List<Product> mProductList;
    TextView welcome;
    TextView marquee;
    Button button;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog);
        //-------------------------------------------------------------
        //GETTING THE LOGIN INFORMATION TROUGH INTENT
        welcome = (TextView) findViewById(R.id.tv);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Profile profile;
        profile = Profile.getCurrentProfile();
        welcome.setText("Welcome " + profile.getFirstName() + " " + profile.getLastName());
       //---------------------------------------------------------------
        //MARQUEE TEXTVIEW AND ANIMATING IT TO BLINK

        //-----------------------------------------------------------------

        //LOGOUT BUTTON
        button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();

            }
        });

        //------------------------------------------------------------
        // OBTAIN A REFERENCE TO THE CATALOG
        mProductList = ShoppingCartHelper.getCatalog(getResources());

        // Create the list
        ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        listViewCatalog.setAdapter(new ProductAdapter(mProductList, getLayoutInflater(), false));

        listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent productDetailsIntent = new Intent(getBaseContext(),ProductDetailsActivity.class);
                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                startActivity(productDetailsIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button viewShoppingCart = (Button) findViewById(R.id.ButtonViewCart);
        viewShoppingCart.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent viewShoppingCartIntent = new Intent(getBaseContext(), ShoppingCartActivity.class);
                startActivity(viewShoppingCartIntent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });



    }

    private void confirmDialog() {
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setIcon(android.R.drawable.ic_dialog_alert);
            b.setTitle("Log Out");
            b.setMessage("Are you sure to log out!!!!");
            b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == DialogInterface.BUTTON_POSITIVE){
                        LoginManager.getInstance().logOut();
                        Intent intent = new Intent(CatalogActivity.this,Login.class);
                        startActivity(intent);
                        finish();
                    }

                }
            });
            b.setNegativeButton("NO",null);
            b.setCancelable(false);
            AlertDialog d = b.create();
            d.show();
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
//--------------------------------------------------------