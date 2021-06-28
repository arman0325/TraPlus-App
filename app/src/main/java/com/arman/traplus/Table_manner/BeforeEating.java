package com.arman.traplus.Table_manner;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.arman.traplus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeforeEating extends AppCompatActivity {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> tableMannerList;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tm_main);
        setTitle("Before Eating");


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

        String[] Towels ={"• Some restaurants may provide towels. It is used for customer to clean their hand before they eat." + "\n"  +"\n" +
                "• Make sure the towels is wet because we only use wet towels to clean our hands "};


        String[] sitting ={"• In formal situations both men and women should kneel on a cushion" + "\n" + "\n" +
                "•In casual situations the men sit cross-legged and women sit with both legs to one side on a cushion"};




        tableMannerList = new HashMap<String, List<String>>();
        for(String group:groupList){
            if(group.equals("Towels in restaurants")){
                loadChild(Towels);
            }
            else if(group.equals("Sitting on a cushion placed")){
                loadChild(sitting);
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
        groupList.add("Towels in restaurants");
        groupList.add("Sitting on a cushion placed");
    }


}

