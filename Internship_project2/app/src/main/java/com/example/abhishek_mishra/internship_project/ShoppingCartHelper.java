package com.example.abhishek_mishra.internship_project;

/**
 * Created by abhishek_mishra on 6/3/2017.
 */

import android.content.res.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ShoppingCartHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Product> catalog;
    private static Map<Product, ShoppingCartEntry> cartMap = new HashMap<Product, ShoppingCartEntry>();

    public static List<Product> getCatalog(Resources res){
        if(catalog == null) {
            catalog = new Vector<Product>();
            catalog.add(new Product("brocolli", res
                    .getDrawable(R.drawable.brocoli),
                    "", 430));
            catalog.add(new Product("carrots", res
                    .getDrawable(R.drawable.carrot),
                    "", 40));
            catalog.add(new Product("Cauliflower", res
                    .getDrawable(R.drawable.cauliflower),
                    "", 48));
            catalog.add(new Product("cucumber", res
                    .getDrawable(R.drawable.cucumber),
                    "", 35));
            catalog.add(new Product("lettuce", res
                    .getDrawable(R.drawable.lettuce),
                    "", 240));
            catalog.add(new Product("corn", res
                    .getDrawable(R.drawable.corn),
                    "", 62));
            catalog.add(new Product("potatoes", res
                    .getDrawable(R.drawable.potatoes),
                    "", 18));
            catalog.add(new Product("onions", res
                    .getDrawable(R.drawable.onion),
                    "", 12));
            catalog.add(new Product("Mushroom", res
                    .getDrawable(R.drawable.mushroom),
                    "", 650));
            catalog.add(new Product("Spinach", res
                    .getDrawable(R.drawable.spinach),
                    "", 30));
            catalog.add(new Product("Squash", res
                    .getDrawable(R.drawable.squash),
                    "", 200));
            catalog.add(new Product("Zucchini", res
                    .getDrawable(R.drawable.zucchini),
                    "", 290));
            catalog.add(new Product("Tomatoes", res
                    .getDrawable(R.drawable.tomatoes),
                    "", 20));
            catalog.add(new Product("Apple", res
                    .getDrawable(R.drawable.apple),
                    "", 200));
            catalog.add(new Product("Oranges", res
                    .getDrawable(R.drawable.oranges),
                    "", 65));
            catalog.add(new Product("Bananas", res
                    .getDrawable(R.drawable.banana),
                    "", 50));
            catalog.add(new Product("Grapes", res
                    .getDrawable(R.drawable.grapes),
                    "", 100));
            catalog.add(new Product("Melon", res
                    .getDrawable(R.drawable.melon),
                    "", 65));


            catalog.add(new Product("Lemon", res
                    .getDrawable(R.drawable.lemon),
                    "", 120));
            catalog.add(new Product("Cheese", res
                    .getDrawable(R.drawable.cheese),
                    "", 400));
            catalog.add(new Product("Olive oil", res
                    .getDrawable(R.drawable.olive_oil),
                    "", 600));
            catalog.add(new Product("rice", res
                    .getDrawable(R.drawable.rice),
                    "", 52));
            catalog.add(new Product("Sugar", res
                    .getDrawable(R.drawable.sugar),
                    "", 40));
            catalog.add(new Product("Tea", res
                    .getDrawable(R.drawable.tea),
                    "", 450));
            catalog.add(new Product("Vegetable Oil", res
                    .getDrawable(R.drawable.vegetable_oil),
                    "", 125));
            catalog.add(new Product("Milk", res
                    .getDrawable(R.drawable.milk),
                    "", 42));
            catalog.add(new Product("Butter", res
                    .getDrawable(R.drawable.butter),
                    "", 400));

            catalog.add(new Product("Ghee", res
                    .getDrawable(R.drawable.ghee),
                    "", 560));
            catalog.add(new Product("Whipped Cream", res
                    .getDrawable(R.drawable.whipped_cream),
                    "", 700));
            catalog.add(new Product("Sour Cream", res
                    .getDrawable(R.drawable.sour_cream),
                    "", 350));
            catalog.add(new Product("Yogurt", res
                    .getDrawable(R.drawable.yogurt),
                    "", 120));


        }

        return catalog;
    }

    public static void setQuantity(Product product, int quantity) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        // If the quantity is zero or less, remove the products
        if(quantity <= 0) {
            if(curEntry != null)
                removeProduct(product);
            return;
        }

        // If a current cart entry doesn't exist, create one
        if(curEntry == null) {
            curEntry = new ShoppingCartEntry(product, quantity);
            cartMap.put(product, curEntry);
            return;
        }

        // Update the quantity
        curEntry.setQuantity(quantity);
    }

    public static int getProductQuantity(Product product) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        if(curEntry != null)
            return curEntry.getQuantity();

        return 0;
    }

    public static void removeProduct(Product product) {
        cartMap.remove(product);
    }

    public static List<Product> getCartList() {
        List<Product> cartList = new Vector<Product>(
                cartMap.keySet().size());
        for(Product p : cartMap.keySet()) {
            cartList.add(p);
        }

        return cartList;
    }


}
