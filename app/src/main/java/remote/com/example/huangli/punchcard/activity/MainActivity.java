package remote.com.example.huangli.punchcard.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.ctviews.BottomActionBar;
import remote.com.example.huangli.punchcard.fragment.FriendFragment;
import remote.com.example.huangli.punchcard.fragment.MineFragment;
import remote.com.example.huangli.punchcard.fragment.TaskFragment;
import remote.com.example.huangli.punchcard.fragment.WorldFragment;

public class MainActivity extends AppCompatActivity {
    private BottomActionBar bottomActionBar;
    private ViewPager viewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Fragment taskFragment,mineFragment,worldFragment,friendFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListeners();
        initUi();
    }

    private void initUi() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
    }

    private void findViews(){
        bottomActionBar = (BottomActionBar)findViewById(R.id.actionbar_bottom);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
    }

    private void setListeners(){
        bottomActionBar.setOnBottomActionBarItemClickListener(new BottomActionBar.OnBottomActionBarItemClickListener() {
            @Override
            public void selected(int type) {
                switch (type){
                    case BottomActionBar.TYPE_BOTTOM_ACTION_TASK:
                        viewPager.setCurrentItem(0);
                        break;
                    case BottomActionBar.TYPE_BOTTOM_ACTION_FRIEND:
                        viewPager.setCurrentItem(1);
                        break;
                    case BottomActionBar.TYPE_BOTTOM_ACTION_WORLD:
                        viewPager.setCurrentItem(2);
                        break;
                    case BottomActionBar.TYPE_BOTTOM_ACTION_MINE:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomActionBar.selected(BottomActionBar.TYPE_BOTTOM_ACTION_TASK);
                        break;
                    case 1:
                        bottomActionBar.selected(BottomActionBar.TYPE_BOTTOM_ACTION_FRIEND);
                        break;
                    case 2:
                        bottomActionBar.selected(BottomActionBar.TYPE_BOTTOM_ACTION_WORLD);
                        break;
                    case 3:
                        bottomActionBar.selected(BottomActionBar.TYPE_BOTTOM_ACTION_MINE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    taskFragment = TaskFragment.newInstance();
                    return taskFragment;
                case 1:
                    friendFragment = FriendFragment.newInstance();
                    return friendFragment;
                case 2:
                    worldFragment = WorldFragment.newInstance();
                    return worldFragment;
                case 3:
                    mineFragment = MineFragment.newInstance();
                    return mineFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0:
//                    return getString(R.string.tab_battery);
//                case 1:
//                    return getString(R.string.tab_mode);
//            }
            return null;
        }
    }
}
