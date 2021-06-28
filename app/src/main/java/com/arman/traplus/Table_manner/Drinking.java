package com.arman.traplus.Table_manner;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arman.traplus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Drinking extends AppCompatActivity {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> tableMannerList;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tm_main);
        setTitle("Drinking");


        createGroupList();
        createChildList();
        expandableListView =findViewById(R.id.expandableListView);
        expandableListAdapter=new MyExpandableListAdapter(this, groupList, tableMannerList);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition =- 1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i !=lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });

    }

    private void createChildList() {

        String[] Alcoholic ={"• It is customary to serve each other rather than pour your own drink." + "\n"  + "\n"+ "• Don't pour too much for your drink"};

        String[] Tea ={"• In a traditional tea shop, need to be silence" + "\n" + "\n" + "• Need to remove shoes upon entering and greet guests with a slight bow. "
        + "\n"  + "\n" +"• Do not talk or shake hands, and instead be seated silently "};





        tableMannerList = new HashMap<String, List<String>>();
        for(String group:groupList){
            if(group.equals("Drinking alcoholic")){
                loadChild(Alcoholic);
            }
            else if(group.equals("Tea ceremonies (In a traditional tea shop")){
                loadChild(Tea);
            }


            tableMannerList.put(group, childList);

        }


    }

    private void loadChild(String[] tableMannerList) {
        childList= new ArrayList<String>();
        for(String manner: tableMannerList){
            childList.add(manner);

        }
    }

    private void createGroupList() {
        groupList= new ArrayList<>();
        groupList.add("Drinking alcoholic");
        groupList.add("Tea ceremonies (In a traditional tea shop)");
    }
    }

