package example.android.jacky.fatigue;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnergyLevelDaily extends AppCompatActivity {

    private PieChart mChart;

    private static String TAG = "EnergyLevelDaily";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_level_daily);

        mChart = (PieChart) findViewById(R.id.chart1);

        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTextSize(13f);
        mChart.setUsePercentValues(true);

        mChart.getDescription().setEnabled(false);
        mChart.setCenterText("Daily Energy Level Summary");
        mChart.setCenterTextSize(25f);
        mChart.setCenterTextColor(Color.MAGENTA);
        mChart.setDrawCenterText(true);

        mChart.setNoDataText("No data has been stored");

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference energyLevelRef = database.getReference("energyDialogResponse");

        DateHelper dateHelper = new DateHelper();

        Query queryRef = energyLevelRef.orderByKey().startAt(Long.toString(dateHelper.getStartOfDayMillis()))
                .endAt(Long.toString(dateHelper.getEndOfDayMillis()));

        Log.d(TAG, "Start At: " + dateHelper.getStartOfDayMillis() + "End At: " + dateHelper.getEndOfDayMillis());

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String,String> responseMap = new HashMap<String, String>();

                for (DataSnapshot jobSnapshot: dataSnapshot.getChildren()) {
                    HashMap<String,String> level = (HashMap<String,String>) jobSnapshot.getValue();
                    for (String value : level.values()){
                        responseMap.put(jobSnapshot.getKey(), value);
                    }
                }

                Log.d(TAG, "Child is : "+ responseMap.keySet());

                if(responseMap != null) {

                    List<PieEntry> entries = new ArrayList<>();

                    int totalResponse = FatigueFragment.countResponse(responseMap);

                    Log.d(TAG, "Number of Response: "+totalResponse);

                    if (FatigueFragment.getCounter(0) != 0) {
                        entries.add(new PieEntry((FatigueFragment.getCounter(0) * 100) / (float) totalResponse, "Tired"));
                    }
                    if (FatigueFragment.getCounter(1) != 0) {
                        entries.add(new PieEntry((FatigueFragment.getCounter(1) * 100) / (float) totalResponse, "10%"));
                    }
                    if (FatigueFragment.getCounter(2) != 0) {
                        entries.add(new PieEntry((FatigueFragment.getCounter(2) * 100) / (float) totalResponse, "20%"));
                    }
                    if (FatigueFragment.getCounter(3) != 0) {
                        entries.add(new PieEntry((FatigueFragment.getCounter(3) * 100) / (float) totalResponse, "30%"));
                    }
                    if (FatigueFragment.getCounter(4) != 0) {
                        entries.add(new PieEntry((FatigueFragment.getCounter(4) * 100) / (float) totalResponse, "40%"));
                    }
                    if (FatigueFragment.getCounter(5) != 0) {
                        entries.add(new PieEntry((FatigueFragment.getCounter(5) * 100) / (float) totalResponse, "50%"));
                    }
                    if (FatigueFragment.getCounter(6) != 0) {
                        entries.add(new PieEntry((FatigueFragment.getCounter(6) * 100) / (float) totalResponse, "60%"));
                    }
                    if (FatigueFragment.getCounter(7) != 0) {
                        entries.add(new PieEntry((FatigueFragment.getCounter(7) * 100) / (float) totalResponse, "70%"));
                    }
                    if (FatigueFragment.getCounter(8) != 0) {
                        entries.add(new PieEntry((FatigueFragment.getCounter(8) * 100) / (float) totalResponse, "80%"));
                    }
                    if (FatigueFragment.getCounter(9) != 0) {
                        entries.add(new PieEntry((FatigueFragment.getCounter(9) * 100) / (float) totalResponse, "90%"));
                    }
                    if (FatigueFragment.getCounter(10) != 0) {
                        entries.add(new PieEntry((FatigueFragment.getCounter(10) * 100) / (float) totalResponse, "Energetic"));
                    }

                    PieDataSet set = new PieDataSet(entries, "Energy Level");
                    set.setColors(new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN, Color.GRAY});
                    set.setSliceSpace(5f);
                    PieData data = new PieData(set);
                    data.setValueFormatter(new PercentFormatter());
                    data.setValueTextColor(Color.BLACK);
                    data.setValueTextSize(18f);

                    mChart.setData(data);
                    mChart.invalidate(); // refresh
                }
                else{
                    Toast.makeText(EnergyLevelDaily.this, "Cannot display the summary as no data is stored for today", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "loadQuery:onCancelled", databaseError.toException());
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

}
