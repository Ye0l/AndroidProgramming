package com.example.db01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<ListItem> listItemArrayList = new ArrayList<ListItem>();
    public CustomAdapter() {
    }

    @Override
    public int getCount() {
        return listItemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return listItemArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext() ;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        TextView textView = view.findViewById(R.id.tvItemName1);
        TextView textView2 = view.findViewById(R.id.tvItemName2);
        TextView textView3 = view.findViewById(R.id.tvItemName3);
        TextView textView4 = view.findViewById(R.id.tvItemName4);

        ListItem item = listItemArrayList.get(i);

        textView.setText(item.getItem1());
        textView2.setText(item.getItem2());
        textView3.setText(item.getItem3());
        textView4.setText(item.getItem4());

        return view;
    }

    public void addItem(String item_1, String item_2, String item_3, String item_4) {
        ListItem item = new ListItem();

        item.setItem1(item_1);
        item.setItem2(item_2);
        item.setItem3(item_3);
        item.setItem4(item_4);

        listItemArrayList.add(item);
    }

    public void clearItem() {
        listItemArrayList.clear();
    }
}
