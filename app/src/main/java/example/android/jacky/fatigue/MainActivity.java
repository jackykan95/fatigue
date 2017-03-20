package example.android.jacky.fatigue;

import android.app.ActionBar;
import android.app.FragmentTransaction;
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
    private Runnable energyLevelRunnable, fatigueRunnable;
    private DialogFragment energyLevelFrag, fatigueFrag;

    // default to 1 minute
    private int energyDelay = 60000 , fatigueDelay = 60000;

    private static String TAG = "MainActivity";

    private boolean isMainActivity;

    private boolean displayEnergyStartup = true;

    private boolean displayFatigueStartup = true;

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

        viewPager.setCurrentItem(1);
    }

    public void displayEnergyLevelDialog(){

        Log.d(TAG, "Displaying Energy Level Dialog");

        energyLevelRunnable = new Runnable() {

            @Override
            public void run() {

                if(energyLevelFrag == null) {
                    energyLevelFrag = EnergyLevelDialog.newInstance();
                }
                if(energyLevelFrag.getDialog() == null && isMainActivity) {
                    energyLevelFrag = EnergyLevelDialog.newInstance();
                    energyLevelFrag.setCancelable(false);
                    energyLevelFrag.show(getSupportFragmentManager(), "dialog");
                }

            }
        };
        Log.d(TAG, "Energy Frequency : "+energyDelay);
        handler.postDelayed(energyLevelRunnable,energyDelay);
    }

    public void displayFatigueDialog(){

        Log.d(TAG, "Displaying Fatigue Dialog");

        fatigueRunnable = new Runnable() {

            @Override
            public void run() {

                if (fatigueFrag == null) {
                    fatigueFrag = FatigueDialog.newInstance();
                }
                if (fatigueFrag.getDialog() == null && isMainActivity) {
                    fatigueFrag = FatigueDialog.newInstance();
                    fatigueFrag.setCancelable(false);
                    fatigueFrag.show(getSupportFragmentManager(), "dialog");
                }


            }
        };
        Log.d(TAG, "Fatigue Frequency : "+fatigueDelay);
        handler.postDelayed(fatigueRunnable,fatigueDelay);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LifeLog.LOGINACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "User authenticated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "User authentication failed", Toast.LENGTH_SHORT).show();
                LifeLog.doLogin(MainActivity.this);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
        isMainActivity = false;
        if (handler != null) {
            handler.removeCallbacks(energyLevelRunnable);
            handler.removeCallbacks(fatigueRunnable);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if (handler != null) {
            handler.removeCallbacks(energyLevelRunnable);
            handler.removeCallbacks(fatigueRunnable);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        isMainActivity = true;
        if (handler != null && !displayEnergyStartup && !displayFatigueStartup) {
            displayEnergyLevelDialog();
            displayFatigueDialog();
        }
    }

    public void setEnergyDelay(int delay){
        energyDelay = delay;

        if (displayEnergyStartup) {
            Log.d(TAG, "Display Energy at startup");
            displayEnergyStartup = false;
            //Log.d(TAG, "Energy Frequency : "+dialogFrequencyDAO.getEnergyFrequency());
            displayEnergyLevelDialog();
        }
    }

    public void setFatigueDelay(int delay){
        fatigueDelay = delay;

        if (displayFatigueStartup) {
            Log.d(TAG, "Display Fatigue at startup");
            displayFatigueStartup = false;
            //Log.d(TAG, "Fatigue Frequency : "+dialogFrequencyDAO.getFatigueFrequency());
            displayFatigueDialog();
        }
    }

    public int getEnergyDelay(){return energyDelay;}

    public int getFatigueDelay(){return fatigueDelay;}

}
