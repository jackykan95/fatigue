package example.android.jacky.fatigue;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sonymobile.lifelog.LifeLog;
import com.sonymobile.lifelog.utils.Debug;
import com.sonymobile.lifelog.utils.ISO8601Date;
import com.sonymobile.lifelog.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Jacky on 11/01/2017.
 */

public class ActivitiesFragment extends Fragment{

    private PieChart mChart;

    private int numOfPhysical, numOfTransport, numOfApplication, numOfCamera, numOfSleep, numOfMusic;

    private static final String API_URL = "https://platform.lifelog.sonymobile.com/v1/users/me/activities";

    private static final String TAG = "ActivitiesFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_activities, container, false);

        mChart = (PieChart) rootView.findViewById(R.id.chart1);

        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTextSize(18f);

        mChart.getDescription().setEnabled(false);
        List<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(18.5f, "Green"));
        entries.add(new PieEntry(26.7f, "Yellow"));
        entries.add(new PieEntry(24.0f, "Red"));
        entries.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set = new PieDataSet(entries, "Election Results");
        set.setColors(new int[] {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW});
        PieData data = new PieData(set);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(18f);
        mChart.setData(data);
        mChart.invalidate(); // refresh

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean hidden){
        if(hidden) {
            // do the Lifelog request here
            Log.d("ActivitiesFragment", "Activities is not in view");
            getLifeLogActivitiesData();
        }
        else{
            Log.d("ActivitiesFragment", "Activities is currently in view");
        }
    }

    public void getLifeLogActivitiesData(){
        LifeLog.checkAuthentication(ActivitiesFragment.this.getContext(), new LifeLog.OnAuthenticationChecked() {
            @Override
            public void onAuthChecked(boolean authenticated) {
                if (authenticated) {

                    // reset the data counters
                    resetCounters();

                    // Perform the
                    JsonObjectRequest activitiesRequest = new AuthedJsonObjectRequest(ActivitiesFragment.this.getContext(),
                            Request.Method.GET, API_URL, (JSONObject) null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    Log.v(TAG, jsonObject.toString());
                                    try {
                                        JSONArray activitiesArray = jsonObject.getJSONArray("result");

//                                        betweenDateTextView = (TextView) findViewById(R.id.dates_logged_data_text_view);
//
//                                        Log.d(TAG, "The content of activities are: " + activitiesArray.toString());
//
//                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
//                                        Calendar latestDateCalendar = ISO8601Date.toCalendar(activitiesArray.optJSONObject(0).getString("startTime"));
//                                        Calendar earliestDateCalendar = ISO8601Date.toCalendar(activitiesArray.optJSONObject(activitiesArray.length()-1).getString("startTime"));
//                                        String betweenDate = "Data Logged between "+dateFormat.format(earliestDateCalendar.getTime())+" and "+dateFormat.format(latestDateCalendar.getTime());
//                                        betweenDateTextView.setText(betweenDate);

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


                                    } catch (Exception e) {
                                        if (Debug.isDebuggable(ActivitiesFragment.this.getContext())) {
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
                    VolleySingleton.getInstance(getActivity()).addToRequestQueue(activitiesRequest);
                } else {
                    //User is not authenticated. Make them login
                    LifeLog.doLogin(getActivity());
                }
            }
        });
    }

    public void resetCounters(){
        numOfApplication = 0;
        numOfCamera = 0;
        numOfMusic = 0;
        numOfPhysical = 0;
        numOfSleep = 0;
        numOfTransport = 0;
    }



}
