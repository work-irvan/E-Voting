package com.muhammad_irvan.evv.admin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.muhammad_irvan.evv.admin.R;
import com.muhammad_irvan.evv.admin.data.DataKandidatPenyelenggara;
import com.muhammad_irvan.evv.admin.data.DataPesertaPemilihan;

import java.util.ArrayList;

/**
 * Created by Irvan on 26/07/2017.
 */

public class AdapterListPesertaPemilihan extends BaseAdapter {
    private Activity activity;
    private Fragment fragment;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    DataPesertaPemilihan tempValues = null;

    public AdapterListPesertaPemilihan(Activity a, ArrayList d, Resources resLocal) {
        activity = a;
        data = d;
        res = resLocal;
        inflater = ( LayoutInflater )activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder{
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
            vi = inflater.inflate(R.layout.list_peserta_pemilihan, null);
            holder = new ViewHolder();
            holder.ItemNamaKandidat = (TextView)vi.findViewById(R.id.peserta_pemilihan_namaKandidat);
            holder.ItemKeterangan = (TextView)vi.findViewById(R.id.peserta_pemilihan_keterangan);
            holder.ItemNotifKosong = (TextView) vi.findViewById(R.id.peserta_pemilihan_notifkosong);
            holder.ItemBungkus = (LinearLayout) vi.findViewById(R.id.peserta_pemilihan_Bungkus);

            vi.setTag(holder);
        }else
            holder=(ViewHolder)vi.getTag();

        if(data.size() <= 0){
            holder.ItemBungkus.setVisibility(View.GONE);
            holder.ItemNotifKosong.setVisibility(View.VISIBLE);
        }else{
            tempValues=null;
            tempValues = (DataPesertaPemilihan) data.get(position);
            holder.ItemBungkus.setVisibility(View.VISIBLE);
            holder.ItemNotifKosong.setVisibility(View.GONE);
            holder.ItemNamaKandidat.setText(tempValues.getNama());
            holder.ItemKeterangan.setText(tempValues.getAlamat());

            //vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

}
