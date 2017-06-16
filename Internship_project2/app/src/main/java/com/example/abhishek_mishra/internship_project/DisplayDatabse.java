package com.example.abhishek_mishra.internship_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DisplayDatabse extends AppCompatActivity {
    Button display;
    SQLiteDatabase db;
    TextView header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_databse);
        display = (Button)findViewById(R.id.button3);

        db = openOrCreateDatabase("shoppingProject",MODE_PRIVATE,null);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(cal.getTime());
                    String q = "select * from shoppingDetails";
                    String sum = "select sum(price) as TOTAL_COST from shoppingDetails where date = '"+ formattedDate + "'" ;
                    Cursor c;
                     c = db.rawQuery(q, null);
                    String o = "";
                    if (c != null) {
                        while (c.moveToNext()) {
                            String date = c.getString(0);
                            String name = c.getString(1);
                            String price = c.getString(2);

                            o = o + "\n" + date +"\t\t\t\t\t\t"+ name + "\t\t\t\t\t\t" + price + "\t\t\t\t\t\t";

                        }
                        c = db.rawQuery(sum, null);
                        while (c.moveToNext()) {
                            String total = c.getString(0);
                            o = o + total + "\n";
                        }
                        o = o + "\n" + "--------------------------------------------------------";


                        myDialog(o);

                    }
                c.close();
            }
        });
    }
    void myDialog(String o){
        final String msg = o;
        setContentView(R.layout.database_details);
        Button b = (Button)findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(DisplayDatabse.this,ShoppingCartActivity.class);
                        startActivity(intent);
                        finish();

            }
        });
        Button exit = (Button)findViewById(R.id.button6);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               confirmDialog();

            }
        });
        header = (TextView)findViewById(R.id.textView6);
        header.setText("Date" + "\t\t\t\t\t\t" + "Item" + "\t\t\t\t\t\t" + "Price" + "\t\t\t\t\t\t" + "Total");
        TextView textView = (TextView)findViewById(R.id.textView9);
        textView.setText(msg);
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
                    Intent intent = new Intent(DisplayDatabse.this,End.class);
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
}
