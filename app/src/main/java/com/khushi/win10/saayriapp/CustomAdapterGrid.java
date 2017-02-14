package com.khushi.win10.saayriapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nensee on 2/13/17.
 */
public class CustomAdapterGrid extends BaseAdapter {
    Context context;
    ArrayList<Post> posts;
    LayoutInflater layoutInflater;



    CustomAdapterGrid(Context context, ArrayList<Post> posts)
    {
        this.context=context;
        this.posts=posts;
    }


    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder
    {
        TextView uid;
        TextView desc;
    }

    private int[] tagCollection;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.gridview, parent, false);
            holder.uid = (TextView) convertView.findViewById(R.id.txtviw1);

            holder.desc = (TextView) convertView.findViewById(R.id.txtviw2);

            convertView.setTag(holder);



        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }

        holder.desc.setText(posts.get(position).getDescription());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(context,SecondActivity.class);
                a.putExtra("pos", posts.get(position).getId());
                context.startActivity(a);
            }
        });


        return convertView;
    }

}
