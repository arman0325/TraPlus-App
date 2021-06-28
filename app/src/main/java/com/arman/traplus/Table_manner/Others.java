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

public class Others extends AppCompatActivity {

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> tableMannerList;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tm_main);
        setTitle("Others");

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

        String[] Soya ={"• Pour the amount of soya sauce you will use into the shallow "+ "\n" + "\n" +
                "• Don't pouring too much"};

        String[] Wasabi ={"• Do not mix with Soya sauce"+ "\n" + "\n" +
                "• Should put it on the sushi"};

        String[] Shells ={"• Should place the empty shells on a empty plate or a bowl"};


        tableMannerList = new HashMap<String, List<String>>();
        for(String group:groupList){
            if(group.equals("Soya sauce dish")){
                loadChild(Soya);
            }
            else if(group.equals("Wasabi(During eating sushi)"))
                loadChild(Wasabi);
            else if(group.equals("Clam shells"))
                loadChild(Shells);


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
        groupList.add("Soya sauce dish");
        groupList.add("Wasabi(During eating sushi)");
        groupList.add("Clam shells");

    }

    }



