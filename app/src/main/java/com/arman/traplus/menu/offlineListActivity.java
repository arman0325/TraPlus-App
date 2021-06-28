package com.arman.traplus.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arman.traplus.R;

public class offlineListActivity extends AppCompatActivity {


    public String[] str = {"Sushi","Izakaya"}; // offline menu list name
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_list);

        listview = (ListView) findViewById(R.id.listOffline);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                str);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onClickListView);

    }

    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(offlineListActivity.this, offlineMenuActivity.class);

            i.putExtra("type", str[position]);
            startActivity(i);
        }
    };
}