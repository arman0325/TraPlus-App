package com.arman.traplus.Table_manner;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arman.traplus.R;

import java.util.List;
import java.util.Map;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

   private Context context;
   private Map<String, List<String>> tableMannerList;
   private List<String> groupList;

   public MyExpandableListAdapter(Context context, List<String> groupList,
                                  Map<String, List<String>> tableMannerList){

       this.context=context;
       this.groupList= groupList;
       this.tableMannerList= tableMannerList;


   }

    @Override
    public int getGroupCount() {
        return tableMannerList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return tableMannerList.get(groupList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int il) {
        return tableMannerList.get(groupList.get(i)).get(il);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int il) {
        return il;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String groupname =getGroup(i).toString();
        if(view== null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tm_group_item, null);
        }
        TextView item = view.findViewById(R.id.tm_group);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(groupname);
       return view;
    }

    @Override
    public View getChildView(int i, int il, boolean b, View view, ViewGroup viewGroup) {
       String childname= getChild(i , il).toString();
       String groupname=getGroup(i).toString();
       if(view==null){
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view = inflater.inflate(R.layout.tm_child_item, null);

       }
        TextView item = view.findViewById(R.id.tm_child);
        ImageView image1 = view.findViewById(R.id.tm_image1);
        ImageView image2 = view.findViewById(R.id.tm_image2);
       item.setText(childname);
       if(groupname=="Towels in restaurants"){

                image1.setImageResource(R.drawable.wet_towel_);

       }
       else if(groupname=="Sitting on a cushion placed"){

           image1.setImageResource(R.drawable.sitting);
       }

       else if(groupname=="Rice"){
           image1.setImageResource(R.drawable.rice);
           image2.setImageResource(R.drawable.rice2);
       }
       else if(groupname=="Sushi"){
           image1.setImageResource(R.drawable.sushi);
           image2.setImageResource(0);
       }
       else if(groupname=="Sashimi"){
           image1.setImageResource(R.drawable.sashimi);
           image2.setImageResource(0);
       }
       else if(groupname=="Noodles"){
           image1.setImageResource(R.drawable.noodles);
           image2.setImageResource(0);
       }
       else if(groupname=="Rice dishes with sauce"){
           image1.setImageResource(R.drawable.sauce);
           image2.setImageResource(0);
       }

       else if(groupname=="Rice dishes with sauce"){
           image1.setImageResource(R.drawable.sauce);
           image2.setImageResource(0);
       }

       else if(groupname=="Drinking alcoholic"){
           image1.setImageResource(R.drawable.alcoholic);
           image2.setImageResource(0);
       }

       else if(groupname=="Tea ceremonies (In a traditional tea shop)"){
           image1.setImageResource(R.drawable.tea);
           image2.setImageResource(0);
       }

       else if(groupname=="Ending the meal"){
           image1.setImageResource(R.drawable.restore);
           image2.setImageResource(0);
       }

       else if(groupname=="Paying the bill"){
           image1.setImageResource(R.drawable.paying_);
           image2.setImageResource(0);
       }
       else if(groupname=="Consumer Tax"){
           image1.setImageResource(R.drawable.tax);
           image2.setImageResource(0);
       }
       else if(groupname=="Soya sauce dish"){
           image1.setImageResource(R.drawable.soya_sauce_dish);
           image2.setImageResource(0);
       }
       else if(groupname=="Wasabi(During eating sushi)"){
           image1.setImageResource(R.drawable.wasabi);
           image2.setImageResource(0);
       }
       else if(groupname=="Clam shells"){
           image1.setImageResource(R.drawable.shell);
           image2.setImageResource(0);
       }







        return view;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
