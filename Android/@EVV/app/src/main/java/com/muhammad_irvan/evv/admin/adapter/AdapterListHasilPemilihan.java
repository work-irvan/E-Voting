package com.muhammad_irvan.evv.admin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.muhammad_irvan.evv.admin.R;
import com.muhammad_irvan.evv.admin.data.DataHasilPemilihan;

import java.util.ArrayList;

/**
 * Created by Irvan on 26/07/2017.
 */

public class AdapterListHasilPemilihan extends BaseAdapter {
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    DataHasilPemilihan tempValues = null;

    public AdapterListHasilPemilihan(Activity a, ArrayList d, Resources resLocal) {
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
            vi = inflater.inflate(R.layout.list_hasil_pemilihan_detail, null);
            holder = new ViewHolder();
            holder.ItemImageKandidat = (ImageView) vi.findViewById(R.id.hasil_image_kandidat);
            holder.ItemNamaKandidat = (TextView)vi.findViewById(R.id.hasil_nama_kandidat);
            holder.ItemPerolehanSuara = (TextView)vi.findViewById(R.id.hasil_perolehan_suara);

            vi.setTag(holder);
        }else
            holder=(ViewHolder)vi.getTag();

        if(data.size() <= 0){
            holder.ItemNamaKandidat.setText("-");
        }else{
            tempValues=null;
            tempValues = (DataHasilPemilihan) data.get(position);

            holder.ItemImageKandidat.setImageBitmap(tempValues.getImageKandidat());
            holder.ItemNamaKandidat.setText(tempValues.getNamaKandidat());
            holder.ItemPerolehanSuara.setText(tempValues.getPerolehanSuara());

            //vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    public static class ViewHolder{
        public ImageView ItemImageKandidat;
        public TextView ItemNamaKandidat;
        public TextView ItemPerolehanSuara;
    }
}
