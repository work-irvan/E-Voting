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
import com.muhammad_irvan.evv.admin.data.DataPemilihanPenyelenggara;

import java.util.ArrayList;

/**
 * Created by Irvan on 26/07/2017.
 */

public class AdapterListPemilihanPenyelenggara extends BaseAdapter {
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    DataPemilihanPenyelenggara tempValues = null;

    public AdapterListPemilihanPenyelenggara(Activity a, ArrayList d, Resources resLocal) {
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
            vi = inflater.inflate(R.layout.list_penyelenggara_pemilihan, null);
            holder = new ViewHolder();
            holder.ItemImagePemilihan = (ImageView) vi.findViewById(R.id.pemilihan_penyelenggara_image);
            holder.ItemSimpanStatus = (TextView) vi.findViewById(R.id.pemilihan_penyelenggara_simpanStatus);
            holder.ItemJudulPemilihan = (TextView) vi.findViewById(R.id.pemilihan_penyelenggara_namaPemilihan);
            holder.ItemTanggalMaxGabung = (TextView) vi.findViewById(R.id.pemilihan_penyelenggara_tanggalMaxGabung);
            holder.ItemTanggalMaxVote = (TextView) vi.findViewById(R.id.pemilihan_penyelenggara_tanggalMaxVote);
            holder.ItemTanggalPerhitungan = (TextView) vi.findViewById(R.id.pemilihan_penyelenggara_tanggalPerhitungan);
            holder.ItemKeterangan = (TextView) vi.findViewById(R.id.pemilihan_penyelenggara_keterangan);
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
            tempValues = (DataPemilihanPenyelenggara) data.get(position);
            holder.ItemBungkus.setVisibility(View.VISIBLE);
            holder.ItemNotifKosong.setVisibility(View.GONE);

            holder.ItemImagePemilihan.setImageBitmap(tempValues.getPenyelenggara_image());
            holder.ItemSimpanStatus.setText(tempValues.getPenyelenggara_simpanStatus());
            holder.ItemJudulPemilihan.setText(tempValues.getPenyelenggara_namaPemilihan());
            holder.ItemTanggalMaxGabung.setText(tempValues.getPenyelenggara_tanggalMaxGabung());
            holder.ItemTanggalMaxVote.setText(tempValues.getPenyelenggara_tanggalMaxVote());
            holder.ItemTanggalPerhitungan.setText(tempValues.getPenyelenggara_tanggalPerhitungan());
            holder.ItemKeterangan.setText(tempValues.getPenyelenggara_keterangan());

            //vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    public static class ViewHolder{
        public ImageView ItemImagePemilihan;
        public TextView ItemJudulPemilihan;
        public TextView ItemTanggalMaxGabung;
        public TextView ItemTanggalMaxVote;
        public TextView ItemTanggalPerhitungan;
        public TextView ItemKeterangan;
        public TextView ItemJumlahPeserta;
        public TextView ItemSimpanStatus;
        public TextView ItemNotifKosong;
        public LinearLayout ItemBungkus;
    }

}
