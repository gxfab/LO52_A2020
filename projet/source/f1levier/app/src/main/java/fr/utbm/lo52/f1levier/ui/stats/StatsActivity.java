package fr.utbm.lo52.f1levier.ui.stats;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import fr.utbm.lo52.f1levier.R;

public class StatsActivity extends AppCompatActivity {

    public static final String EXTRA_RACE_ID = "race_id";

    private int raceId;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        raceId = getIntent().getIntExtra(EXTRA_RACE_ID, -1);
        if (raceId == -1) {
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        mPager = findViewById(R.id.stats_pager);
        mPagerAdapter = new StatsPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private class StatsPagerAdapter extends FragmentStatePagerAdapter {
        public StatsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {
                case 0:
                    fragment = GlobalTeamStatsFragment.newInstance(raceId);
                    break;
                case 1:
                    fragment = GlobalParticipantStatsFragment.newInstance(raceId);
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
