package com.muhammad_irvan.eev;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.muhammad_irvan.eev.Data.DataPemilihanTerpilih;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PemilihanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemilihan);

        toolbar = (Toolbar) findViewById(R.id.pemilihan_toolbar);
        toolbar.setTitle(DataPemilihanTerpilih.getPenyelenggara_namaPemilihan());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpagerPemilihanPenyelenggara);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabsPemilihanPenyelenggara);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        PemilihanActivity.ViewPagerAdapter adapter = new PemilihanActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragment_Pemilihan_Info(), "INFO");
        adapter.addFrag(new Fragment_Kandidat(), "KANDIDAT");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        try {
            Date date1  = new Date();
            Date date2  = sdf.parse(DataPemilihanTerpilih.getPenyelenggara_tanggalPerhitungan());
            if(date1.compareTo(date2) < 0){

            }else{
                adapter.addFrag(new Fragment_Perhitungan(), "PERHITUNGAN");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
