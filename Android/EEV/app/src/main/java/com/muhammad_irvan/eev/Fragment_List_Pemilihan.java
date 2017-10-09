package com.muhammad_irvan.eev;

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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.muhammad_irvan.eev.Adapter.AdapterListPemilihan;
import com.muhammad_irvan.eev.Data.DataPemilihan;
import com.muhammad_irvan.eev.Data.DataPemilihanTerpilih;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Irvan on 24/07/2017.
 */

public class Fragment_List_Pemilihan extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    public Fragment_List_Pemilihan CustomListView = null;
    public ArrayList<DataPemilihan> CustomListViewValuesArr = new ArrayList<DataPemilihan>();
    private SwipeRefreshLayout swipeRefreshLayout;
    ListView list;
    AdapterListPemilihan adapter;

    public Fragment_List_Pemilihan() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_list_pemilihan, container, false);

        Resources res = getResources();

        list=(ListView)rootView.findViewById(R.id.ListPemilihanPeserta);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout_pemilihan);

        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new AdapterListPemilihan(getActivity(), CustomListViewValuesArr, res);
        list.setAdapter(adapter);
        list.setClickable(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!CustomListViewValuesArr.isEmpty()){
                    final DataPemilihan tempValues = (DataPemilihan) CustomListViewValuesArr.get(position);

                    Intent buka = new Intent(getContext(), PemilihanActivity.class);

                    DataPemilihanTerpilih.setPenyelenggara_idPemilihan(tempValues.getId_pemilihan());
                    DataPemilihanTerpilih.setPenyelenggara_image(tempValues.getImage_pemilihan());
                    DataPemilihanTerpilih.setPenyelenggara_namaPemilihan(tempValues.getJudul_pemilihan());
                    DataPemilihanTerpilih.setPenyelenggara_keterangan(tempValues.getKeterangan_pemilihan());

                    DataPemilihanTerpilih.setPenyelenggara_tanggalMaxGabung(tempValues.getPenyelenggara_tanggalMaxGabung());
                    DataPemilihanTerpilih.setPenyelenggara_tanggalMaxVote(tempValues.getPenyelenggara_tanggalMaxVote());
                    DataPemilihanTerpilih.setPenyelenggara_tanggalPerhitungan(tempValues.getPenyelenggara_tanggalPerhitungan());

                    DataPemilihanTerpilih.setPenyelenggara_selesai(tempValues.getPenyelenggara_selesai());

                    Log.d("Testtt", tempValues.getPenyelenggara_tanggalMaxGabung());
                    startActivity(buka);
                }
            }
        });

        setList();

        return rootView;
    }

    private void setList(){
        if(!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final String URL = BuildConfig.BaseUrl + "api/tampilpemilihanpeserta";
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
                        final DataPemilihan sched = new DataPemilihan();
                        sched.setId_pemilihan(object.getString("id_pemilihan"));
                        sched.setImage_pemilihan(ImageFromStr(object.getString("icon")));
                        sched.setJudul_pemilihan(object.getString("nama_pemilihan"));
                        sched.setKeterangan_pemilihan(object.getString("keterangan"));
                        sched.setPenyelenggara_tanggalMaxGabung(object.getString("max_gabung"));
                        sched.setPenyelenggara_tanggalMaxVote(object.getString("max_vote"));
                        sched.setPenyelenggara_tanggalPerhitungan(object.getString("perhitungan"));
                        sched.setPenyelenggara_selesai(object.getString("selesai"));

                        CustomListViewValuesArr.add(sched);
                    }
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } catch (JSONException e) {
                    swipeRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                swipeRefreshLayout.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<String,String>();
                map.put("id_peserta",InfoAkun.getInfo_peserta_id_peserta());

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

    @Override
    public void onRefresh() {
        setList();
    }
    public Bitmap ImageFromStr(String str){
        Bitmap bmp;
        byte[] decodeImage = Base64.decode(str, Base64.DEFAULT);
        bmp = BitmapFactory.decodeByteArray(decodeImage, 0, decodeImage.length);
        return bmp;
    }
}
