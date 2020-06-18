package com.example.hackerd.appweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{
    Context context;
    ArrayList<Thoitiet> arrayList;

    public CustomAdapter(Context context, ArrayList<Thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.dong_listview,null);

        Thoitiet thoitiet = arrayList.get(position);

        TextView txtviewngay = convertView.findViewById(R.id.txtviewngay);
        TextView txtviewtrangthai = convertView.findViewById(R.id.txtviewtrangthai);
        TextView txtmax = convertView.findViewById(R.id.txtmax);
        TextView txtmin = convertView.findViewById(R.id.txtmin);
        ImageView imgvtrangthai = convertView.findViewById(R.id.imgvtrangthai);

        txtviewngay.setText(thoitiet.Day);
        txtviewtrangthai.setText(thoitiet.Status);
        txtmax.setText(thoitiet.MaxTemp+"°C");
        txtmin.setText(thoitiet.MinTemp+"°C");

        Picasso.with(context).load("http://openweathermap.org/img/w/"+thoitiet.Image+".png").into(imgvtrangthai);
        return convertView;
    }
}
