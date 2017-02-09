package example.android.jacky.fatigue;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.sonymobile.lifelog.LifeLog;

import Adapter.TabsPagerAdapter;

public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "Settings", "Activities", "Fatigue"};

    private Handler handler;
    private Runnable energyLevelRunnable;
    private DialogFragment energyLevelFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        LifeLog.initialise("29361d34-123d-4659-a764-629d6b28eb52","nI-rknO3BVyL2OXzxahdssobjNM","https://localhost");
        LifeLog.doLogin(MainActivity.this);

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    public void displayEnergyLevelDialog(){

        energyLevelRunnable = new Runnable() {

            @Override
            public void run() {

                if(energyLevelFrag == null) {
                    energyLevelFrag = EnergyLevelDialog.newInstance();
                }
                else{
                    if(energyLevelFrag.getDialog() == null) {
                        energyLevelFrag = EnergyLevelDialog.newInstance();
                        energyLevelFrag.setCancelable(false);
                        energyLevelFrag.show(getSupportFragmentManager(), "dialog");
                    }
                }

//                handler.postDelayed(this,2000);
            }
        };

        handler.postDelayed(energyLevelRunnable,3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LifeLog.LOGINACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "User authenticated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "User authentication failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        //displayEnergyLevelDialog();
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());

        Log.d("Testing Tab", tab.getText().toString());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onPause(){
        super.onPause();
//        if (handler != null) {
//            handler.removeCallbacks(energyLevelRunnable);
//        }
    }

    @Override
    public void onResume(){
        super.onResume();
//        if (handler != null) {
//            displayEnergyLevelDialog();
//        }
    }



}
