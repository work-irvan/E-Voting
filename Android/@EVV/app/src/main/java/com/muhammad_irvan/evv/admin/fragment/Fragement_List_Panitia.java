package com.muhammad_irvan.evv.admin.fragment;

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
import android.widget.ListView;
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
import com.muhammad_irvan.evv.admin.R;
import com.muhammad_irvan.evv.admin.UbahPanitia;
import com.muhammad_irvan.evv.admin.adapter.AdapterListPanitia;
import com.muhammad_irvan.evv.admin.data.DataPanitia;
import com.muhammad_irvan.evv.admin.data.DataPanitiaTerpilih;
import com.muhammad_irvan.evv.admin.data.DataPemilihanPenyelenggaraTerpilih;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Irvan on 10/08/2017.
 */

public class Fragement_List_Panitia extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public Fragment_Penyelenggara_Pemilihan CustomListView = null;
    public ArrayList<DataPanitia> CustomListViewValuesArr = new ArrayList<DataPanitia>();
    ListView list;
    SwipeRefreshLayout swipeRefreshLayout;
    AdapterListPanitia adapter;
    public Fragement_List_Panitia() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_list_panitia, container, false);

        Resources res       = getResources();

        list                =(ListView)rootView.findViewById(R.id.ListPanitia);

        swipeRefreshLayout  = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshPanitia);
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new AdapterListPanitia(getActivity(), CustomListViewValuesArr, res);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!CustomListViewValuesArr.isEmpty()){
                    final DataPanitia tempValues = (DataPanitia) CustomListViewValuesArr.get(position);
                    DataPanitiaTerpilih.setId_panitia(tempValues.getId_panitia());
                    DataPanitiaTerpilih.setNama(tempValues.getNama());
                    DataPanitiaTerpilih.setLevel(tempValues.getLevel());
                    DataPanitiaTerpilih.setEmail(tempValues.getEmail());
                    DataPanitiaTerpilih.setPassword(tempValues.getPassword());

                    startActivity(new Intent(getContext(), UbahPanitia.class));
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

    @Override
    public void onResume() {
        super.onResume();
        ambildata();
    }

    public void ambildata(){
        if(!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/tampilpanitia";
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

                        final DataPanitia sched = new DataPanitia();
                        sched.setId_panitia(object.getString("id_panitia"));
                        sched.setNama(object.getString("nama"));
                        if(object.getString("level").equals("1")){
                            sched.setLevel("Panitia Kandidat");
                        }else if(object.getString("level").equals("2")){
                            sched.setLevel("Panitia Peserta");
                        }
                        sched.setEmail(object.getString("email"));
                        sched.setPassword(object.getString("password"));



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
}
