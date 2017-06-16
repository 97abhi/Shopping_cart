package com.example.abhishek_mishra.internship_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;

import java.util.List;

public class ProductDetailsActivity extends Activity {
    TextView welcome;
    Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetails);
        //-------------------------------------------------
        //MARQUEE ELEMENT AND TO BLINK IT

        //-------------------------------------------------
        //TO TAKE THE CONTENTS OF THE CURRENT PROFILE
        welcome = (TextView)findViewById(R.id.tv);
        exit = (Button)findViewById(R.id.exit);
        Profile profile;
        profile = Profile.getCurrentProfile();
        welcome.setText("Welcome " + profile.getFirstName() + " " + profile.getLastName());
        //--------------------------------------------------
        //CREATING A LIST
        List<Product> catalog = ShoppingCartHelper.getCatalog(getResources());

        int productIndex = getIntent().getExtras().getInt(
                ShoppingCartHelper.PRODUCT_INDEX);
        final Product selectedProduct = catalog.get(productIndex);
        //-----------------------------------------------------
        // SET THE PROPER IMAGE AND TEXT
        ImageView productImageView = (ImageView) findViewById(R.id.ImageViewProduct);
        productImageView.setImageDrawable(selectedProduct.productImage);

        String title = selectedProduct.title;

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewProductPrice);
        productPriceTextView.setText("Rs" + selectedProduct.price);

        // UPDATE THE CURRENT QUANTITY IN THE CART
        TextView textViewCurrentQuantity = (TextView) findViewById(R.id.textViewCurrentlyInCart);
        textViewCurrentQuantity.setText("Currently in Cart: " + ShoppingCartHelper.getProductQuantity(selectedProduct));

        // SAVE A REFERENCE TO THE QUANTITY EDIT TEXT
        final EditText editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);

        Button addToCartButton = (Button) findViewById(R.id.ButtonAddToCart);
        addToCartButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // CHECK TO SEE A VALID QUANTITY IS ENTERED OR NOT
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(editTextQuantity.getText()
                            .toString());

                    if (quantity < 0) {
                        Toast.makeText(getBaseContext(),
                                "Please enter a quantity of 0 or higher",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (Exception e) {
                    Toast.makeText(getBaseContext(),
                            "Please enter a numeric quantity",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                // IF WE MAKE IT HERE, A VALID QUANTITY WAS ENTERED
                ShoppingCartHelper.setQuantity(selectedProduct, quantity);

                // CLOSE THE ACTIVITY
                finish();
            }
        });
        exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}