package com.example.abhishek_mishra.internship_project;

/**
 * Created by abhishek_mishra on 6/3/2017.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ShoppingCartActivity extends Activity {
        //---------------------------------------------

    private List<Product> mCartList;
    private ProductAdapter mProductAdapter;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);
        //-------------------------------------------------
        //SETTING UP THE EXIT AND BACK BUTT AND OPENING THE DATABASE
        Button back = (Button)findViewById(R.id.back);
        Button exit = (Button)findViewById(R.id.exit);
        Button proceedToCheckout = (Button)findViewById(R.id.Button02);
        db = openOrCreateDatabase("shoppingProject",MODE_PRIVATE,null);
        //--------------------------------------------------
        //--------------------------------------------------

        mCartList = ShoppingCartHelper.getCartList();
        //  to clear the selections
        for(int i=0; i<mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }


        // Create the list
        final ListView listViewCatalog = (ListView) findViewById(R.id.ListViewCatalog);
        mProductAdapter = new ProductAdapter(mCartList, getLayoutInflater(), true);
        listViewCatalog.setAdapter(mProductAdapter);

        listViewCatalog.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent productDetailsIntent = new Intent(getBaseContext(),ProductDetailsActivity.class);
                productDetailsIntent.putExtra(ShoppingCartHelper.PRODUCT_INDEX, position);
                startActivity(productDetailsIntent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(),CatalogActivity.class);
                startActivity(intent);
                finish();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
        proceedToCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q = "create table if not exists shoppingDetails( Date date,name varchar,price number(4,4))";
                db.execSQL(q);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(cal.getTime());
                for(int i=0; i<mCartList.size(); i++) {
                    String q1 = "insert into shoppingDetails values('"+formattedDate+"','"+mCartList.get(i).title+"','"+mCartList.get(i).price+"')";
                db.execSQL(q1);
                }
                Intent intent = new Intent(ShoppingCartActivity.this,DisplayDatabse.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the data
        if(mProductAdapter != null) {
            mProductAdapter.notifyDataSetChanged();
        }

        double subTotal = 0;
        for(Product p : mCartList) {
            int quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += p.price * quantity;
        }

        TextView productPriceTextView = (TextView) findViewById(R.id.TextViewSubtotal);
        productPriceTextView.setText("Subtotal: Rs" + subTotal);
    }
    private void confirmDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setIcon(android.R.drawable.ic_delete);
        b.setTitle("EXIT");
        b.setMessage("Are you sure you want to exit!!!!");
        b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    finish();
                }

            }
        });
        b.setNegativeButton("NO",null);
        b.setCancelable(false);
        AlertDialog d = b.create();
        d.show();
    }

}