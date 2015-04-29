package com.japanzai.koroshiya.filechooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.japanzai.koroshiya.R;

import java.util.ArrayList;

public class FileItemAdapter extends BaseAdapter {

    final ArrayList<FileItem> items;
    final Context c;
    final View.OnClickListener ocl;
    final View.OnLongClickListener olcl;

    public FileItemAdapter(Context c, ArrayList<FileItem> items, View.OnClickListener ocl, View.OnLongClickListener olcl) {
        this.c = c;
        this.items = items;
        this.ocl = ocl;
        this.olcl = olcl;
    }

    public long getItemId(int position) {
        return 0;
    }

    public FileItem getItem(int position) {
        return items.get(position);
    }

    public int getCount() {
        return items.size();
    }

    @Override
    public TextView getView(int position, View v, ViewGroup parent) {

        TextView tv;

        if (v == null){
            tv = (TextView) LayoutInflater.from(c).inflate(R.layout.list_item, null);
        }else{
            tv = (TextView) v;
        }

        FileItem p = getItem(position);
        tv.setCompoundDrawablesWithIntrinsicBounds(null, p.getRes(), null, null);

        String t = p.getText();
        tv.setContentDescription(t);

        if (t.length() > 25){
            if (t.startsWith("/")){
                int last = t.lastIndexOf('/');
                int sLast = t.substring(0, last).lastIndexOf('/');
                String lStr = t.substring(last);
                String sStr = t.substring(sLast, last);
                t = ellipsize(sStr) + ellipsize(lStr);
            }else{
                t = ellipsize(t);
            }
        }
        tv.setText(t);


        if (ocl != null) tv.setOnClickListener(ocl);
        if (olcl != null) tv.setOnLongClickListener(olcl);

        return tv;

    }

    private String ellipsize(String str){
        return str.length() <= 25 ? str : str.substring(0, 23) + "...";
    }
}