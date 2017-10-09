package com.muhammad_irvan.evv.admin.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.muhammad_irvan.evv.admin.InfoAkun;
import com.muhammad_irvan.evv.admin.PemilihanPenyelenggara;
import com.muhammad_irvan.evv.admin.R;
import com.muhammad_irvan.evv.admin.adapter.AdapterListPemilihanPenyelenggara;
import com.muhammad_irvan.evv.admin.adapter.AdapterListPesertaPemilihan;
import com.muhammad_irvan.evv.admin.data.DataPemilihanPenyelenggara;
import com.muhammad_irvan.evv.admin.data.DataPemilihanPenyelenggaraTerpilih;
import com.muhammad_irvan.evv.admin.data.DataPesertaPemilihan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Irvan on 08/08/2017.
 */

public class Fragment_Pemilihan_Peserta extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public ArrayList<DataPesertaPemilihan> CustomListViewValuesArr = new ArrayList<DataPesertaPemilihan>();
    ListView list;
    SwipeRefreshLayout swipeRefreshLayout;
    AdapterListPesertaPemilihan adapter;
    public Fragment_Pemilihan_Peserta() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_penyelenggara_peserta, container, false);

        Resources res = getResources();

        list=(ListView)rootView.findViewById(R.id.ListPemilihanPeserta);

        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshPeserta);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new AdapterListPesertaPemilihan(getActivity(), CustomListViewValuesArr, res);
        list.setAdapter(adapter);
        list.setClickable(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!CustomListViewValuesArr.isEmpty()){
                    final DataPesertaPemilihan tempValues = (DataPesertaPemilihan) CustomListViewValuesArr.get(position);

                }
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!CustomListViewValuesArr.isEmpty()){

                    final DataPesertaPemilihan tempValues = (DataPesertaPemilihan) CustomListViewValuesArr.get(position);

                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.detail_list_peserta_pemilihan);
                    //dialog.setTitle("Opsi " + tempValues.getNama());

                    final TextView detail_nama      = (TextView)dialog.findViewById(R.id.detail_list_peserta_nama);
                    final TextView detail_jenkel    = (TextView)dialog.findViewById(R.id.detail_list_peserta_jenkel);
                    final TextView detail_tglLahir  = (TextView)dialog.findViewById(R.id.detail_list_peserta_tanggalLahir);
                    final TextView detail_alamat    = (TextView)dialog.findViewById(R.id.detail_list_peserta_alamat);
                    final TextView detail_email     = (TextView)dialog.findViewById(R.id.detail_list_peserta_email);
                    final Button Btn1 = (Button) dialog.findViewById(R.id.detail_list_peserta_Btn1);
                    final Button Btn2= (Button) dialog.findViewById(R.id.detail_list_peserta_Btn2);

                    detail_nama.setText(tempValues.getNama());
                    detail_jenkel.setText(tempValues.getJenis_kelamin());
                    detail_tglLahir.setText(tempValues.getTanggal_lahir());
                    detail_alamat.setText(tempValues.getAlamat());
                    detail_email.setText(tempValues.getEmail());


                    Btn1.setText("Batal");
                    Btn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    Btn2.setText("Keluarkan");
                    Btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hapus(tempValues.getId_pemilihan(), tempValues.getId_peserta());
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }
            }
        });

        ambildata();
        return rootView;
    }

    @Override
    public void onRefresh() {
        ambildata();
    }

    public void ambildata(){
        if(!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/tampilpeserta";
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

                        final DataPesertaPemilihan sched = new DataPesertaPemilihan();
                        sched.setId_pemilihan(object.getString("id_pemilihan"));
                        sched.setId_peserta(object.getString("id_peserta"));
                        sched.setNama(object.getString("nama"));
                        sched.setJenis_kelamin(object.getString("jenis_kelamin"));
                        sched.setTanggal_lahir(object.getString("tanggal_lahir"));
                        sched.setAlamat(object.getString("alamat"));
                        sched.setEmail(object.getString("email"));
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

    public void hapus(final String id_pemilihan, final String id_peserta){
        if(!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/hapuspeserta";
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONN", response);
                    JSONObject jObject = new JSONObject(response);
                    jObject = new JSONObject(response);
                    ambildata();
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
                map.put("id_pemilihan", id_pemilihan);
                map.put("id_peserta", id_peserta);

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
}
