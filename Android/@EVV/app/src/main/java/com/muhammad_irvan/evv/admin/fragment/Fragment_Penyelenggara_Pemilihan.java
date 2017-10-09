package com.muhammad_irvan.evv.admin.fragment;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.muhammad_irvan.evv.admin.BuildConfig;
import com.muhammad_irvan.evv.admin.InfoAkun;
import com.muhammad_irvan.evv.admin.PemilihanPenyelenggara;
import com.muhammad_irvan.evv.admin.R;
import com.muhammad_irvan.evv.admin.adapter.AdapterListPemilihanPenyelenggara;
import com.muhammad_irvan.evv.admin.data.DataPemilihanPenyelenggara;
import com.muhammad_irvan.evv.admin.data.DataPemilihanPenyelenggaraTerpilih;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Irvan on 26/07/2017.
 */

public class Fragment_Penyelenggara_Pemilihan extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public Fragment_Penyelenggara_Pemilihan CustomListView = null;
    public ArrayList<DataPemilihanPenyelenggara> CustomListViewValuesArr = new ArrayList<DataPemilihanPenyelenggara>();
    ListView list;
    SwipeRefreshLayout swipeRefreshLayout;
    AdapterListPemilihanPenyelenggara adapter;
    public Fragment_Penyelenggara_Pemilihan() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_penyelenggara_pemilihan, container, false);

        Resources res = getResources();

        list=(ListView)rootView.findViewById(R.id.ListPemilihanPenyelenggara);

        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshPemilihan);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new AdapterListPemilihanPenyelenggara(getActivity(), CustomListViewValuesArr, res);
        list.setAdapter(adapter);
        list.setClickable(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!CustomListViewValuesArr.isEmpty()){
                    final DataPemilihanPenyelenggara tempValues = (DataPemilihanPenyelenggara) CustomListViewValuesArr.get(position);

                    Intent buka = new Intent(getActivity().getApplicationContext(), PemilihanPenyelenggara.class);

                    DataPemilihanPenyelenggaraTerpilih.setPenyelenggara_idPemilihan(tempValues.getPenyelenggara_idPemilihan());
                    DataPemilihanPenyelenggaraTerpilih.setPenyelenggara_image(tempValues.getPenyelenggara_image());
                    DataPemilihanPenyelenggaraTerpilih.setPenyelenggara_namaPemilihan(tempValues.getPenyelenggara_namaPemilihan());
                    DataPemilihanPenyelenggaraTerpilih.setPenyelenggara_tanggalMaxGabung(tempValues.getPenyelenggara_tanggalMaxGabung());
                    DataPemilihanPenyelenggaraTerpilih.setPenyelenggara_tanggalMaxVote(tempValues.getPenyelenggara_tanggalMaxVote());
                    DataPemilihanPenyelenggaraTerpilih.setPenyelenggara_tanggalPerhitungan(tempValues.getPenyelenggara_tanggalPerhitungan());
                    DataPemilihanPenyelenggaraTerpilih.setPenyelenggara_simpanStatus(tempValues.getPenyelenggara_simpanStatus());
                    DataPemilihanPenyelenggaraTerpilih.setPenyelenggara_keterangan(tempValues.getPenyelenggara_keterangan());
                    DataPemilihanPenyelenggaraTerpilih.setPenyelenggara_peserta(tempValues.getPenyelenggara_peserta());
                    DataPemilihanPenyelenggaraTerpilih.setSelesai(tempValues.getSelesai());

                    startActivity(buka);

                }
            }
        });

        ambildata();
        return rootView;
    }


    public void ambildata(){
        if(!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/tampilpemilihanpenyelenggara";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);
                    CustomListViewValuesArr.clear();
                    JSONObject jObject = new JSONObject(response);
                    JSONArray data_pemilihan = jObject.getJSONArray("umpan");
                    for(int i = 0; i < data_pemilihan.length(); i++){
                        JSONObject object = data_pemilihan.getJSONObject(i);
                        String simpan_status = "";
                        if(object.getInt("status") == 0){
                            simpan_status = "Draft";
                        }else{
                            simpan_status = "Publish";
                        }
                        final DataPemilihanPenyelenggara sched = new DataPemilihanPenyelenggara();
                        sched.setPenyelenggara_idPemilihan(object.getString("id_pemilihan"));
                        sched.setPenyelenggara_image(ImageFromStr(object.getString("icon")));
                        sched.setPenyelenggara_namaPemilihan(object.getString("nama_pemilihan"));
                        sched.setPenyelenggara_simpanStatus(simpan_status);
                        sched.setPenyelenggara_tanggalMaxGabung(object.getString("max_gabung"));
                        sched.setPenyelenggara_tanggalMaxVote(object.getString("max_vote"));
                        sched.setPenyelenggara_tanggalPerhitungan(object.getString("perhitungan"));
                        sched.setPenyelenggara_keterangan(object.getString("keterangan"));
                        String selesai = object.getString("selesai");
                        Log.d("BBBBB", "selesai : " + selesai);
                        if(selesai.equals("0")){
                            sched.setSelesai(false);
                            Log.d("BBBBB", "false");
                        }else{
                            sched.setSelesai(true);
                            Log.d("BBBBB", "true");
                        }

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
                map.put("id_penyelenggara", InfoAkun.getInfo_penyelenggara_id_penyelenggara());

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
