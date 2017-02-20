package example.android.jacky.fatigue;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.sonymobile.lifelog.LifeLog;
import com.sonymobile.lifelog.utils.Debug;
import com.sonymobile.lifelog.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesDaily extends AppCompatActivity {

    private PieChart mChart;

    private int numOfPhysical, numOfTransport, numOfApplication, numOfCamera, numOfSleep, numOfMusic;

    private static final String API_URL = "https://platform.lifelog.sonymobile.com/v1/users/me/activities";

    private static final String TAG = "ActivitiesFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_daily);

        mChart = (PieChart) findViewById(R.id.chart1);

        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTextSize(13f);
        mChart.setUsePercentValues(true);

        mChart.getDescription().setEnabled(false);
        mChart.setCenterText("Activities Summary");
        mChart.setCenterTextSize(25f);
        mChart.setCenterTextColor(Color.MAGENTA);
        mChart.setDrawCenterText(true);

        mChart.setNoDataText("No data has been logged by LifeLog");

        Legend legend = mChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextColor(Color.WHITE);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        LifeLog.checkAuthentication(ActivitiesDaily.this, new LifeLog.OnAuthenticationChecked() {
            @Override
            public void onAuthChecked(boolean authenticated) {
                if (authenticated) {

                    processLifeLogRequests();

                }
                else {
                    //User is not authenticated. Make them login
                    LifeLog.doLogin(ActivitiesDaily.this);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    public void processLifeLogRequests(){

        String apiUrl = API_URL;

        apiUrl = apiUrl + "?start_time="+ DateHelper.getIsoDate(false,false)+"&end_time="+ DateHelper.getIsoDate(false,true);


        Log.d(TAG, "The API Url is :" + apiUrl);

        // Perform the activities request
        JsonObjectRequest activitiesRequest = new AuthedJsonObjectRequest(getApplicationContext(),
                Request.Method.GET, apiUrl, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.v(TAG, jsonObject.toString());
                        try {
                            // reset the data counters
                            resetCounters();

                            JSONArray activitiesArray = jsonObject.getJSONArray("result");

                            for(int i = 0; i < activitiesArray.length(); i++){
                                JSONObject activityObj = activitiesArray.optJSONObject(i);
                                String type = activityObj.getString("type");
                                if(type.equalsIgnoreCase("physical")){
                                    numOfPhysical++;
                                }
                                else if(type.equalsIgnoreCase("transport")){
                                    numOfTransport++;
                                }
                                else if(type.equalsIgnoreCase("application")){
                                    numOfApplication++;
                                }
                                else if(type.equalsIgnoreCase("music")){
                                    numOfMusic++;
                                }
                                else if(type.equalsIgnoreCase("camera")){
                                    numOfCamera++;
                                }
                                else if(type.equalsIgnoreCase("sleep")){
                                    numOfSleep++;
                                }
                            }
                            Log.d(TAG, "numOfPhysical: "+numOfPhysical);
                            Log.d(TAG, "numOfTransport: "+numOfTransport);
                            Log.d(TAG, "numOfApplication: "+numOfApplication);
                            Log.d(TAG, "numOfMusic: "+numOfMusic);
                            Log.d(TAG, "numOfCamera: "+numOfCamera);
                            Log.d(TAG, "numOfSleep: "+numOfSleep);

                            if(!checkNoLoggedData()) {
                                List<PieEntry> entries = new ArrayList<>();

                                int totalActivity = numOfApplication + numOfTransport + numOfPhysical + numOfSleep + numOfMusic + numOfCamera;

                                entries.add(new PieEntry((numOfPhysical * 100) / (float) totalActivity, "Physical"));
                                entries.add(new PieEntry((numOfTransport * 100) / (float) totalActivity, "Transport"));
                                entries.add(new PieEntry((numOfApplication * 100) / (float) totalActivity, "Application"));
                                entries.add(new PieEntry((numOfMusic * 100) / (float) totalActivity, "Music"));
                                entries.add(new PieEntry((numOfCamera * 100) / (float) totalActivity, "Camera"));
                                entries.add(new PieEntry((numOfSleep * 100) / (float) totalActivity, "Sleep"));

                                PieDataSet set = new PieDataSet(entries, "Activities");
                                set.setColors(new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.GRAY});
                                set.setSliceSpace(5f);
                                PieData data = new PieData(set);
                                data.setValueFormatter(new PercentFormatter());
                                data.setValueTextColor(Color.BLACK);
                                data.setValueTextSize(18f);

                                mChart.setData(data);
                                mChart.invalidate(); // refresh
                            }
                            else{
                                Toast.makeText(ActivitiesDaily.this, "Cannot display the summary as no data is logged in this period", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            if (Debug.isDebuggable(ActivitiesDaily.this)) {
                                Log.w(TAG, "JSONException", e);
                            }
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        VolleySingleton.getInstance(ActivitiesDaily.this).addToRequestQueue(activitiesRequest);
    }

    public void resetCounters(){
        numOfApplication = 0;
        numOfCamera = 0;
        numOfMusic = 0;
        numOfPhysical = 0;
        numOfSleep = 0;
        numOfTransport = 0;
    }

    public boolean checkNoLoggedData(){

        if(numOfApplication == 0 && numOfCamera == 0 && numOfMusic == 0
                && numOfPhysical == 0 && numOfSleep == 0 && numOfTransport == 0){
            return true;
        }

        return false;
    }



}
