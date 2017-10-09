package com.muhammad_irvan.evv.admin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muhammad_irvan.evv.admin.R;
import com.muhammad_irvan.evv.admin.data.DataHasilPemilihan;
import com.muhammad_irvan.evv.admin.data.DataPanitia;

import java.util.ArrayList;

/**
 * Created by Irvan on 26/07/2017.
 */

public class AdapterListPanitia extends BaseAdapter {
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    DataPanitia tempValues = null;

    public AdapterListPanitia(Activity a, ArrayList d, Resources resLocal) {
        activity = a;
        data = d;
        res = resLocal;
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView == null){
            vi = inflater.inflate(R.layout.list_panitia, null);
            holder = new ViewHolder();
            holder.ItemNama     = (TextView) vi.findViewById(R.id.panitia_nama);
            holder.ItemLevel    = (TextView)vi.findViewById(R.id.panitia_level);
            holder.ItemEmail    = (TextView)vi.findViewById(R.id.panitia_email);
            holder.ItemNotifKosong = (TextView) vi.findViewById(R.id.panitia_notifkosong);
            holder.ItemBungkus  = (LinearLayout) vi.findViewById(R.id.panitia_pemilihan_Bungkus);

            vi.setTag(holder);
        }else
            holder=(ViewHolder)vi.getTag();

        if(data.size() <= 0){
            holder.ItemBungkus.setVisibility(View.GONE);
            holder.ItemNotifKosong.setVisibility(View.VISIBLE);
        }else{
            tempValues=null;
            tempValues = (DataPanitia) data.get(position);
            holder.ItemBungkus.setVisibility(View.VISIBLE);
            holder.ItemNotifKosong.setVisibility(View.GONE);
            holder.ItemNama.setText(tempValues.getNama());
            holder.ItemLevel.setText(tempValues.getLevel());
            holder.ItemEmail.setText(tempValues.getEmail());

            //vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    public static class ViewHolder{
        public TextView ItemNama;
        public TextView ItemLevel;
        public TextView ItemEmail;
        public TextView ItemNotifKosong;
        public LinearLayout ItemBungkus;
    }
}
