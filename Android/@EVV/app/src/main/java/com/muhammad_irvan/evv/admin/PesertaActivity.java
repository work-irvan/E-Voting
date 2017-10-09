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
import com.muhammad_irvan.evv.admin.fragment.Fragment_Pemilihan_Peserta;
import com.muhammad_irvan.evv.admin.fragment.Fragment_Pemilihan_Peserta_permintaan;

import java.util.ArrayList;
import java.util.List;

public class PesertaActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peserta);

        toolbar = (Toolbar) findViewById(R.id.toolbarPeserta);
        toolbar.setTitle("Peserta " + DataPemilihanPenyelenggaraTerpilih.getPenyelenggara_namaPemilihan());
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpagerPeserta);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabsPeserta);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        PesertaActivity.ViewPagerAdapter adapter = new PesertaActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Fragment_Pemilihan_Peserta(), "PESERTA");
        adapter.addFrag(new Fragment_Pemilihan_Peserta_permintaan(), "PERMINTAAN");

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
