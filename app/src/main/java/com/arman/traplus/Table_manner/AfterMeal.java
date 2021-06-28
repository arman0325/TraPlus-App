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

public class AfterMeal extends AppCompatActivity {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> tableMannerList;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tm_main);
        setTitle("After Meal");


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

        String[] Ending ={"• Need to restoring the table to the beginning." +"/n"+ "• Need to put back the chopstickes into its paper holder."+
                "\n" +"\n" + "• Some restaurant may need customers to retrieve the food container by themselves "};

        String[] Bill ={"• Some restaurant may provide a container for collect the banknote and coins." +"\n"+"\n"+
                "• If someone is not putting their cash on the container, this behaviours is not respect the staffs. "};

        String[] Tax ={"• Don't be surprised because there will be a 8% consumer tax on the bill"};







        tableMannerList = new HashMap<String, List<String>>();
        for(String group:groupList){
            if(group.equals("Ending the meal")){
                loadChild(Ending);
            }
           else if(group.equals("Paying the bill")){
                loadChild(Bill);
            }
           else if(group.equals("Consumer Tax")){
                loadChild(Tax);
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
        groupList.add("Ending the meal");
        groupList.add("Paying the bill");
        groupList.add("Consumer Tax");


    }
    }

