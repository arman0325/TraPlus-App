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

public class WhileEating extends AppCompatActivity {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> tableMannerList;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tm_main);
        setTitle("During Meal:Commonly Food Type ");


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
        String[] Rice ={"• Do not pour soy sauce over white, cooked rice."
        +"\n" + "\n" +"• Hold the rice bowl in one hand and the chopsticks in the other "};

        String[] Sushi ={"• Pour some soy sauce into the small dish provided."
                +"\n"  +"\n" +"• Wasabi will already contain in sushi by chef"+ "\n" + "\n" +
                "•Hands or chopsticks can be used to eat sushi"};

        String[] Sashimi ={"• Pour some soy sauce into the small dish provided."
                +"\n" + "\n" +"• Put some wasabi on the sashimi piece, but be careful not to use too much"+ "\n"  +"\n" +
                "• Dip the sashimi pieces into the soy sauce"};


        String[] Noodles ={"• Using chopsticks, lead the noodles into your mouth."+ "\n" +"\n" + "• You can  try to copy the slurping sound. " +
                "\n" +"\n" +"• It meansto tell the chef the noodles taste good in Japan "};

        String[] Sauce ={"•  Large spoons are often provided for these dishes, instead. "+ "\n"  +"\n"+
                "• Because it is difficult to it with chopstick"};





        tableMannerList = new HashMap<String, List<String>>();
        for(String group:groupList){
            if(group.equals("Rice")) {
                loadChild(Rice);
            }

            else if(group.equals("Sushi")) {
                loadChild(Sushi);
            }

            else if(group.equals("Sashimi")) {
                loadChild(Sashimi);
            }

            else if(group.equals("Noodles")) {
                loadChild(Noodles);
            }
            else if(group.equals("Rice dishes with sauce")) {
                loadChild(Sauce);
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
        groupList.add("Rice");
        groupList.add("Sushi");
        groupList.add("Sashimi");
        groupList.add("Noodles");
        groupList.add("Rice dishes with sauce");
    }


}




