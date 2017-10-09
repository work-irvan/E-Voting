package com.muhammad_irvan.eev.Adapter;

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

import com.muhammad_irvan.eev.R;
import com.muhammad_irvan.eev.Data.DataPemilihan;

import java.util.ArrayList;

/**
 * Created by Irvan on 24/07/2017.
 */

public class AdapterListPemilihan extends BaseAdapter {
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    DataPemilihan tempValues = null;

    public AdapterListPemilihan(Activity a, ArrayList d, Resources resLocal) {
        activity = a;
        data = d;
        res = resLocal;
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{
        public ImageView ItemImagePemilihan;
        public TextView ItemJudulPemilihan;
        public TextView ItemTanggalPemilihan;
        public TextView ItemKeteranganPemilihan;
        public TextView ItemPenyelenggara;
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
            vi = inflater.inflate(R.layout.list_pemilihan_peserta, null);
            holder = new ViewHolder();
            holder.ItemImagePemilihan = (ImageView) vi.findViewById(R.id.imagePemilihan);
            holder.ItemJudulPemilihan = (TextView)vi.findViewById(R.id.pemilihan_judul);
            holder.ItemTanggalPemilihan = (TextView)vi.findViewById(R.id.pemilihan_tanggal);
            holder.ItemKeteranganPemilihan = (TextView)vi.findViewById(R.id.pemilihan_keterangan);
            holder.ItemPenyelenggara = (TextView)vi.findViewById(R.id.pemilihan_penyelenggara);
            holder.ItemNotifKosong = (TextView) vi.findViewById(R.id.pemilihan_penyelenggara_notifkosong);
            holder.ItemBungkus = (LinearLayout) vi.findViewById(R.id.pemilihan_penyelenggara_Bungkus);

            vi.setTag(holder);
        }else
            holder=(ViewHolder)vi.getTag();

        if(data.size() <= 0){
            holder.ItemBungkus.setVisibility(View.GONE);
            holder.ItemNotifKosong.setVisibility(View.VISIBLE);
        }else{
            tempValues=null;
            tempValues = (DataPemilihan) data.get(position);
            holder.ItemBungkus.setVisibility(View.VISIBLE);
            holder.ItemNotifKosong.setVisibility(View.GONE);

            holder.ItemImagePemilihan.setImageBitmap(tempValues.getImage_pemilihan());
            holder.ItemJudulPemilihan.setText(tempValues.getJudul_pemilihan());
            holder.ItemTanggalPemilihan.setText(tempValues.getTanggal_pemilhan());
            holder.ItemKeteranganPemilihan.setText(tempValues.getKeterangan_pemilihan());
            holder.ItemPenyelenggara.setText(tempValues.getPenyelenggara_pemilihan());

            //vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;


    }
}
