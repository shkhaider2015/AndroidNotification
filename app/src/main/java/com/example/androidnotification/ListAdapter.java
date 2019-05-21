package com.example.androidnotification;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private static final String TAG = "ListAdapter";
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Users> usersArrayList;

    public ListAdapter(Context context, ArrayList<Users> usersArrayList)
    {
        this.context = context;
        this.usersArrayList = usersArrayList;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return usersArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_item, parent, false);

        }
        Users users = usersArrayList.get(position);

        TextView textView = convertView.findViewById(R.id.list_text);
        textView.setText(users.email);
        Log.d(TAG, "getView: email " + users.email);

        return convertView;
    }
}
