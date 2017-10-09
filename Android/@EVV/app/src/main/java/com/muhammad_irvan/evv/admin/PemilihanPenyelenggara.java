package com.muhammad_irvan.evv.admin;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.muhammad_irvan.evv.admin.data.DataPemilihanPenyelenggaraTerpilih;
import com.muhammad_irvan.evv.admin.fragment.Fragment_Penyelenggara_Kandidat;
import com.muhammad_irvan.evv.admin.fragment.Fragment_Penyelenggara_Pemilihan_Info;
import com.muhammad_irvan.evv.admin.fragment.Fragment_Penyelenggara_Perhitungan;

import java.util.ArrayList;
import java.util.List;

public class PemilihanPenyelenggara extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemilihan_penyelenggara);

        toolbar = (Toolbar) findViewById(R.id.toolbarPemilihanPenyelenggara);
        toolbar.setTitle(DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_namaPemilihan());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpagerPemilihanPenyelenggara);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabsPemilihanPenyelenggara);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(InfoAkun.getLevel() == 0){
            adapter.addFrag(new Fragment_Penyelenggara_Pemilihan_Info(), "INFO");
            adapter.addFrag(new Fragment_Penyelenggara_Kandidat(), "KANDIDAT");
            adapter.addFrag(new Fragment_Penyelenggara_Perhitungan(), "PERHITUNGAN");
        }else if(InfoAkun.getLevel() == 1){
            adapter.addFrag(new Fragment_Penyelenggara_Kandidat(), "KANDIDAT");
        }else if(InfoAkun.getLevel() == 2){
            adapter.addFrag(new Fragment_Penyelenggara_Pemilihan_Info(), "INFO");
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
