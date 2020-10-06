package com.webapp.aisha.utils.view_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.webapp.aisha.R;
import com.webapp.aisha.models.City;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {

    ArrayList<City> items;
    Context context;

    public SpinnerAdapter(Context context, ArrayList<City> strings) {
        this.context = context;
        items = strings;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_spinner, null);
        TextView textView = view.findViewById(R.id.tv_text);
        textView.setText(items.get(position).getName());
        return view;
    }
}
