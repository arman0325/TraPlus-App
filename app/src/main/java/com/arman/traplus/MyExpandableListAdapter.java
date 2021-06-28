package com.arman.traplus;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

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
       if(view==null){
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view = inflater.inflate(R.layout.tm_child_item, null);

       }
        TextView item = view.findViewById(R.id.tm_child);
       item.setText(childname);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
