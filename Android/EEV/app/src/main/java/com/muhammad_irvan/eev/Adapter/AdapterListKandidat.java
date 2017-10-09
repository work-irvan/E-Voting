package com.muhammad_irvan.eev.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muhammad_irvan.eev.R;
import com.muhammad_irvan.eev.Data.DataKandidat;

import java.util.ArrayList;

/**
 * Created by Irvan on 26/07/2017.
 */

public class AdapterListKandidat extends BaseAdapter {
    private Activity activity;
    private Fragment fragment;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    DataKandidat tempValues = null;

    public AdapterListKandidat(Activity a, ArrayList d, Resources resLocal) {
        activity = a;
        data = d;
        res = resLocal;
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{
        public ImageView ItemImageKandidat;
        public TextView ItemNamaKandidat;
        public TextView ItemKeterangan;
        public TextView ItemNotifKosong;
        public LinearLayout ItemBungkus;
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
            vi = inflater.inflate(R.layout.list_kandidat, null);
            holder = new ViewHolder();
            holder.ItemImageKandidat = (ImageView) vi.findViewById(R.id.penyelenggara_kandidat_imageKandidat);
            holder.ItemNamaKandidat = (TextView)vi.findViewById(R.id.penyelenggara_kandidat_namaKandidat);
            holder.ItemKeterangan = (TextView)vi.findViewById(R.id.penyelenggara_kandidat_keterangan);
            holder.ItemNotifKosong = (TextView) vi.findViewById(R.id.kandidat_penyelenggara_notifkosong);
            holder.ItemBungkus = (LinearLayout) vi.findViewById(R.id.kandidat_penyelenggara_Bungkus);

            vi.setTag(holder);
        }else
            holder=(ViewHolder)vi.getTag();

        if(data.size() <= 0){
            holder.ItemBungkus.setVisibility(View.GONE);
            holder.ItemNotifKosong.setVisibility(View.VISIBLE);
        }else{
            tempValues=null;
            tempValues = (DataKandidat) data.get(position);
            holder.ItemBungkus.setVisibility(View.VISIBLE);
            holder.ItemNotifKosong.setVisibility(View.GONE);
            holder.ItemImageKandidat.setImageBitmap(tempValues.getKandidatImage());
            holder.ItemNamaKandidat.setText(tempValues.getKandidatNama());
            holder.ItemKeterangan.setText(tempValues.getKandidatKeterangan());

            //vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

}
