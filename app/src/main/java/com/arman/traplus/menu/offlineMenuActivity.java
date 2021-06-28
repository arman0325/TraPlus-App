package com.arman.traplus.menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arman.traplus.FoodData.Food;
import com.arman.traplus.FoodData.Izakaya;
import com.arman.traplus.FoodData.Sushi;
import com.arman.traplus.R;
import com.arman.traplus.translate;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class offlineMenuActivity extends AppCompatActivity {

    private Menu menu;
    private ItemAdapter adapter;
    private SharedPreferences prefs;
    private Resources res;
    private String menuStr;
    private int menuItemNum = 0;
    private boolean firstItem = true;
    private ArrayList<Food> FoodItems;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle(R.string.offlineMenuTitle);

        type = getIntent().getStringExtra("type");
        menuBuild();
    }

    private void menuBuild(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResources();

        if (type.equals("Sushi")){
            menuCase("Sushi-sushi");
            menuCase("Sushi-Ingredients");
            menuCase("Sushi-Special");
        } else {
            menuCase("Izakaya-meat");
            menuCase("Izakaya-fired");
            menuCase("Izakaya-other");
        }



        RecyclerView recyclerView = findViewById(R.id.product_recycler_view);
        adapter = new ItemAdapter(this, menu);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        changeFontSize();
        adapter.notifyDataSetChanged();
    }

    public void menuCase(String selectMenuName){
        switch (selectMenuName){
            case "Sushi-sushi":
                Sushi sushi = new Sushi();
                FoodItems = sushi.getSushi();
                createMenuItemDefault(FoodItems);
                break;
            case "Sushi-Ingredients":
                sushi = new Sushi();
                FoodItems = sushi.getIngredients();
                createMenuItemDefault(FoodItems);
                break;
            case "Sushi-Special":
                sushi = new Sushi();
                FoodItems = sushi.getSpecial();
                createMenuItemDefault(FoodItems);
                break;
            case "Izakaya-meat":
                Izakaya izakaya = new Izakaya();
                FoodItems = izakaya.getMeatDishes();
                createMenuItemDefault(FoodItems);
                break;
            case "Izakaya-fired":
                izakaya = new Izakaya();
                FoodItems = izakaya.getFriedFoods();
                createMenuItemDefault(FoodItems);
                break;
            case "Izakaya-other":
                izakaya = new Izakaya();
                FoodItems = izakaya.getOtherFoods();
                createMenuItemDefault(FoodItems);
                break;

        }

    }

    // create the menu item for default data that do not translate the words
    public void createMenuItemDefault(ArrayList<Food> Foods) {
        if(firstItem){
            Collections.reverse(Arrays.asList(Foods));
            menu = new Menu();
            menuItemNum = Foods.size();
            for (int i = 0; i < Foods.size(); i++) {
                //translate trans = new translate();
                //String zhNames = trans.translateMethod(jaNames[i], "ja", "zh-TW");
                menu.addItem("p" + i, Foods.get(i).getZhName(), Foods.get(i).getJaName(), 0);
            }
            firstItem = false;
        } else {
            for (int i = 0; i < Foods.size(); i++) {
                menu.addItem("p" + (menuItemNum+i), Foods.get(i).getZhName(), Foods.get(i).getJaName(), 0);
            }
            menuItemNum += Foods.size() + 1;
        }
    }

    // for ocr to get word that need translate to add to menu item
    public void createMenuNewItem(String Foods) {
        String[] jaNames = Foods.split("\n");
        for (int i = 0; i < jaNames.length; i++) {
            if (jaNames[i].trim().isEmpty() || jaNames[0]==null || jaNames[0] == ""){
            }else {
                translate trans = new translate();
                String zhNames = trans.translateMethod(jaNames[i], "ja", "zh-TW");
                menu.addItem("p" + (menuItemNum+i), zhNames, jaNames[i], 0);
            }
        }
        menuItemNum += jaNames.length;
    }


    // Button click event handling for showing summary of cart
    public void viewCart(View button) {
        menu.toggleView();
        adapter.notifyDataSetChanged();
        if (menu.getViewAllProduct()) {
            ((TextView) findViewById(R.id.cart_textView)).setText("Cart ");
            ((Button) findViewById(R.id.view_cart)).setText("View Cart");

            findViewById(R.id.remove_all).setVisibility(View.VISIBLE);
        } else {
            ((Button) findViewById(R.id.view_cart)).setText("View All Product");
            findViewById(R.id.remove_all).setVisibility(View.INVISIBLE);
        }
    }

    // Button click event handling for removing all item from cart
    public void removeAll(View button) {
        menu.removeAllItemFromCart();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(offlineMenuActivity.this, menuPreferenceActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                new AlertDialog.Builder(this)
                        .setTitle("Menu Cart")
                        .setMessage("You can put menu item into the cart.")
                        .setNeutralButton(android.R.string.ok, null)
                        .show();
                break;
            case R.id.addPhoto:
                if (isNetworkConnected()) {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(this);
                    break;
                }else{
                    Toast.makeText(this, "Not network turned on\nPlease check your network", Toast.LENGTH_LONG).show();
                }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //open the crop image activity
        Log.d("TAG", "onActivityResult: " + CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Intent i = new Intent(offlineMenuActivity.this, AddNewItemActivity.class);
                i.putExtra("uri", resultUri.toString());
                i.putExtra("className","offlineMenuActivity");

                startActivityForResult(i, 1);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        // for get return ocr string to translate add to the menu
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                menuStr = data.getStringExtra("menuString");
                Log.d("TAG", "onActivityResult: " + menuStr);
                createMenuNewItem(menuStr);
            }
        }


    }

    // Change font size
    public void changeFontSize() {
        int fontSize = Integer.parseInt(prefs.getString(res.getString(R.string.font_size_key), res.getString(R.string.font_size_default)));
        adapter.setFontSize(fontSize);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        changeFontSize();
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}