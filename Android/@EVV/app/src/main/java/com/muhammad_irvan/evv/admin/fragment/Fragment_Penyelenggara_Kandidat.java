package com.muhammad_irvan.evv.admin.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.muhammad_irvan.evv.admin.BuildConfig;
import com.muhammad_irvan.evv.admin.R;
import com.muhammad_irvan.evv.admin.TambahKandidat;
import com.muhammad_irvan.evv.admin.UbahKandidat;
import com.muhammad_irvan.evv.admin.adapter.AdapterListKandidatPenyelenggara;
import com.muhammad_irvan.evv.admin.data.DataKandidatPenyelenggara;
import com.muhammad_irvan.evv.admin.data.DataKandidatPenyelenggaraTerpilih;
import com.muhammad_irvan.evv.admin.data.DataPemilihanPenyelenggaraTerpilih;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Irvan on 26/07/2017.
 */

public class Fragment_Penyelenggara_Kandidat extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public Fragment_Penyelenggara_Kandidat CustomListView = null;
    public ArrayList<DataKandidatPenyelenggara> CustomListViewValuesArr = new ArrayList<DataKandidatPenyelenggara>();
    ListView list;
    AdapterListKandidatPenyelenggara adapter;
    Button tambah;
    SwipeRefreshLayout swipeRefreshLayout;
    public Fragment_Penyelenggara_Kandidat() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOGG", "BBB " + DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_idPemilihan());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_penyelenggara_kandidat, container, false);

        Resources res = getResources();

        list=(ListView)rootView.findViewById(R.id.ListKandidatPenyelenggara);
        tambah = (Button)rootView.findViewById(R.id.kandidat_btntambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), TambahKandidat.class));
            }
        });

        if(!DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_simpanStatus().equals("Draft")){
            tambah.setEnabled(false);
        }else{
            tambah.setEnabled(true);
        }


        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshKandidat);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new AdapterListKandidatPenyelenggara(getActivity(), CustomListViewValuesArr, res);
        list.setAdapter(adapter);
        list.setClickable(true);
        ambildata();

        Log.d("LOGG", "AAA " + DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_idPemilihan());
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!CustomListViewValuesArr.isEmpty()){

                    final DataKandidatPenyelenggara tempValues = (DataKandidatPenyelenggara) CustomListViewValuesArr.get(position);

                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.detail_list_pemilihan_kandidat_penyelenggara);
                    //dialog.setTitle("Opsi " + tempValues.getNama());

                    final ImageView detail_image_kanididat = (ImageView) dialog.findViewById(R.id.detail_list_pemilhan_kandidat_penyelenggara_imagegambar);
                    final TextView detail_nama_kanididat = (TextView)dialog.findViewById(R.id.detail_list_pemilhan_kandidat_penyelenggara_namakandidat);
                    final TextView detail_slogan_kanididat = (TextView)dialog.findViewById(R.id.detail_list_pemilhan_kandidat_penyelenggara_slogan);
                    final TextView detail_tanggalLahir_kanididat = (TextView)dialog.findViewById(R.id.detail_list_pemilhan_kandidat_penyelenggara_tanggallahir);
                    final TextView detail_keterangan_kanididat = (TextView)dialog.findViewById(R.id.detail_list_pemilhan_kandidat_penyelenggara_keterangan);
                    final TextView detail_alamat_kanididat = (TextView)dialog.findViewById(R.id.detail_list_pemilhan_kandidat_penyelenggara_alamat);
                    final Button tombolUbah = (Button) dialog.findViewById(R.id.detail_list_pemilhan_kandidat_penyelenggara_tombolUbah);


                    detail_image_kanididat.setImageBitmap(tempValues.getKandidatImage());
                    detail_nama_kanididat.setText(tempValues.getKandidatNama());
                    detail_slogan_kanididat.setText(tempValues.getKandidatSlogan());
                    detail_tanggalLahir_kanididat.setText(tempValues.getKandidatTanggal());
                    detail_keterangan_kanididat.setText(tempValues.getKandidatKeterangan());
                    detail_alamat_kanididat.setText(tempValues.getKandidatAlamat());

                    if(!DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_simpanStatus().equals("Draft")){
                        tombolUbah.setEnabled(false);
                    }else{
                        tombolUbah.setEnabled(true);
                    }
                    tombolUbah.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent buka = new Intent(getContext(), UbahKandidat.class);
                            DataKandidatPenyelenggaraTerpilih.setKandidatId(tempValues.getKandidatId());
                            DataKandidatPenyelenggaraTerpilih.setKandidatIdPemilihan(tempValues.getKandidatIdPemilihan());
                            DataKandidatPenyelenggaraTerpilih.setKandidatNama(tempValues.getKandidatNama());
                            DataKandidatPenyelenggaraTerpilih.setKandidatSlogan(tempValues.getKandidatSlogan());
                            DataKandidatPenyelenggaraTerpilih.setKandidatTanggal(tempValues.getKandidatTanggal());
                            DataKandidatPenyelenggaraTerpilih.setKandidatAlamat(tempValues.getKandidatAlamat());
                            DataKandidatPenyelenggaraTerpilih.setKandidatKeterangan(tempValues.getKandidatKeterangan());
                            DataKandidatPenyelenggaraTerpilih.setKandidatImage(tempValues.getKandidatImage());
                            startActivity(buka);
                            dialog.hide();
                        }
                    });

                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    dialog.show();
                    dialog.getWindow().setAttributes(lp);
                }
            }
        });
        return rootView;

    }

    public void ambildata(){
        if(!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/tampilkandidatpenyelenggara";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);

                    DataKandidatPenyelenggaraTerpilih.setRespone(response);
                    CustomListViewValuesArr.clear();
                    JSONObject jObject = new JSONObject(response);
                    JSONArray data_pemilihan = jObject.getJSONArray("umpan");
                    for(int i = 0; i < data_pemilihan.length(); i++){
                        JSONObject object = data_pemilihan.getJSONObject(i);

                        final DataKandidatPenyelenggara sched = new DataKandidatPenyelenggara();
                        sched.setKandidatId(object.getString("id_kandidat"));
                        sched.setKandidatIdPemilihan(object.getString("id_pemilihan"));
                        sched.setKandidatImage(ImageFromStr(object.getString("gambar")));
                        sched.setKandidatNama(object.getString("nama_kanidat"));
                        sched.setKandidatSlogan(object.getString("slogan"));
                        sched.setKandidatTanggal(object.getString("tgl_lahir"));
                        sched.setKandidatAlamat(object.getString("alamat"));
                        sched.setKandidatKeterangan(object.getString("keterangan"));

                        CustomListViewValuesArr.add(sched);
                    }
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
                if(error instanceof TimeoutError){
                    Toast.makeText(getActivity().getApplicationContext(), "Time Out Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NoConnectionError){
                    Toast.makeText(getActivity().getApplicationContext(), "No Connection Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof AuthFailureError){
                    Toast.makeText(getActivity().getApplicationContext(), "Auth Failure Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof NetworkError){
                    Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }else if(error instanceof ServerError){
                    Toast.makeText(getActivity().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String,String>();
                map.put("id_pemilihan", DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_idPemilihan());

                return map;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();

                return headers;
            }
        };
        queue.add(request);
    }
    public Bitmap ImageFromStr(String str){
        Bitmap bmp;
        byte[] decodeImage = Base64.decode(str, Base64.DEFAULT);
        bmp = BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);
        return bmp;
    }

    @Override
    public void onRefresh() {
        ambildata();
        super.onResume();
    }
}
